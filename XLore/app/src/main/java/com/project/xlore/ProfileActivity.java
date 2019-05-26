package com.project.xlore;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.xlore.data.Converters;
import com.project.xlore.data.User;
import com.project.xlore.data.UserRepository;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity
{
    private User userProfile;
    private String name, email, phoneNumber, bio, skills;
    private EditText nameText, emailText, phoneNumberText, bioText, skillsText;
    private Converters converter = new Converters();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userProfile = UserSelf.getUser();
        setFieldContent();
    }

    public void setFieldContent()
    {
        setText(R.id.profile_text_name, userProfile.getName());
        setText(R.id.profile_text_email, userProfile.getEmail());
        setText(R.id.profile_text_phonenumber, userProfile.getPhoneNumber());
        setText(R.id.profile_multilinerext_bio, userProfile.getBio());
        setText(R.id.profile_multilinetext_skills, converter.convertFromStringArray(userProfile.getSkills()));
    }

    public void setText(int id, String content)
    {
        TextView text = (TextView) findViewById(id);
        text.setText(content);
    }

    public void getUserInput(View view)
    {
        nameText = idToEditText(R.id.profile_text_name);
        emailText  = idToEditText(R.id.profile_text_email);
        phoneNumberText  = idToEditText(R.id.profile_text_phonenumber);
        bioText = idToEditText(R.id.profile_multilinerext_bio);
        skillsText = idToEditText(R.id.profile_multilinetext_skills);

        name = editTextToString(nameText);
        email = editTextToString(emailText);
        phoneNumber = editTextToString(phoneNumberText);
        bio = editTextToString(bioText);
        skills = editTextToString(skillsText);

        updateUser();
    }

    public void updateUser()
    {
        userProfile.setName(name);
        userProfile.setEmail(email);
        userProfile.setPhoneNumber(phoneNumber);
        userProfile.setBio(bio);
        userProfile.setSkills(converter.convertToStringArray(skills));

        UserRepository.updateUser(userProfile);

        Toast loginToast = Toast.makeText(this, getString(R.string.profile_confirm), Toast.LENGTH_LONG);
        loginToast.show();

        finish();
    }

    /* Converters for ease when changing data types. */
    private String editTextToString(EditText editText)
    {
        return editText.getText().toString();
    }
    private EditText idToEditText(int id)
    {
        return findViewById(id);
    }
}
