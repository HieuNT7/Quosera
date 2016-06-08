package project.iuh.hh.quosera.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import project.iuh.hh.quosera.information.Information;
import project.iuh.hh.quosera.R;
import project.iuh.hh.quosera.entites.Account;
import project.iuh.hh.quosera.parse.ParseAccountHelper;
import project.iuh.hh.quosera.utils.Utils;

public class LoginActivity extends Activity {
    EditText edtemail,edtpassword;
    CheckBox cbsave ;
    Button btn_login;
    TextView tv_linksignup,tv_title;

    final String TAG = "Login Activity";
    boolean isUserFirstTime;
    public static final String PREF_USER_FIRST_TIME = "user_first_time";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MapControl();
        isUserFirstTime = Boolean.valueOf(Utils.readSharedSetting(LoginActivity.this, PREF_USER_FIRST_TIME, "true"));
        if (isUserFirstTime) {
            Intent introIntent = new Intent(LoginActivity.this, IntroActivity.class);
            introIntent.putExtra(PREF_USER_FIRST_TIME, isUserFirstTime);
            startActivity(introIntent);
        }

        //config font
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/OleoScript-Regular.ttf");
        tv_title.setTypeface(typeface);

        //Share preferences
        restoringPreferences();
        cbsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savingPreferences();
            }
        });
        edtemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                savingPreferences();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                savingPreferences();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        tv_linksignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void savingPreferences() {
        SharedPreferences pre = getSharedPreferences(TAG,MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        boolean checkbox = cbsave.isChecked();
        if (!checkbox)
            editor.clear();
        else
        {
            editor.putString("email", edtemail.getText().toString());
            editor.putString("password", edtpassword.getText().toString());
            editor.putBoolean("checked", checkbox);
        }
        editor.commit();
    }

    private void restoringPreferences() {
        SharedPreferences pre = getSharedPreferences(TAG, MODE_PRIVATE);
        boolean checkbox  = pre.getBoolean("checked",false);
        cbsave.setChecked(checkbox);
        if (checkbox)
        {
            edtemail.setText(pre.getString("email",""));
            edtpassword.setText(pre.getString("password",""));
            //if (!edtpassword.getText().equals("") && !edtemail.getText().equals(""))
            //    login();
        }

    }

    private void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }
        if (!Utils.isNetworkAvailable(this))
        {
            Toast.makeText(LoginActivity.this, "Network not available, Click ok to enable wifi", Toast.LENGTH_LONG);
            startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
            return;
        }

        btn_login.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String email = edtemail.getText().toString();
        final String password = edtpassword.getText().toString();

        // Implement your own authentication logic here.
        final ParseAccountHelper parseAccountHelper = new ParseAccountHelper();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        if (parseAccountHelper.getObjectById(email)==null)
                            onLoginFailed();
                        else {
                            Account acc = parseAccountHelper.getObjectById(email);
                            if (SupportHashing.enCryp(password)!=acc.getPassword())
                                onLoginFailed();
                            else
                            {
                                Information.email = email;
                                onLoginSuccess();
                            }

                        }
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    private boolean validate() {
        boolean valid = true;

        String email = edtemail.getText().toString();
        String password = edtpassword.getText().toString();

        //check email
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtemail.setError("Enter a valid email address");
            valid = false;
        } else {
            edtemail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            edtpassword.setError("Between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            edtpassword.setError(null);
        }

        return valid;
    }
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        btn_login.setEnabled(true);
    }
    public void onLoginSuccess() {
        btn_login.setEnabled(true);
        Log.w("WTF",Information.email);
        Intent intent_main = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent_main);
    }

    private void MapControl() {
        edtemail = (EditText)findViewById(R.id.edtemail_lg);
        edtpassword = (EditText)findViewById(R.id.edtpassword_lg);
        cbsave = (CheckBox)findViewById(R.id.cbsave);
        btn_login = (Button)findViewById(R.id.btn_login);
        tv_linksignup = (TextView)findViewById(R.id.link_signup);
        tv_title = (TextView)findViewById(R.id.tvtitle);
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
