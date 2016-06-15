package yhanbl.yhanbl;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import yhanbl.yhanbl.adapter.NewsfeedAdapter;
import yhanbl.yhanbl.adapter.NewsfeedFlingAdapterView;
import yhanbl.yhanbl.model.AuthenticationTokenManager;
import yhanbl.yhanbl.model.Message;
import yhanbl.yhanbl.model.NewsfeedMessageArchive;
import yhanbl.yhanbl.network.ServiceGenerator;
import yhanbl.yhanbl.network.YhanblClient;

public class NewsfeedActivity extends ActionBarActivity {

    private NewsfeedAdapter newsfeedAdapter;
    private NewsfeedFlingAdapterView flingContainer;
    private GetNewMessagesTask mGetNewMessagesTask;
    private NewsfeedFlingAdapterView mFlingFrameView;
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("NewsAct", "on create ");
        super.onCreate(savedInstanceState);

        setTheme(R.style.AppTheme);

        setContentView(R.layout.newsfeed_activity);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.newsfeed_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Log.d("ACTION BAR", String.format("",getSupportActionBar().getHeight()));

        mFlingFrameView = (NewsfeedFlingAdapterView) findViewById(R.id.newsfeed_fling_frame);
        mProgressView = findViewById(R.id.newsfeed_progress);

        //link view to container
        flingContainer = mFlingFrameView;

        List<Message> messages = new ArrayList<>();

        // get an adapter
        newsfeedAdapter = new NewsfeedAdapter(this);

        //set the listener and the adapter
        flingContainer.setAdapter(newsfeedAdapter);
        flingContainer.setFlingListener(getFlingListener());

        setButtonsListeners();

        attemptGetNewMessages();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.newsfeed_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_create) {
            Intent intent = new Intent(getApplicationContext(), NewmessageActivity.class);
            startActivity(intent);
            return true;
        } else if ( id == R.id.action_inbox ) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.newsfeed_menu, menu);

        return super.onPrepareOptionsMenu(menu);
    }*/

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
                flingContainer.getTopCardListener().cancel();
            }
        });
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptGetNewMessages() {

        if (mGetNewMessagesTask != null) {
            return;
        }

        boolean cancel = false;
        View focusView = null;

        showProgress(true);
        mGetNewMessagesTask = new GetNewMessagesTask();
        mGetNewMessagesTask.execute((Void) null);
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

            @Override
            public void onCancel() {
                newsfeedAdapter.cancelLastChoice();
                flingContainer.removeAllViewsInLayout();
                newsfeedAdapter.notifyDataSetChanged();
            }
        };
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mFlingFrameView.setVisibility(show ? View.GONE : View.VISIBLE);
            mFlingFrameView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mFlingFrameView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mFlingFrameView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class GetNewMessagesTask extends AsyncTask<Void, Void, Boolean> {

        private AuthenticationTokenManager atManager;
        private List<Message> mResponseBody;

        GetNewMessagesTask() {
            atManager = new AuthenticationTokenManager(getBaseContext());
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            // ===== retrieve messages
            //set token
//            String auth_token = new AuthenticationTokenManager(getBaseContext()).getSerializedAuthenticationToken();
            AuthenticationTokenManager atManager = new AuthenticationTokenManager(getBaseContext());
            atManager.setAuthenticationToken(
                    "vcytJLlo1ax2Ny5jFkOicAlpf9bm1SnF0NxSpD/7xItk1WqlWDI3+eFuAuXm/SGW9QoO/ZJ3tN7s2LoBteJN6Q==",
                    "User0");
            String auth_token = atManager.getSerializedAuthenticationToken();

            Log.d("NewsAct", auth_token);
            YhanblClient yhanblClient = ServiceGenerator.createService(YhanblClient.class, auth_token);
            Call<List<Message>> call = yhanblClient.getNewMessages();

            Response<List<Message>> callResponse = null;

            try {
                // Simulate network access.
                callResponse = call.execute();
                mResponseBody = callResponse.body();
            } catch (IOException e) {
                Log.d("NewsFeed", "error on network access");
                e.printStackTrace();
            }

            if ( callResponse != null && callResponse.isSuccessful()) {
                Log.d("NewsFeed", "is successful");
                Log.d("NewsFeed", String.valueOf(callResponse.body()));
                return true;
            } else {
                Log.d("NewsFeed", "error !");
                Log.d("NewsFeed", callResponse.toString());
                return false;
            }

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mGetNewMessagesTask = null;


            if (success) {
//                finish();
                newsfeedAdapter.refreshWithMessages(mResponseBody);
                showProgress(false);
            } else {
                Log.d("NewsAct", "Hm big fail");
            }
        }

        @Override
        protected void onCancelled() {
            mGetNewMessagesTask = null;
            showProgress(false);
        }
    }
}
