package project.iuh.hh.quosera.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import project.iuh.hh.quosera.R;
import project.iuh.hh.quosera.entites.Account;
import project.iuh.hh.quosera.entites.User;
import project.iuh.hh.quosera.information.Information;
import project.iuh.hh.quosera.parse.ParseAccountHelper;
import project.iuh.hh.quosera.parse.ParseUserHelper;
import project.iuh.hh.quosera.utils.Utils;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class EditProfileActivity extends AppCompatActivity{

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    // UI references.
    private EditText mFirstName, mLastName, mPasswordView;
    Spinner mSpinMonth, mSpinDay, mSpinYear;
    Button btnOk, btnCancel;
    final String TAG = "Edit Profile Activity = ";
    String email = Information.email;
    final ParseUserHelper parseUserHelper = new ParseUserHelper();
    final ParseAccountHelper parseAccountHelper = new ParseAccountHelper();
    User user = parseUserHelper.getObjectById(email);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_edit_profile);
        getcontrols();
        //Render Spinner
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

        //Render data on textview
        mFirstName.setText(user.getFirstname());
        mLastName.setText(user.getLastname());

        String birthday = user.getBirthday();
        String [] ele_birthday = {"", "", ""};
        int seq = 0;
        for(int i=0; i<birthday.length(); i++)
        {
            if(birthday.charAt(i) == '/')
                seq++;
            else
                ele_birthday[seq] += birthday.charAt(i);
        }
        SetupSpinner(mSpinDay,arrayday, ele_birthday[0]);
        SetupSpinner(mSpinMonth, arraymonth, ele_birthday [1]);
        SetupSpinner(mSpinYear, arrayyear, ele_birthday[2]);

        btnOk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptEdit();
            }
        });
        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfileActivity.this.finish();
            }
        });
    }

    private void SetupSpinner(Spinner spinner, ArrayList<String> arr, String val) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if(!val.equals(null)){
            int pos = adapter.getPosition(val);
            spinner.setSelection(pos);
        }
    }


    private void getcontrols() {
        mFirstName = (EditText) findViewById(R.id.edt_firstname);
        mLastName = (EditText) findViewById(R.id.edt_lastname);
        mPasswordView = (EditText) findViewById(R.id.edt_password);
        mSpinDay = (Spinner) findViewById(R.id.eSpinDay);
        mSpinMonth = (Spinner) findViewById(R.id.eSpinMonth);
        mSpinYear= (Spinner) findViewById(R.id.eSpinYear);
        btnOk = (Button) findViewById(R.id.btn_edt_ok);
        btnCancel = (Button) findViewById(R.id.btn_edt_cancel);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptEdit() {
        // Reset errors.
        mPasswordView.setError(null);

        // Store values at the time of the edit attempt.
        final String firstname = mFirstName.getText().toString();
        final String lastname = mLastName.getText().toString();
        final String password = mPasswordView.getText().toString();
        final String birthday = mSpinDay.getSelectedItem().toString() + "/" + mSpinMonth.getSelectedItem().toString() + "/" + mSpinYear.getSelectedItem().toString();

        boolean cancel = false;
        View focusView = null;

        //Check for a valid first name, if the user entered one
        if(TextUtils.isEmpty(firstname)){
            mFirstName.setError(getString(R.string.error_empty_firstname));
            focusView = mFirstName;
            cancel = true;
        }
        //Check for a valid last name, if the user entered one
        if(TextUtils.isEmpty(lastname)){
            mLastName.setError(getString(R.string.error_empty_lastname));
            focusView = mLastName;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (isPasswordValid(password)==false) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt edit and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // perform the user edit attempt.

            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setBirthday(birthday);

            //Set password into account after validate
            final long passwordencryp = SupportHashing.enCryp(password);
            Account account = parseAccountHelper.getObjectById(email);
            account.setPassword(passwordencryp);

            if(!parseUserHelper.UpdateUser(user) || !parseAccountHelper.updateAccount(account))
                Toast.makeText(EditProfileActivity.this, "Co gi do sai sai dau day", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(EditProfileActivity.this, "Update profile successfully", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean isPasswordValid(String password) {
        if (password.length() < 4 || password.length() > 10)
            return false;
        else
            return true;
    }
    /*
    *
    *   Auto complete email
    *
    */

    /*
    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                populateAutoComplete();
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }


    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(EditProfileActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
    }
    */
}

