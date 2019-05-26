package com.project.xlore;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.project.xlore.data.Job;
import com.project.xlore.data.User;
import com.project.xlore.job_list.JobListActivity;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class SearchJobsActivity extends AppCompatActivity
{
    AutoCompleteTextView language;
    String[] programmingLanguages = new String[]{"Java", "Python", "C++", "MySQL", "C", "C#", "JavaScript", "PHP", "Ruby", };
    String[] jobOptions = new String[]{"Web Development", "Software Development", "Network Engineer", "Project Management"};
    private SeekBar seekBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_jobs);
        language = findViewById(R.id.search_text_language);
        language.setAdapter(new ArrayAdapter<>(SearchJobsActivity.this, android.R.layout.simple_list_item_1, programmingLanguages));
        seekBar = findViewById(R.id.search_salary_seekbar);
        textView = findViewById(R.id.search_max_salary);
        salaryBar();
    }

    /* Starts the job list activity */
    public void startJobListActivity(View view)
    {

        Intent intent;
        intent = new Intent(getApplicationContext(), JobListActivity.class);
        startActivity(intent);
    }

    public void salaryBar()
    {
        textView.setText("Max Salary: £" + seekBar.getProgress());

        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progressValue;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progressValue = progress;
                        textView.setText("Max Salary: £" + (progress*1000));
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        textView.setText("Max Salary: £" + (progressValue*1000));
                    }
                }
        );
    }
}

// TODO: Currently if trying to run JobListActivity from here, the list appears empty. Try fix with by creating a new login page, linking here to there, and figuring out which part fixes it.