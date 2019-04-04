package co.uk.babylon.ui.list;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.uk.babylon.R;
import co.uk.babylon.data.model.Post;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder> {

    private final PostSelectedListener postSelectedListener;
    private final List<Post> data = new ArrayList<>();

    PostListAdapter(final ListViewModel viewModel, final LifecycleOwner lifecycleOwner, final PostSelectedListener postSelectedListener) {
        this.postSelectedListener = postSelectedListener;
        viewModel.getPosts().observe(lifecycleOwner, posts -> {
            data.clear();
            if (posts != null) {
                data.addAll(posts);
                notifyDataSetChanged();
            }
        });
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_post_list_item, parent, false);
        return new PostViewHolder(view, postSelectedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostViewHolder holder, final int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(final int position) {
        return data.get(position).getId().hashCode();
    }

    static final class PostViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.post_name)
        TextView postNameTextView;

        private Post post;

        PostViewHolder(final View itemView, final PostSelectedListener repoSelectedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                if (post != null) {
                    repoSelectedListener.onPostSelected(post);
                }
            });
        }

        void bind(final Post post) {
            this.post = post;
            postNameTextView.setText(post.getTitle());
        }
    }
}
