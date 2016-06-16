package yhanbl.yhanbl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.graphics.Typeface;

/**
 * Created by Paul on 16/06/2016.
 */
public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_activity);

        // Font
        String fontPath = "fonts/Arca-Heavy.ttf";
        TextView text = (TextView) findViewById(R.id.textView);
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        text.setTypeface(tf);
    }

    public void myClickHandler(View target) {
        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(intent);
    }
}
