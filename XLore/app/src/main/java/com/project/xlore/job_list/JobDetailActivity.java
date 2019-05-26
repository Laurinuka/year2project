package com.project.xlore.job_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.project.xlore.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class JobDetailActivity extends AppCompatActivity
{
    private boolean appliedFor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null)
        {
            Bundle arguments = new Bundle();
            arguments.putString(JobDetailFragment.ARG_ITEM_ID, getIntent().getStringExtra(JobDetailFragment.ARG_ITEM_ID));
            JobDetailFragment fragment = new JobDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.job_detail_container, fragment).commit();
        }

        onClickFab();
    }

    private void onClickFab()
    {
        appliedFor = false;
        final FloatingActionButton fab = findViewById(R.id.jobdetail_fab_apply);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!appliedFor)
                {
                    Snackbar.make(view, "Application Sent", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    fab.setImageResource(R.drawable.ic_remove_circle_black_24dp);
                    appliedFor = true;
                }
                else
                {
                    Snackbar.make(view, "Application Revoked", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    fab.setImageResource(R.drawable.ic_add_circle_black_24dp);
                    appliedFor = false;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            navigateUpTo(new Intent(this, JobListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
