package project.iuh.hh.quosera;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Nguyen Hoang on 13/03/2016.
 */
public class QuoseraApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "XK7XsqASGY3fJrVrq4VoBtHR1nDg6wKRUFV17uT9", "x6fPCBxVZoL9uaDBgpMXNzZ5CBYA7M2dxagxOLbN");
    }
}
