package yhanbl.yhanbl;

import android.app.FragmentManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by Paul on 16/06/2016.
 */
public class MessageViewActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    private boolean mShowingBack = false;
    private Menu mMenu;

        private Handler mHandler = new Handler();
        private CardBackFragment cardBack;
        private CardFrontFragment cardFront;
        public String extraText;
        public String extraTitle;
        public String extraImage;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.message_view_frame);

            Toolbar myToolbar = (Toolbar) findViewById(R.id.message_new_toolbar);
            setSupportActionBar(myToolbar);


            ActionBar ab = getSupportActionBar();
            ab.setDisplayHomeAsUpEnabled(true);

            extraTitle = getIntent().getStringExtra("title");
            extraText = getIntent().getStringExtra("text");
//            Integer image_id = getIntent().getIntExtra("image_id", 0);
//            Log.d("HUM", String.format("%d", image_id));
//            extraImage = (ImageView) findViewById(image_id);
            extraImage = getIntent().getStringExtra("image_tag");

            if (savedInstanceState == null) {
                // If there is no saved instance state, add a fragment representing the
                // front of the card to this activity. If there is saved instance state,
                // this fragment will have already been added to the activity.
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, new CardFrontFragment())
                        .commit();
            } else {
                mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
            }

            // Monitor back stack changes to ensure the action bar shows the appropriate
            // button (either "photo" or "info").
            getFragmentManager().addOnBackStackChangedListener(this);
        }

    private void flipCard() {
        if (mShowingBack) {
            getFragmentManager().popBackStack();
            return;
        }

        // Flip to the back.

        mShowingBack = true;

        // Create and commit a new fragment transaction that adds the fragment for the back of
        // the card, uses custom animations, and is part of the fragment manager's back stack.

        getFragmentManager()
                .beginTransaction()

                // Replace the default fragment animations with animator resources representing
                // rotations when switching to the back of the card, as well as animator
                // resources representing rotations when flipping back to the front (e.g. when
                // the system Back button is pressed).
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in, R.animator.card_flip_left_out)

                // Replace any fragments currently in the container view with a fragment
                // representing the next page (indicated by the just-incremented currentPage
                // variable).
                .replace(R.id.container, new CardBackFragment())

                // Add this transaction to the back stack, allowing users to press Back
                // to get to the front of the card.
                .addToBackStack(null)

                // Commit the transaction.
                .commit();

        // Defer an invalidation of the options menu (on modern devices, the action bar). This
        // can't be done immediately because the transaction may not yet be committed. Commits
        // are asynchronous in that they are posted to the main thread's message loop.
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                invalidateOptionsMenu();
            }
        });
    }

    @Override
    public void onBackStackChanged() {
        mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);

        // When the back stack changes, invalidate the options menu (action bar).
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.viewmessage_menu, menu);
        mMenu = menu;

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_revert) {
            flipCard();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A fragment representing the front of the card.
     */
    public static class CardFrontFragment extends Fragment {
        public CardFrontFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View myFrag = inflater.inflate(R.layout.message_view_picture, container, false);
//            ImageView image = (ImageView) myFrag.findViewById(R.id.view_image);
//            MessageViewActivity act = (MessageViewActivity) getActivity();
//            Log.d("yolo", act.extraImage);
//            int drawableId = -1;
//            try {
//                Class res = R.drawable.class;
//                Field field = res.getField(act.extraImage);
//                drawableId = field.getInt(null);
//            }
//            catch (Exception e) {
//                Log.e("MyTag", "Failure to get drawable id.", e);
//            }
//            image.setImageResource(drawableId);
            return myFrag;
        }

        public static int getResId(String resName, Class<?> c) {

            try {
                Field idField = c.getDeclaredField(resName);
                return idField.getInt(idField);
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }
    }

    /**
     * A fragment representing the back of the card.
     */
    public static class CardBackFragment extends Fragment {
        public CardBackFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View myFrag = inflater.inflate(R.layout.message_view_text, container, false);
            TextView title = (TextView) myFrag.findViewById(R.id.textView3);
            TextView text = (TextView) myFrag.findViewById(R.id.textView4);
            MessageViewActivity act = (MessageViewActivity) getActivity();
            title.setText(act.extraTitle);
            Log.d("HUM", act.extraTitle);
            text.setText(act.extraText);
            Log.d("HUM", act.extraText);
            return myFrag;
        }
    }
}
