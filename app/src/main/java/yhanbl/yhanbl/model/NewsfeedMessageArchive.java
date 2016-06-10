package yhanbl.yhanbl.model;

/**
 * Created by mrgn on 09/06/2016.
 */
public class NewsfeedMessageArchive {
    private final int IS_DELETABLE = 0;
    private final int IS_READABLE  = 1;

    private final int EXIT_LEFT   = 0;
    private final int EXIT_RIGHT  = 1;

    private int status;
    private int exitSide;
    private Message message;

    public NewsfeedMessageArchive(Message message, boolean shouldDelete, boolean exitedToLeft) {

        if (message == null) throw new NullPointerException("Message is NULL for NewsfeedMessageArchive");

        this.message = message;
        if (shouldDelete) {
            this.status = IS_DELETABLE;
        } else {
            this.status = IS_READABLE;
        }
    }

    public boolean isReadable() {
        return this.status == IS_READABLE;
    }

    public boolean isDeletable() {
        return this.status == IS_DELETABLE;
    }

    public boolean exitedToLeft() {
        return this.exitSide == EXIT_LEFT;
    }

    public boolean exitedToRight() {
        return this.exitSide == EXIT_RIGHT;
    }

    public Message getMessage() {
        return this.message;
    }

}
