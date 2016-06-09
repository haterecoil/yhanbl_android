package yhanbl.yhanbl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import yhanbl.yhanbl.adapter.NewsfeedAdapter;
import yhanbl.yhanbl.model.Message;
import yhanbl.yhanbl.swipecards.SwipeFlingAdapterView;

public class NewsfeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);

        //link view to container
        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        List<Message> messages = new ArrayList<>();
        messages.add(new Message(
                        "http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg",
                        "Message 1"));
        messages.add(new Message(
                        "http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg",
                        "Message 2"));
        messages.add(new Message(
                        "http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg",
                        "Message 3"));
        messages.add(new Message(
                        "http://i.ytimg.com/vi/PnxsTxV8y3g/maxresdefault.jpg",
                        "Message 4"));

        // get an adapter
        final NewsfeedAdapter newsfeedAdapter = new NewsfeedAdapter(this);
        newsfeedAdapter.refreshWithMessages(messages);

        //set the listener and the adapter
        flingContainer.setAdapter(newsfeedAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener(){

            @Override
            public void removeFirstObjectInAdapter() {
                Message message = newsfeedAdapter.getMessage(0);
                Log.d("LIST", String.format("Gonna remove %s", message.getExcerpt()));
                newsfeedAdapter.remove(0);
                newsfeedAdapter.notifyDataSetChanged();
                Log.d("LIST", String.format("New item is %s", newsfeedAdapter.getMessage(0).getExcerpt()));
            }

            // delete Message
            // this method seems to execute after the remve but still
            // returns the removed object
            @Override
            public void onLeftCardExit(Object dataObject) {
                Log.d("LIST", "onLeftCardExit");
                if (dataObject.getClass() == Message.class) {
                    newsfeedAdapter.setMessageToDelete((Message) dataObject);
                }
            }

            // add Message to "Read Later"
            @Override
            public void onRightCardExit(Object dataObject) {
                Log.d("LIST", "onRightCardExit");
                if (dataObject.getClass() == Message.class) {
                    newsfeedAdapter.setMessageToReadLater((Message) dataObject);
                }
            }

            // When stack is empty,
            // delete all "deleted" messages and
            // go to "all messages" activity
            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                Log.d("LIST", "onAdapterAboutToEmpty");
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
            }
        });



    }
}
