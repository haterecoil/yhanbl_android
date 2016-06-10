package yhanbl.yhanbl.adapter;

import android.view.View;
import android.widget.TextView;

import yhanbl.yhanbl.R;
import yhanbl.yhanbl.model.Message;

/**
 * Created by mrgn on 09/06/2016.
 */
public class NewsfeedMessageHolder {

    private final TextView helloText;

    public NewsfeedMessageHolder(View flingContainer) {
        helloText = (TextView) flingContainer.findViewById(R.id.helloText);
    }

    public void refreshWithMessage(Message message) {
        helloText.setText(message.getExcerpt());
    }

}
