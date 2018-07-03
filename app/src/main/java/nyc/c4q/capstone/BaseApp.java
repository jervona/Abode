package nyc.c4q.capstone;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by jervon.arnoldd on 7/2/18.
 */

public class BaseApp extends Application {

    public MyComponent myComponent;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myComponent = DaggerMyComponent.create();
    }

    public MyComponent getMyComponent() {
        return myComponent;
    }
}

