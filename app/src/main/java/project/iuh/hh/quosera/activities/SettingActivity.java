package project.iuh.hh.quosera.activities;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import project.iuh.hh.quosera.R;
import project.iuh.hh.quosera.utils.Utils;

public class SettingActivity extends AppCompatActivity {

    Switch aSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_setting);
        aSwitch = (Switch) findViewById(R.id.switch1);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Utils.changeTheme(SettingActivity.this, Utils.MY_THEME);
                //    MainActivity.toolbar.setBackgroundColor(getResources().getColor(R.color.green));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            MainActivity.tabHost.setBackground(getDrawable(R.color.light_green));
                        }
                    }
                }
                /*else {
                    Utils.changeTheme(SettingActivity.this, Utils.APP_THEME);
                    MainActivity.toolbar.setBackgroundColor(getResources().getColor(R.color.blue));

                }*/
            }
        });
    }
}
