package yhanbl.yhanbl;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by mrgn on 12/06/2016.
 */
public class YhanblApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }

}
