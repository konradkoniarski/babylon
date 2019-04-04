package co.uk.babylon.ui.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import co.uk.babylon.R;
import co.uk.babylon.base.BaseFragment;
import co.uk.babylon.util.ViewModelFactory;

public class DetailsFragment extends BaseFragment {

    @BindView(R.id.post_title)
    TextView postTitleTextView;
    @BindView(R.id.post_body)
    TextView postBodyTextView;
    @BindView(R.id.user_name)
    TextView userNameTextView;
    @BindView(R.id.number_comments)
    TextView numberCommentsTextView;

    @Inject
    ViewModelFactory viewModelFactory;
    private DetailsViewModel detailsViewModel;

    @Override
    protected int layoutRes() {
        return R.layout.screen_details;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        detailsViewModel = ViewModelProviders.of(getBaseActivity(), viewModelFactory).get(DetailsViewModel.class);
        detailsViewModel.restoreFromBundle(savedInstanceState);
        displayPosts();
    }

    @Override
    public void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
        detailsViewModel.saveToBundle(outState);
    }

    private void displayPosts() {
        detailsViewModel.getSelectedPost().observe(this, post -> {
            if (post != null) {
                postTitleTextView.setText(post.getTitle());
                postBodyTextView.setText(post.getBody());
                userNameTextView.setText(String.valueOf(post.getUserName()));
                numberCommentsTextView.setText(String.valueOf(post.getCommentNumber()));
            }
        });
    }
}
