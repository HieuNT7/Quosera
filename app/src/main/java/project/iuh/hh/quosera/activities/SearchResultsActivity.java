package project.iuh.hh.quosera.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Nguyen Hoang on 17/04/2016.
 */
public class SearchResultsActivity extends Activity {

    /*
    C/C++
    C#
    JAVA
    ANDROID
    ASP.NET
    SQLITE
    HTML CSS
    GRAPHICS DESIGN*/

    private static final String[]tag = new String[]{"C/C++", "C#", "JAVA", "ANDROID", "ASP.NET",
            "SQLITE", "HTML", "CSS", "GRAPHICS DESIGN"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
        //    Log.w("HUYHOANG", query);
            //use the query to search your data somehow
        }
    }
}
