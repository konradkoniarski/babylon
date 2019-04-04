package co.uk.babylon.data.network;

import java.util.List;

import javax.inject.Inject;

import co.uk.babylon.data.model.Comment;
import co.uk.babylon.data.model.Post;
import co.uk.babylon.data.model.User;
import io.reactivex.Observable;
import io.reactivex.Single;

public class DataRepository {

    private final NetworkService networkService;

    @Inject
    public DataRepository(final NetworkService networkService) {
        this.networkService = networkService;
    }

    public Single<List<Post>> getPosts() {
        return networkService.getPosts().cache();
    }

    public Single<List<User>> getUsers() {
        return networkService.getUsers();
    }

    public Single<List<Comment>> getComments() {
        return networkService.getComments();
    }

    public Observable<Post> getPost(final String userId, final String postId) {

        final Observable<List<Comment>> commentList = getComments()
                .toObservable()
                .flatMap(Observable::fromIterable)
                .filter(c -> c.getPostId().equals(postId))
                .toList()
                .toObservable();
        final Observable<List<User>> userList = getUsers()
                .toObservable()
                .flatMap(Observable::fromIterable)
                .filter(u -> u.getId().equals(userId))
                .toList()
                .toObservable();

        final Observable result = Observable.zip(commentList, userList, (comments, users) -> {
            final Post post = new Post();
            post.setCommentNumber(String.valueOf(comments.size()));
            post.setUserName(users.get(0).getName());
            return post;
        });

        return result;
    }
}
