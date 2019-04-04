package co.uk.babylon.data.network;

import java.util.List;

import co.uk.babylon.data.model.Comment;
import co.uk.babylon.data.model.Post;
import co.uk.babylon.data.model.User;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface NetworkService {

    @GET("posts")
    Single<List<Post>> getPosts();

    @GET("users")
    Single<List<User>> getUsers();

    @GET("comments")
    Single<List<Comment>> getComments();
}
