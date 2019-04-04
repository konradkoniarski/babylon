package co.uk.babylon.ui.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Bundle;

import javax.inject.Inject;

import co.uk.babylon.data.model.Post;
import co.uk.babylon.data.network.DataRepository;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class DetailsViewModel extends ViewModel {

    private final DataRepository dataRepository;
    private CompositeDisposable disposable;

    private final MutableLiveData<Post> selectedPost = new MutableLiveData<>();

    LiveData<Post> getSelectedPost() {
        return selectedPost;
    }

    @Inject
    DetailsViewModel(final DataRepository dataRepository) {
        this.dataRepository = dataRepository;
        disposable = new CompositeDisposable();
    }

    public void setSelectedPost(final Post post) {
        selectedPost.setValue(post);
    }

    void saveToBundle(final Bundle outState) {
        if (selectedPost.getValue() != null) {
            outState.putStringArray("post_details",
                    new String[]{
                            selectedPost.getValue().getUserId(),
                            selectedPost.getValue().getId()}
            );
        }
    }

    void restoreFromBundle(final Bundle savedInstanceState) {
        final String userId = selectedPost.getValue().getUserId();
        final String postId = selectedPost.getValue().getId();
        loadRepo(userId, postId);
    }

    private void loadRepo(final String userId, final String postId) {
        disposable.add(Single.fromObservable(dataRepository.getPost(userId, postId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Post>() {
                    @Override
                    public void onSuccess(final Post value) {
                        final Post currentPost = selectedPost.getValue();
                        currentPost.setCommentNumber(value.getCommentNumber());
                        currentPost.setUserName(value.getUserName());
                        selectedPost.setValue(currentPost);
                    }

                    @Override
                    public void onError(final Throwable e) {

                    }
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
    }
}
