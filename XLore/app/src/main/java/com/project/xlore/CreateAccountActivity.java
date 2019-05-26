package com.project.xlore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.project.xlore.data.User;
import com.project.xlore.data.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

public class CreateAccountActivity extends AppCompatActivity
{
    private String role, name, email, phoneNumber, password, confirmPassword;
    private EditText nameText, emailText, phoneNumberText, passwordText, confirmPasswordText;
    private RadioButton employee, employer;
    private ArrayList<User> existingProfiles;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    //"(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    //"(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 6 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        getSupportActionBar().hide();
        existingProfiles = retrieveDatabase();
    }

    /*
     * Runs when user clicks the submit button when creating an account.
     * Collects the users input, and then checks to see if it is valid.
     * If input is valid the user is added to the database and next activity is started.
    */
    public void createAccount(View view)
    {
        getUserInput();

        if(!(isAccountUnique() &&
            isNameValid() &&
            isEmailValid() &&
            isPhoneNumberValid() &&
            isPasswordValid() &&
            isConfirmPasswordValid()))
            return;

        User user = new User(role, name, email, phoneNumber, password);
        UserRepository repo = new UserRepository(getApplicationContext());
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        Toast loginToast = Toast.makeText(this, getString(R.string.account_success), Toast.LENGTH_LONG);
        loginToast.show();

        repo.insertUser(user);
        startActivity(intent);
    }

    /*
     * Collects the ids of each entry field and converts it to an EditText object, and
     * then converts that EditText object into a raw string.
    */
    private void getUserInput()
    {
        nameText = idToEditText(R.id.createaccount_text_name);
        emailText  = idToEditText(R.id.createaccount_text_email);
        phoneNumberText  = idToEditText(R.id.createaccount_text_phone);
        passwordText  = idToEditText(R.id.createaccount_text_password);
        confirmPasswordText  = idToEditText(R.id.createaccount_text_confirmpassword);

        name = editTextToString(nameText);
        email = editTextToString(emailText);
        phoneNumber = editTextToString(phoneNumberText);
        password = editTextToString(passwordText);
        confirmPassword = editTextToString(confirmPasswordText);

        role = getRadioInput();
    }

    /*
     * Collects the ids of the radio button fields and converts it to a RadioButton object.
     * Each button is then checked to see if it has been selected, and the corresponding
     * String object is returned.
    */
    private String getRadioInput()
    {
        employee = idToRadioButton(R.id.createaccount_radio_employee);
        employer = idToRadioButton(R.id.createaccount_radio_employer);

        if(employee.isChecked())
            return getString(R.string.employee_string);
        else if(employer.isChecked())
            return getString(R.string.employer_string);
        else
            return getString(R.string.none_string);
    }

    /*
     * Loops through the corresponding employee/employer database, and checks to
     * see if any profiles currently stored are using the same email as the one
     * entered into the field. If so return false, otherwise return true.
     * This method only checks the database of the corresponding role, so users
     * can create two accounts with the same email (one as employee, one as employer)
     * if they wish.
    */
    private boolean isAccountUnique()
    {
        for(User user : existingProfiles)
        {
            if(user.getEmail().equals(email))
            {
                emailText.setError(getString(R.string.used_email));
                return false;
            }
        }
        return true;
    }

    /*
     * Checks if the contents of the name field are valid.
     * If the field is empty or only contains whitespace, return error and false.
    */
    private boolean isNameValid()
    {
        if(name.isEmpty() || name.trim().isEmpty())
        {
            nameText.setError(getString(R.string.field_required));
            return false;
        }
        return true;
    }

    /*
     * Checks if the contents of the email field are valid.
     * If the field is empty or only contains whitespace, return error and false.
     * If email doesn't contain @ symbol, return error and false.
    */
    private boolean isEmailValid()
    {
        if(email.isEmpty() || email.trim().isEmpty())
        {
            emailText.setError(getString(R.string.field_required));
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailText.setError(getString(R.string.invalid_email));
            return false;
        }
        return true;
    }

    /*
     * Checks if the contents of the phone number field are valid.
     * If the field is empty or only contains whitespace, return error and false.
    */
    private boolean isPhoneNumberValid()
    {
        if(phoneNumber.isEmpty() || phoneNumber.trim().isEmpty())
        {
            phoneNumberText.setError(getString(R.string.field_required));
            return false;
        }
        return true;
    }

    /*
     * Checks if the contents of the password field are valid.
     * If the field is empty or only contains whitespace, return error and false.
     * If password length is less than 6, return error and false.
    */
    private boolean isPasswordValid()
    {

//        if(password.isEmpty() || password.trim().isEmpty())
//        {
//            passwordText.setError(getString(R.string.field_required));
//            return false;
//        }
//        else if(password.length() < 6)
//        {
//            passwordText.setError(getString(R.string.short_password));
//            return false;
//        }
        if (!PASSWORD_PATTERN.matcher(password).matches())
        {
            passwordText.setError("Password must meet the following requirements \n - At least one uppercase letter \n - At least one lowercase letter \n - At least one number \n - At least six characters long");
            return false;
        }
        else
        {
            passwordText.setError(null);
            return true;
        }

    }

    /*
     * Checks if the contents of the password confirmation field are valid.
     * If the field is empty or only contains whitespace, return error and false.
     * If password length is less than 6, return error and false.
     * If the confirmation password doesn't match the password, return error and false.
    */
    private boolean isConfirmPasswordValid()
    {
        if(confirmPassword.isEmpty() || confirmPassword.trim().isEmpty())
        {
            confirmPasswordText.setError(getString(R.string.field_required));
            return false;
        }
//        else if(confirmPassword.length() < 6)
//        {
//            confirmPasswordText.setError(getString(R.string.short_password));
//            return false;
//        }
        else if(!confirmPassword.equals(password))
        {
            confirmPasswordText.setError(getString(R.string.mismatch_passwords));
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
     * Converters for ease when changing data types.
    */
    private String editTextToString(EditText editText)
    {
        return editText.getText().toString();
    }
    private EditText idToEditText(int id)
    {
        return findViewById(id);
    }
    private RadioButton idToRadioButton(int id)
    {
        return findViewById(id);
    }
}