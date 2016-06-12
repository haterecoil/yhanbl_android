package yhanbl.yhanbl.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by mrgn on 12/06/2016.
 */
public class AuthenticationTokenManager {

    private AuthenticationToken authenticationToken;

    private final String SHARED_PREFERENCES_KEY = "YhanblAuthenticationToken";

    private SharedPreferences spSettings;


    public AuthenticationTokenManager(Context context) {
        this.spSettings = PreferenceManager.getDefaultSharedPreferences(context);
        this.tryToSetTokenFromSp();
    }

    /**
     * Create a new token in SharedPreferences
     * and store it in object
     * @param token plain text token
     * @param username plain text username
     */
    public void setAuthenticationToken(String token, String username) {
        this.authenticationToken = new AuthenticationToken(token, username);
        this.persistAuthenticationToken();
    }

    /**
     * Create a new token in SharedPreferences
     * and store it in object
     * @param token plain text token
     */
    public void setAuthenticationToken(String token) {
        this.authenticationToken = new AuthenticationToken(token);
        this.persistAuthenticationToken();
    }

    /**
     * Create a new token in SharedPreferences
     * and store it in object
     * @param token plain text token
     */
    public void setAuthenticationToken(AuthenticationToken token) {
        Log.d("ATM", "gonna set");
        Log.d("ATM", token.toString());
        this.authenticationToken = token;
        this.persistAuthenticationToken();
    }

    public boolean hasAuthenticationToken() {
        return !("".equals(spSettings.getString(SHARED_PREFERENCES_KEY, "")));
    }

    /**
     * Simple getter
     * @return AuthenticationToken object
     */
    public AuthenticationToken getAuthenticationToken() {
        return this.authenticationToken;
    }

    /**
     * String version getter (can be injected in a header)
     * @return full string version of token;
     */
    public String getSerializedAuthenticationToken() {
        return this.getAuthenticationToken().toString();
    }

    /**
     * persist stored token in SharedPreferences
     */
    private void persistAuthenticationToken() {
        Log.d("ATM", "gonna persist");
        SharedPreferences.Editor editor = this.spSettings.edit();
        editor.putString(SHARED_PREFERENCES_KEY, this.getSerializedAuthenticationToken());
        editor.commit();
        Log.d("ATM", "persisted");
    }

    private void tryToSetTokenFromSp() {
        String auth_token_string = spSettings.getString(SHARED_PREFERENCES_KEY, "");
        if (auth_token_string.length() > 0) {
            this.setAuthenticationToken(auth_token_string);
        }
    }


}
