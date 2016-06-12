package yhanbl.yhanbl.network;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import yhanbl.yhanbl.model.User;

/**
 * Created by mrgn on 12/06/2016.
 */
public interface YhanblClient {

    @POST("/sign_in")
    Call<User.Token> authenticateUser(@Body User.Credentials userCredentials);

}
