package yhanbl.yhanbl.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import yhanbl.yhanbl.model.AuthenticationToken;
import yhanbl.yhanbl.model.Message;
import yhanbl.yhanbl.model.User;

/**
 * Created by mrgn on 12/06/2016.
 */
public interface YhanblClient {

    /*=========
      USER AUTHENTICATION
    ======== */

    // Retreive token from user/apassword
    @POST("/sign_in")
    Call<AuthenticationToken> authenticateUser(@Body User.Credentials userCredentials);

    // create a new user
    @POST("/users")
    Call<AuthenticationToken> createUser(@Body User newUser);

    /*=========
      MESSAGES
    ======== */

    // Get all messages for given user
    @GET("/messages")
    Call<List<Message>> getUserMessages(@Header("Authorization")String authorizationToken);

    // Get one message detail
    @GET("messages/{id}")
    Call<Message> getOneMessage(@Header("Authorization")String authorizationToken, @Path("id") int messageId);

}
