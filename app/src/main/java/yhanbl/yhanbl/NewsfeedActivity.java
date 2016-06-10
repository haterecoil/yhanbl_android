package yhanbl.yhanbl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import yhanbl.yhanbl.adapter.NewsfeedAdapter;
import yhanbl.yhanbl.adapter.NewsfeedFlingAdapterView;
import yhanbl.yhanbl.model.Message;

public class NewsfeedActivity extends AppCompatActivity {

    private NewsfeedAdapter newsfeedAdapter;
    private NewsfeedFlingAdapterView flingContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsfeed_activity);

        //link view to container
        flingContainer = (NewsfeedFlingAdapterView) findViewById(R.id.newsfeed_fling_frame);

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
        newsfeedAdapter = new NewsfeedAdapter(this);

        //set the listener and the adapter
        flingContainer.setAdapter(newsfeedAdapter);
        flingContainer.setFlingListener(getFlingListener());

        setButtonsListeners();

        newsfeedAdapter.refreshWithMessages(messages);
    }

    private void setButtonsListeners() {
        findViewById(R.id.newsfeed_button_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flingContainer.getTopCardListener().selectLeft();
            }
        });

        findViewById(R.id.newsfeed_button_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flingContainer.getTopCardListener().selectRight();
            }
        });

        findViewById(R.id.newsfeed_button_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsfeedAdapter.cancelLastChoice();
            }
        });


    }

    // return all fling event listeners
    protected NewsfeedFlingAdapterView.onFlingListener getFlingListener() {
        return new NewsfeedFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                Message message = newsfeedAdapter.getMessage(0);
                Log.d("ACT Listener", String.format("Gonna remove %s", message.getExcerpt()));
                newsfeedAdapter.remove(0);
                newsfeedAdapter.notifyDataSetChanged();
                if (newsfeedAdapter.getCount() <= 0) {
                    Log.d("ACT-END", "The end");
                    return;
                }
                Log.d("ACT Listener", String.format("New item is %s", newsfeedAdapter.getMessage(0).getExcerpt()));
            }

            // delete Message
            // this method seems to execute after the remve but still
            // returns the removed object
            @Override
            public void onLeftCardExit(Object dataObject) {
                Log.d("ACT Listener", "onLeftCardExit");
                if (dataObject.getClass() == Message.class) {
                    newsfeedAdapter.setMessageToDelete((Message) dataObject);
                }
            }

            // add Message to "Read Later"
            @Override
            public void onRightCardExit(Object dataObject) {
                Log.d("ACT Listener", "onRightCardExit");
                if (dataObject.getClass() == Message.class) {
                    newsfeedAdapter.setMessageToReadLater((Message) dataObject);
                }
            }

            // When stack is empty,
            // delete all "deleted" messages and
            // go to "all messages" activity
            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                Log.d("ACT Listener", "onAdapterAboutToEmpty");
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
            }
        };
    }
}
