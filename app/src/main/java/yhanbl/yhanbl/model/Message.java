package yhanbl.yhanbl.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by mrgn on 09/06/2016.
 */
public class Message {
    private String text;
    private String imagePath;

    public Message(String imagePath, String text) {
        this.imagePath = imagePath;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getExcerpt() {
        if (text.length() > 120) {
            return text.substring(0, 120).concat("...");
        } else {
            return text;
        }
    }
}
