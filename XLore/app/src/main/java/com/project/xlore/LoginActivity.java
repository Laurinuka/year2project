package com.project.xlore;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.project.xlore.data.User;
import com.project.xlore.data.UserRepository;
import com.project.xlore.job_list.JobListActivity;
import com.project.xlore.user_list.UserListActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>
{
    private static final int REQUEST_READ_CONTACTS = 0;
    private UserLoginTask mAuthTask = null;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        mEmailView = findViewById(R.id.login_text_email);
        populateAutoComplete();

        mPasswordView = findViewById(R.id.login_text_password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent)
            {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL)
                {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = findViewById(R.id.login_button_signin);
        mEmailSignInButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_container_form);
        mProgressView = findViewById(R.id.login_progress_loading);
    }

    /*
     * Attempts to sign in, specified by the login form.
     * Checks if a login task has already been instantiated. If so, return.
     * Errors are then reset and the email and password entered are retrieved.
     * Checks are then made to see if the email and password entered are valid.
     * If either are invalid, the login process is cancelled. Other, it continues.
     */
    private void attemptLogin()
    {
        if (mAuthTask != null)
            return;

        mEmailView.setError(null);
        mPasswordView.setError(null);

        boolean cancelLogin = false;
        View focusView = null;
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        if(!isEmailValid(email))
        {
            focusView = mEmailView;
            cancelLogin = true;
        }

        if (!isPasswordValid(password))
        {
            focusView = mPasswordView;
            cancelLogin = true;
        }

        if(cancelLogin)
        {
            focusView.requestFocus();
        }
        else
        {
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    /*
     * Checks if the email is empty, and that it contains an @ symbol to help ensure
     * it is a valid email address.
    */
    private boolean isEmailValid(String email)
    {
        if(TextUtils.isEmpty(email))
        {
            mEmailView.setError(getString(R.string.field_required));
            return false;
        }
        else if(!email.contains("@"))
        {
            mEmailView.setError(getString(R.string.invalid_email));
            return false;
        }
        return true;
    }

    /*
     * Checks if the password is empty, and that it is the minimum length.
    */
    private boolean isPasswordValid(String password)
    {
        if(TextUtils.isEmpty(password))
        {
            mPasswordView.setError(getString(R.string.field_required));
            return false;
        }
        else if(password.length() < 6)
        {
            mPasswordView.setError(getString(R.string.short_password));
            return false;
        }
        return true;
    }

    /*
     * Gets the role from the radio options, and then runs the corresponding conditional.
     * Then retrieves the corresponding users from the database, loops through the list and
     * adds it to the local ArrayList, which is then returned.
    */
    private ArrayList<User> retrieveDatabase()
    {
        String role = getRadioInput();
        UserRepository repo = new UserRepository(getApplicationContext());
        final ArrayList<User> list = new ArrayList<>();

        if(role.equals(getString(R.string.employee_string)))
        {
            repo.getEmployeeUsers().observe(this, new Observer<List<User>>()
            {
                @Override
                public void onChanged(@Nullable List<User> users)
                {
                    for(User user : users)
                        list.add(user);
                }
            });
        }
        else if(role.equals(getString(R.string.employer_string)))
        {
            repo.getEmployerUsers().observe(this, new Observer<List<User>>()
            {
                @Override
                public void onChanged(@Nullable List<User> users)
                {
                    for(User user : users)
                        list.add(user);
                }
            });
        }
        return list;
    }

    /*
     * Retrieves the radio buttons via their id, and then checks which one of them
     * has been selected. The corresponding string is then returned.
    */
    private String getRadioInput()
    {
        RadioButton employee = findViewById(R.id.login_radio_employee);
        RadioButton employer = findViewById(R.id.login_radio_employer);

        if(employee.isChecked())
            return getString(R.string.employee_string);
        else if(employer.isChecked())
            return getString(R.string.employer_string);
        return getString(R.string.none_string);
    }

    private void populateAutoComplete()
    {
        if (!mayRequestContacts())
            return;
        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts()
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED)
            return true;

        if (shouldShowRequestPermissionRationale(READ_CONTACTS))
        {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener()
                    {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v)
                        {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        }
        else
        {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /*
     * Callback received when a permissions request has been completed.
    */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if (requestCode == REQUEST_READ_CONTACTS)
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                populateAutoComplete();
    }

    /*
     * Shows the progress UI and hides the login form.
    */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
        {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd(Animator animation)
                {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd(Animator animation)
                {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        }
        else
        {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /*
     * Retrieve data rows for the device user's 'profile' contact.
     * Then selects only email addresses, showing the primary email address
     * first. There won't be a primary email if the user hasn't specified one.
    */
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle)
    {
        return new CursorLoader(this,
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor)
    {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }
        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader){}

    /*
     * Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
    */
    private void addEmailsToAutoComplete(List<String> emailAddressCollection)
    {
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);
        mEmailView.setAdapter(adapter);
    }

    private interface ProfileQuery
    {
        String[] PROJECTION ={ContactsContract.CommonDataKinds.Email.ADDRESS, ContactsContract.CommonDataKinds.Email.IS_PRIMARY};
        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /*
     * Represents an asynchronous login/registration task used to authenticate the user.
    */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean>
    {
        private final String mEmail;
        private final String mPassword;
        private final ArrayList<User> mDatabase;

        UserLoginTask(String email, String password)
        {
            mEmail = email;
            mPassword = password;
            mDatabase = retrieveDatabase();
        }

        /*
         * Loops through the user databases, and checks the email and password against
         * each user profile. If both match, the function is returned true. Otherwise,
         * the profile is not found and returns false.
        */
        @Override
        protected Boolean doInBackground(Void... params)
        {
            try { Thread.sleep(2000); }
            catch (InterruptedException e) { return false; }

            for(User user : mDatabase)
            {
                if(user.getEmail().equals(mEmail))
                    if(user.getPassword().equals(mPassword))
                    {
                        UserSelf.setUser(user);
                        return true;
                    }
            }
            return false;
        }

        /*
         * Runs after login execution. If doInBackground() returns true, then
         * the login processes finishes and the next intent starts. Otherwise,
         * the user is prompted that their login information is incorrect.
        */
        @Override
        protected void onPostExecute(final Boolean success)
        {
            mAuthTask = null;
            showProgress(false);

            if (success)
            {
                finish();
                Intent intent;
                if(UserSelf.getUser().getRole().equals(getString(R.string.employee_string)))
                    intent = new Intent(getApplicationContext(), JobListActivity.class);
                else if(UserSelf.getUser().getRole().equals(getString(R.string.employer_string)))
                    intent = new Intent(getApplicationContext(), UserListActivity.class);
                else
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
            else
            {
                mEmailView.setError(getString(R.string.invalid_login));
                mPasswordView.setError(getString(R.string.invalid_login));
                mEmailView.requestFocus();
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled()
        {
            mAuthTask = null;
            showProgress(false);
        }
    }
}