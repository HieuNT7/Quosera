package project.iuh.hh.quosera.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import project.iuh.hh.quosera.R;
import project.iuh.hh.quosera.entites.User;
import project.iuh.hh.quosera.information.Information;
import project.iuh.hh.quosera.parse.ParseUserHelper;
import project.iuh.hh.quosera.utils.Utils;

public class ProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tv_username, tv1, tv2, tv3, tv4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_profile);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        String email = bundle.getString("email");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Choose edit Profile", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(getBaseContext(), EditProfileActivity.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getControls();
        if(!email.equals(Information.email))
            fab.setVisibility(View.INVISIBLE);
        else
            fab.setVisibility(View.VISIBLE);

        renderLayout(email);
    }

    private void renderLayout(String email) {
        ParseUserHelper puh = new ParseUserHelper();
        User user = puh.getObjectById(email);
        tv_username.setText(user.getLastname() + " " + user.getFirstname());
        tv1.setText(user.getEmail());
        tv2.setText(user.getBirthday());
        if(user.getGender() == true)
        tv3.setText("Male");
        else
        tv3.setText("Female");
        tv4.setText(user.getIntroduction());
    }


    public void getControls()
    {
        tv_username = (TextView) findViewById(R.id.tv_username);
        tv1 = (TextView) findViewById(R.id.tvNumber1);
        tv2 = (TextView) findViewById(R.id.tvNumber2);
        tv3 = (TextView) findViewById(R.id.tvNumber3);
        tv4 = (TextView) findViewById(R.id.tvNumber4);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
