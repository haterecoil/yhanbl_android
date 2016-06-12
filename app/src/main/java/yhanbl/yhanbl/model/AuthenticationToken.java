package yhanbl.yhanbl.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mrgn on 12/06/2016.
 */
public class AuthenticationToken {

    @SerializedName("authentication_token")
    private String token;

    @SerializedName("username")
    private String username;

    private String fullToken;

    public AuthenticationToken(String serializedToken) {
        this.fullToken = serializedToken;
    }

    public AuthenticationToken(String token, String username) {
        this.token = token;
        this.username = username;
        this.setFullToken();
    }

    @Override
    public String toString() {
        if (this.fullToken == null) this.setFullToken();
        return this.fullToken;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private void setFullToken() {
        this.fullToken = String.format("Token token=\"%s\", username=\"%s\"", this.token, this.username);
    }
}
