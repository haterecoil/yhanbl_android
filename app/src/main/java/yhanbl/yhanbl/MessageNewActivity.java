package yhanbl.yhanbl;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import yhanbl.yhanbl.fragment.MessageNewPictureFragment;
import yhanbl.yhanbl.fragment.MessageNewTextFragment;
import yhanbl.yhanbl.model.Message;

public class MessageNewActivity extends AppCompatActivity
        implements FragmentManager.OnBackStackChangedListener{

    private Message mMessage;
    private LayoutInflater mInflater;
    private View mMessageView;
    private boolean mShowingBack = false;
    private Menu mMenu;
    private String mCurrentPhotoPath;
    private MessageNewPictureFragment messageNewPictureFragment;
    private MessageNewTextFragment messageNewTextFragment;
    private String MnpfTag;
    /**
     * A handler object, used for deferring UI operations.
     */
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_new_activity);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.message_new_toolbar);
        setSupportActionBar(myToolbar);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Log.d("MNA", String.format("message layout id %d ", R.id.message_new_frame));
            messageNewTextFragment = new MessageNewTextFragment();
            messageNewPictureFragment = new MessageNewPictureFragment();
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.message_new_frame, messageNewTextFragment, MnpfTag)
                    .commit();
        } else {
            mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
            messageNewPictureFragment = (MessageNewPictureFragment) getFragmentManager()
                    .getFragment(savedInstanceState, MnpfTag);

        }


        Log.d("MNA", "else");
        getFragmentManager().addOnBackStackChangedListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.newmessage_menu, menu);
        mMenu = menu;

        if (mShowingBack) {
            mMenu.findItem(R.id.action_addpicture).setVisible(false);
            mMenu.findItem(R.id.action_edit).setVisible(true);
        } else {
            mMenu.findItem(R.id.action_addpicture).setVisible(true);
            mMenu.findItem(R.id.action_edit).setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_edit) {
            flipCard();
        } else if ( id == R.id.action_addpicture) {
            flipCard();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setButtonsListeners() {
        Log.d("LISTEN", "to your heart");
    }

    private void flipCard() {

        if (mShowingBack) {
            Log.d("MNA", "showing bac");
            getFragmentManager().popBackStack();
//            mMenu.findItem(R.id.action_addpicture).setVisible(false);
//            mMenu.findItem(R.id.action_edit).setVisible(true);
//
//            mShowingBack = false;

            // hide photograph, show pencil
            return;
        }

        Log.d("MNA", "showing front");

/*        mMenu.findItem(R.id.action_addpicture).setVisible(true);
        mMenu.findItem(R.id.action_edit).setVisible(false);*/

        // Flip to the back.

        mShowingBack = true;

        // Create and commit a new fragment transaction that adds the fragment for
        // the back of the card, uses custom animations, and is part of the fragment
        // manager's back stack.

        getFragmentManager()
                .beginTransaction()

                // Replace the default fragment animations with animator resources
                // representing rotations when switching to the back of the card, as
                // well as animator resources representing rotations when flipping
                // back to the front (e.g. when the system Back button is pressed).
                .setCustomAnimations(
                        R.animator.card_flip_right_in,
                        R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in,
                        R.animator.card_flip_left_out)

                // Replace any fragments currently in the container view with a
                // fragment representing the next page (indicated by the
                // just-incremented currentPage variable).
                .replace(R.id.message_new_frame, messageNewPictureFragment)

                // Add this transaction to the back stack, allowing users to press
                // Back to get to the front of the card.
                .addToBackStack(null)

                // Commit the transaction.
                .commit();

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
    protected void onSaveInstanceState(Bundle outState) {
        if (outState == null) {
            Log.d("FUCK", "outstate is null");
        }
        if (messageNewPictureFragment == null) {
            Log.d("FUCK", "mnpf is null");
        }
        super.onSaveInstanceState(outState);

        if ( outState != null && messageNewPictureFragment != null) {
            //Save the fragment's instance
            getFragmentManager().putFragment(outState, MnpfTag, messageNewPictureFragment);
        }
    }
}
