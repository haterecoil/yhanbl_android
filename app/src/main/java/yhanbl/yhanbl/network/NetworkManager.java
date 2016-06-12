package yhanbl.yhanbl.network;

import java.util.List;

import yhanbl.yhanbl.model.Message;

/**
 * Created by mrgn on 11/06/2016.
 */
public class NetworkManager {
    public List<Message> mMessages;

    //une interface définit un contrat / un comportement
    public interface MovieResultListener {
        void onFoundMovies(Message[] messages);
        void onFail();
    }

    public static void getNewMessages() {

    }
}
