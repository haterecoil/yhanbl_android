package yhanbl.yhanbl.model;

import android.app.Service;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import yhanbl.yhanbl.network.ServiceGenerator;

/**
 * Created by mrgn on 09/06/2016.
 */
public class Message {

    @SerializedName("text")
    private String text;

    @SerializedName("picture")
    private String messagePicture;

    public Message(String messagePicture, String text) {
        this.messagePicture = ServiceGenerator.API_BASE_URL + messagePicture;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setMessagePicture(String messagePicture) {
        this.messagePicture = messagePicture;
    }

    public String getPictureUrl() {
        return "http://vps254447.ovh.net" + messagePicture;
    }

    public String getExcerpt() {
        if (text.length() > 120) {
            return text.substring(0, 120).concat("...");
        } else {
            return text;
        }
    }
}
