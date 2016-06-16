package yhanbl.yhanbl;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by Paul on 15/06/2016.
 */
public class InboxActivity extends ActionBarActivity {

    private LayoutInflater mInflater;
    private View mMessageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inbox_activity);

        setTheme(R.style.AppTheme);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.inbox_toolbar);
        setSupportActionBar(myToolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.inbox_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_create) {
            Intent intent = new Intent(getApplicationContext(), MessageNewActivity.class);
            startActivity(intent);
            return true;
        } else if ( id == R.id.action_inbox ) {
            Intent intent = new Intent(getApplicationContext(), InboxActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*public void onReplyClick(View v) {
        Toast.makeText(this, "REPLY activated", Toast.LENGTH_SHORT).show();
    }*/
}