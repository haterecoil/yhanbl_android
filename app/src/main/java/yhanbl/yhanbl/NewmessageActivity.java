package yhanbl.yhanbl;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import yhanbl.yhanbl.model.Message;

public class NewmessageActivity extends ActionBarActivity {

    private Message mMessage;
    private LayoutInflater mInflater;
    private View mMessageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newmessage_activity);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.newmessage_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.newmessage_menu, menu);
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

}
