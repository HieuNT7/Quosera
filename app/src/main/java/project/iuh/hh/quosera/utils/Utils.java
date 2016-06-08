package project.iuh.hh.quosera.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.graphics.drawable.DrawableCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import project.iuh.hh.quosera.R;
import project.iuh.hh.quosera.activities.MainActivity;
import project.iuh.hh.quosera.activities.SettingActivity;
import project.iuh.hh.quosera.information.Information;
import project.iuh.hh.quosera.parse.ParseUserHelper;

/**
 * Created by HieuNT on 27/02/2016.
 */
public class Utils {

    private static final String PREFERENCES_FILE = "materialsample_settings";
    private static int cTheme = 0;
    public final static int MY_THEME = 1;
    public final static int APP_THEME = 2;

    public static int getToolbarHeight (Context context)
    {
        int height = (int) context.getResources().getDimension(R.dimen.abc_action_bar_default_height_material);
        return height;
    }

    public static int getStatusbarHeight (Context context)
    {
        int height = (int) context.getResources().getDimension(R.dimen.statusbar_size);
        return height;
    }

    public static Drawable tintMyDrawable(Drawable drawable, int color) {

        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, color);
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
        return drawable;
    }

    public static String readSharedSetting(Context ctx, String settingName, String defaultValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getString(settingName, defaultValue);
    }

    public static void saveSharedSetting(Activity activity, String prefUserFirstTime, String aFalse) {
        SharedPreferences sharedPref = activity.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(prefUserFirstTime, aFalse);
        editor.apply();
    }
    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
    public static boolean islike(String email,String postid)
    {
        if (email.isEmpty())
            return false;
        List<String> arrlikes = (new ParseUserHelper()).getLikesPost(email);
        if (arrlikes == null)
            return false;
        if (arrlikes.contains(postid))
            return true;
        return false;
    }
    public static boolean isfollowing(String email,String postid)
    {
        if (email.isEmpty())
            return false;
        List<String> arrFollows = (new ParseUserHelper()).getFollowPost(email);
        if (arrFollows==null)
            return false;
        if (arrFollows.contains(postid))
            return true;
        return false;
    }
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
    public static void changeTheme (SettingActivity activity, int theme)
    {
        cTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, MainActivity.class));
    }

    public static void onActivityCreateSetTheme(Activity activity) {
        switch (cTheme)
        {
            case MY_THEME:
                activity.setTheme(R.style.MyTheme); break;
            /*case APP_THEME:
                activity.setTheme(R.style.AppTheme); break;*/
        }
    }

}

