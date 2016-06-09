package yhanbl.yhanbl.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import yhanbl.yhanbl.R;
import yhanbl.yhanbl.model.Message;

/**
 * Created by mrgn on 09/06/2016.
 */
public class NewsfeedAdapter extends BaseAdapter {
    private List<Message> messages;
    private List<Message> messagesToReadLater;
    private List<Message> messagesToDelete;
    //parsing XML + instanciation des vues
    //charger les cellules depuis le XML
    private LayoutInflater layoutInflater;

    public NewsfeedAdapter(Context context) {
        messages = new ArrayList<Message>();
        messagesToReadLater = new ArrayList<Message>();
        messagesToDelete  = new ArrayList<Message>();

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

    public Message getMessage(int position) { return (Message) getItem(position); }

    public void remove(int position) {
        messages.remove(position);
    }

    @Override
    public View getView(final int position, View viewFromCache, ViewGroup parent) {

        //if empty cache
        //  create cache
        if(viewFromCache == null){
            //parent = listview
            //attachToParent = false OBLIGATOIRE
            //création d'un cellule à partir du layout item newsfeed
            viewFromCache = layoutInflater.inflate(R.layout.item_newsfeed, parent, false);
            //create cache tag with a row movie
            viewFromCache.setTag(new NewsfeedItemHolder(viewFromCache));
        }

        // get current message
        Message message = messages.get(position);
        // get holder from cache
        NewsfeedItemHolder newsfeedItemHolder = (NewsfeedItemHolder) viewFromCache.getTag();
        //update holder with the message
        newsfeedItemHolder.refreshWithMessage(message);

        return viewFromCache;
    }

    public void setMessageToReadLater(Message message) {
        Log.d("ADAPTER", "Set to read later");
        messagesToReadLater.add(message);
    }

    public void setMessageToDelete(Message message) {
        Log.d("ADAPTER", "Set to delete");
        messagesToDelete.add(message);
    }

    public void refreshWithMessages(List<Message> messages) {
        this.messages.clear();
        this.messages.addAll(messages);

        notifyDataSetChanged();
    }


}
