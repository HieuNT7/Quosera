package project.iuh.hh.quosera.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import project.iuh.hh.quosera.R;
import project.iuh.hh.quosera.entites.Account;
import project.iuh.hh.quosera.entites.User;
import project.iuh.hh.quosera.parse.ParseAccountHelper;
import project.iuh.hh.quosera.parse.ParseUserHelper;
import project.iuh.hh.quosera.utils.Utils;

public class SignupActivity extends Activity {
    Spinner spinMonth,spinDay,spinYear;
    EditText edtpassword,edtemail,edtemail1,edtfirstname,edtlastname;
    CheckBox checkboxsex;

    Button btn_signup;
    TextView tv_linklogin,tv_title;
    final String TAG = "Signup Activity = ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_signup);
        MapControl();
        //config font
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/OleoScript-Regular.ttf");
        tv_title.setTypeface(typeface);

        //
        ArrayList<String> arrayday = new ArrayList<String>();
        for (Integer i=1;i<=31;i++)
            arrayday.add(i.toString());

        ArrayList<String> arraymonth = new ArrayList<String>();
        arraymonth.add("January");arraymonth.add("February");arraymonth.add("March");
        arraymonth.add("April");arraymonth.add("May");arraymonth.add("June");
        arraymonth.add("July");arraymonth.add("August");arraymonth.add("September");
        arraymonth.add("October");arraymonth.add("November");arraymonth.add("December");


        ArrayList<String> arrayyear = new ArrayList<String>();
        for (Integer i=1950;i<=2016;i++)
            arrayyear.add(i.toString());
        //
        SetupSpinner(spinDay,arrayday);
        SetupSpinner(spinMonth,arraymonth);
        SetupSpinner(spinYear,arrayyear);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();

            }
        });
        tv_linklogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        btn_signup.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();
        final String firstname = edtfirstname.getText().toString();
        final String lastname = edtlastname.getText().toString();
        final String email = edtemail.getText().toString();
        final String password = edtpassword.getText().toString();
        final long passwordencryp = SupportHashing.enCryp(password);
        final Boolean sex = checkboxsex.isChecked();
        final String birthday = spinDay.getSelectedItem().toString() + "/" + spinMonth.getSelectedItem().toString() + "/" + spinYear.getSelectedItem().toString();
        Log.w(TAG,"birthday" + birthday);

        // TODO: Implement your own signup logic here.
        final ParseUserHelper parseUserHelper = new ParseUserHelper();
        final ParseAccountHelper parseAccountHelper = new ParseAccountHelper();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if(parseUserHelper.postObject(new User(firstname,lastname,email,sex,birthday)))
                            Log.w(TAG,"true");
                        else
                            Log.w(TAG,"false");
                        parseAccountHelper.postObject(new Account(email,passwordencryp));
                        onSignupSuccess();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    private void onSignupSuccess() {
        btn_signup.setEnabled(true);
        Toast.makeText(getBaseContext(), "Signup success", Toast.LENGTH_LONG).show();
        finish();
    }

    private boolean validate() {
        boolean valid = true;

        String firstname = edtfirstname.getText().toString();
        String lastname = edtlastname.getText().toString();
        String email = edtemail.getText().toString();
        String email1 = edtemail1.getText().toString();
        String password = edtpassword.getText().toString();

        //check first name
        if (firstname.isEmpty())
        {
            valid = false;
            edtfirstname.setError("What's your name?");
        }
        else
            edtfirstname.setError(null);
        //check last name
        if (lastname.isEmpty())
        {
            valid = false;
            edtlastname.setError("What's your name?");
        }
        else
            edtlastname.setError(null);
        //check email
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtemail.setError("Enter a valid email address");
            valid = false;
        } else {
            edtemail.setError(null);
        }
        //check email 1
        if (!email1.equals(email))
        {
            edtemail1.setError("Not match");
            valid = false;
        }
        else
            edtemail1.setError(null);
        //check password
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            edtpassword.setError("Between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            edtpassword.setError(null);
        }
        //check dupicate
        ParseUserHelper parseUserHelper = new ParseUserHelper();
        if (parseUserHelper.getObjectById(email)!=null)
        {
            valid = false;
            Toast.makeText(this,"Email has already",Toast.LENGTH_LONG).show();
        }
        return valid;
    }



    private void SetupSpinner(Spinner spinner,ArrayList<String> arr)
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void MapControl() {
        edtfirstname = (EditText)findViewById(R.id.edtfirstname);
        edtlastname = (EditText)findViewById(R.id.edtlastname);
        edtemail = (EditText)findViewById(R.id.edtemail);
        edtemail1 = (EditText)findViewById(R.id.edtemail1);
        edtpassword = (EditText)findViewById(R.id.edtpassword);
        spinDay = (Spinner)findViewById(R.id.spinDay);
        spinMonth = (Spinner)findViewById(R.id.spinMonth);
        spinYear = (Spinner)findViewById(R.id.spinYear);
        checkboxsex = (CheckBox)findViewById(R.id.chexkboxsex);
        btn_signup = (Button)findViewById(R.id.btn_signup);
        tv_linklogin =  (TextView)findViewById(R.id.link_login);
        tv_title = (TextView)findViewById(R.id.tvtitle_su);
    }
    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "SignUp Failed", Toast.LENGTH_LONG).show();
        btn_signup.setEnabled(true);
    }
    private void onSignupFailed(String s)
    {
        Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show();
        btn_signup.setEnabled(true);
    }
}
