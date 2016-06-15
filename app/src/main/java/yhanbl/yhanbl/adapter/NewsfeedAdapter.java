package yhanbl.yhanbl.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import yhanbl.yhanbl.R;
import yhanbl.yhanbl.model.Message;
import yhanbl.yhanbl.model.NewsfeedMessageArchive;

/**
 * Created by mrgn on 09/06/2016.
 */
public class NewsfeedAdapter extends BaseAdapter {
    private List<Message> messages;
    private List<NewsfeedMessageArchive> messagesArchive;

    //parsing XML + instanciation des vues
    //charger les cellules depuis le XML
    private LayoutInflater layoutInflater;

    public NewsfeedAdapter(Context context) {
        messages = new ArrayList<Message>();
        messagesArchive = new ArrayList<NewsfeedMessageArchive>();

        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public Message getMessage(int position) {
        return (Message) getItem(position);
    }

    public void remove(int position) {
        messages.remove(position);
    }

    @Override
    public View getView(final int position, View viewFromCache, ViewGroup parent) {

        //if empty cache
        //  create cache
        if (viewFromCache == null) {
            //parent = listview
            //attachToParent = false OBLIGATOIRE
            //création d'un cellule à partir du layout item newsfeed
            viewFromCache = layoutInflater.inflate(R.layout.newsfeed_message, parent, false);
            //create cache tag with a row movie
            viewFromCache.setTag(new NewsfeedMessageHolder(viewFromCache));
        }

        // get current message
        Message message = messages.get(position);
        // get holder from cache
        NewsfeedMessageHolder newsfeedMessageHolder = (NewsfeedMessageHolder) viewFromCache.getTag();
        //update holder with the message
        Log.d("NewsfeedAd.getView", String.format("Refreshing with message %s", message.getExcerpt()));
        Log.d("NewsfeedAd.getView", String.format("Refreshing with picture %s", message.getPictureUrl()));
        newsfeedMessageHolder.refreshWithMessage(message);

        return viewFromCache;
    }

    public void setMessageToReadLater(Message message) {
        Log.d("ADAPTER", "Set to read later");
        messagesArchive.add(new NewsfeedMessageArchive(message, false, false));
    }

    public void setMessageToDelete(Message message) {
        Log.d("ADAPTER", "Set to delete");
        messagesArchive.add(new NewsfeedMessageArchive(message, true, true));
    }

    public Message getAndRemoveLastMessage() {
        return messagesArchive.remove(messagesArchive.size() - 1).getMessage();
    }

    public void refreshWithMessages(List<Message> messages) {
        Log.d("REFRESH", "Refresh with messages...");
        this.messages.clear();
        this.messages.addAll(messages);

        notifyDataSetChanged();
    }

// here was the cancel

    /**
     * cancelLastChoice
     * removes last messageArchive and inserts it in messages
     */
    public void cancelLastChoice() {
        Log.d("LISTEN TO ME", "just cancel !");


        /**
         * CURRENT EVENTS WHEN YOU MOVE SOMETHING
         *
         * .selectLeft()
         *  .onSelected(true, _Y_vers_ou_aller, _durée)
         *   this.frame.animate   -> this.frame = current_frame
         *      -> chenge les valeurs de frame.getX etgetY...
         *  ça veur dire que je dois
         */
        if (messagesArchive.size() <= 0) return;
        //STEP 1 : remove from messagesArchive, replace in messages
        Message message = messagesArchive.remove(messagesArchive.size() - 1).getMessage();
        Log.d("CANCEL", String.format("Just picked %s", message.getExcerpt()));
        this.messages.add(0, message);
//        List<Message> messagesCopy = new ArrayList<Message>(this.messages);
//        notifyDataSetChanged();
//        this.refreshWithMessages(messagesCopy);
        // remove last NewsfeedMessageArchive from messageArchive
        // set it as first elem in messages
        // trigger the right animation
    }


}
