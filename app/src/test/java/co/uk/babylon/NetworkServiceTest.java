package co.uk.babylon;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import co.uk.babylon.data.model.Comment;
import co.uk.babylon.data.model.Post;
import co.uk.babylon.data.model.User;
import co.uk.babylon.data.network.DataRepository;
import co.uk.babylon.data.network.NetworkService;
import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

import static org.junit.Assert.assertTrue;

public class NetworkServiceTest {

    private static final String POST_DATA = "[\n" +
            "  {\n" +
            "    \"userId\": 1,\n" +
            "    \"id\": 1,\n" +
            "    \"title\": \"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",\n" +
            "    \"body\": \"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto\"\n" +
            "  }\n" +
            "]";

    private static final String USER_DATA = "[\n" +
            "  {\n" +
            "    \"id\": 1,\n" +
            "    \"name\": \"Leanne Graham\",\n" +
            "    \"username\": \"Bret\",\n" +
            "    \"email\": \"Sincere@april.biz\",\n" +
            "    \"address\": {\n" +
            "      \"street\": \"Kulas Light\",\n" +
            "      \"suite\": \"Apt. 556\",\n" +
            "      \"city\": \"Gwenborough\",\n" +
            "      \"zipcode\": \"92998-3874\",\n" +
            "      \"geo\": {\n" +
            "        \"lat\": \"-37.3159\",\n" +
            "        \"lng\": \"81.1496\"\n" +
            "      }\n" +
            "    },\n" +
            "    \"phone\": \"1-770-736-8031 x56442\",\n" +
            "    \"website\": \"hildegard.org\",\n" +
            "    \"company\": {\n" +
            "      \"name\": \"Romaguera-Crona\",\n" +
            "      \"catchPhrase\": \"Multi-layered client-server neural-net\",\n" +
            "      \"bs\": \"harness real-time e-markets\"\n" +
            "    }\n" +
            "  }\n" +
            "]";
    private static final String COMMENT_DATA = "[\n" +
            "  {\n" +
            "    \"postId\": 1,\n" +
            "    \"id\": 1,\n" +
            "    \"name\": \"id labore ex et quam laborum\",\n" +
            "    \"email\": \"Eliseo@gardner.biz\",\n" +
            "    \"body\": \"laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium\"\n" +
            "  }\n" +
            "]";

    private MockWebServer mockWebServer;
    private NetworkService service;

    @Before
    public void setUp() {
        mockWebServer = new MockWebServer();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("").toString())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        final Dispatcher dispatcher = new Dispatcher() {

            @Override
            public MockResponse dispatch(final RecordedRequest request) throws InterruptedException {

                if (request.getPath().equals("/posts")) {
                    return new MockResponse().setResponseCode(200).setBody(POST_DATA);
                } else if (request.getPath().equals("/users")) {
                    return new MockResponse().setResponseCode(200).setBody(USER_DATA);
                } else if (request.getPath().equals("/comments")) {
                    return new MockResponse().setResponseCode(200).setBody(COMMENT_DATA);
                }
                return new MockResponse().setResponseCode(404);
            }
        };
        mockWebServer.setDispatcher(dispatcher);
        service = retrofit.create(NetworkService.class);
    }

    @Test
    public void testPost() throws IOException {
        final Single<List<Post>> call = service.getPosts();
        call.subscribe((posts, throwable) -> {
            assertTrue(posts != null);
            assertTrue(posts.size() == 1);
            assertTrue(posts.get(0) != null);
            assertTrue(posts.get(0).getId() != null);
            assertTrue(posts.get(0).getId() != "1");
        });
    }

    @Test
    public void testUsers() throws IOException {
        final Single<List<User>> call = service.getUsers();
        call.subscribe((user, throwable) -> {
            assertTrue(user != null);
            assertTrue(user.size() == 1);
            assertTrue(user.get(0) != null);
            assertTrue(user.get(0).getId() != null);
            assertTrue(user.get(0).getId() != "1");
        });
    }

    @Test
    public void testComments() throws IOException {
        final Single<List<Comment>> call = service.getComments();
        call.subscribe((comment, throwable) -> {
            assertTrue(comment != null);
            assertTrue(comment.size() == 1);
            assertTrue(comment.get(0) != null);
            assertTrue(comment.get(0).getId() != null);
            assertTrue(comment.get(0).getId() != "1");
        });
    }

    @Test
    public void testDataRepository() {
        final DataRepository dataRepository = new DataRepository(service);
        final Observable<Post> call = dataRepository.getPost("1", "1");
        call.toList().subscribe((post, throwable) -> {
            assertTrue(post != null);
            assertTrue(post.size() == 1);
            assertTrue(post.get(0) != null);
            assertTrue(post.get(0).getCommentNumber().equals("1"));
            assertTrue(post.get(0).getUserName().equals("Leanne Graham"));
        });
    }

    @After
    public void close() throws IOException {
        mockWebServer.shutdown();
    }
}