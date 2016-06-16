package yhanbl.yhanbl;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    public void readMe(View v) {
        Log.d("READ", "ME");
        LinearLayout ll = (LinearLayout) v;
        ImageView image = (ImageView) ll.getChildAt(0);
        Log.d("READ IMG", String.format("%s", image.getDrawable()));
        TextView title = (TextView) ll.getChildAt(1);
        Log.d("READ TXT", String.format("%s", title.getText()));
        TextView text = (TextView) ll.getChildAt(2);
        Log.d("READ TXT", String.format("%s", text.getText()));

        Intent intent = new Intent(getBaseContext(), MessageViewActivity.class);
        intent.putExtra("image_tag", (String) image.getTag());
        intent.putExtra("title", title.getText());
        intent.putExtra("text", text.getText());
        startActivity(intent);

    }


    /*public void onReplyClick(View v) {
        Toast.makeText(this, "REPLY activated", Toast.LENGTH_SHORT).show();
    }*/
}