package yhanbl.yhanbl.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mrgn on 12/06/2016.
 */
public class User {

    private String username;
    private String password;

    public User(){}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public class Credentials {

        private String username;
        private String password;

        public Credentials(String pUsername, String pPassword) {
            this.username = pUsername;
            this.password = pPassword;
        }

        public String getUsername() {
            return this.username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public class Token {
        @SerializedName("authentication_token")
        private String authenticationToken;

        private String token;

        @SerializedName("username")
        private String username;


        public Token(String token, String username) {
            this.username = username;
            this.setToken(token);
        }

        public void setToken(String token) {
            this.token = String.format("Token token=\"%s\", username=\"%s\"", token, this.username);
        }


        public String getToken() {
            return token;
        }
    }
}
