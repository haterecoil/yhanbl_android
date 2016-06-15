package yhanbl.yhanbl.adapter;

import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import yhanbl.yhanbl.R;
import yhanbl.yhanbl.model.Message;
import yhanbl.yhanbl.view.SquareImageView;

/**
 * Created by mrgn on 09/06/2016.
 */
public class NewsfeedMessageHolder {

    private final TextView messageText;
    private final SquareImageView messageImage;
    private final View mFLingContainer;

    public NewsfeedMessageHolder(View flingContainer) {
        mFLingContainer = flingContainer;

        messageText = (TextView) flingContainer.findViewById(R.id.newsfeed_message_text);
        messageImage = (SquareImageView) flingContainer.findViewById(R.id.newsfeed_message_image);
    }

    public void refreshWithMessage(Message message) {
        messageText.setText(message.getExcerpt());
        Glide.with(mFLingContainer.getContext()).load(message.getPictureUrl()).into(messageImage);
    }

}
