package com.project.xlore.job_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.project.xlore.MainActivity;
import com.project.xlore.ProfileActivity;
import com.project.xlore.R;
import com.project.xlore.data.Job;
import com.project.xlore.data.JobRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

public class JobListActivity extends AppCompatActivity
{
    public final static List<Job> JOBS = new ArrayList<>();
    public final static Map<String, Job> JOB_MAP = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        View recyclerView = findViewById(R.id.job_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView)
    {
        retrieveJobDatabase();
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(JOBS));
    }

    private void retrieveJobDatabase()
    {
        JOBS.clear();
        JOB_MAP.clear();
        JobRepository repo = new JobRepository(this);

        repo.getAllJobs().observe(this, new Observer<List<Job>>()
        {
            @Override
            public void onChanged(@Nullable List<Job> jobs)
            {
                for(Job job : jobs)
                {
                    JOBS.add(job);
                    JOB_MAP.put(Integer.toString(job.getId()), job);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_profile)
        {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_applications)
        {
            return true;
        }
        else if (id == R.id.action_addjob)
        {
            Toast alertToast = Toast.makeText(this, getString(R.string.toolbar_addjobalert), Toast.LENGTH_LONG);
            alertToast.show();
            return true;
        }
        else if (id == R.id.action_logout)
        {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>
    {
        private final List<Job> mValues;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Job item = (Job) view.getTag();
                Context context = view.getContext();
                Intent intent = new Intent(context, JobDetailActivity.class);
                intent.putExtra(JobDetailFragment.ARG_ITEM_ID, Integer.toString(item.getId()));
                context.startActivity(intent);
            }
        };

        SimpleItemRecyclerViewAdapter(List<Job> items)
        {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position)
        {
            holder.mContentView.setText(mValues.get(position).getJobName());
            holder.mTypeView.setText(mValues.get(position).getEmploymentType());

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount()
        {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {
            final TextView mContentView;
            final TextView mTypeView;

            ViewHolder(View view)
            {
                super(view);
                mContentView = view.findViewById(R.id.joblist_text_content);
                mTypeView = view.findViewById(R.id.joblist_text_type);
            }
        }
    }
}
