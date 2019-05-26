package com.project.xlore.data;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class JobRepository
{
    private static JobDatabase jobDatabase;

    /* Creates a new instance of the database. */
    public JobRepository(Context context)
    {
        jobDatabase = JobDatabase.getInstance(context);
    }

    /* Inserts a new job into the database. Uses Async to prevent the UI freezing. */
    public static void insertJob(final Job job)
    {
        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... voids)
            {
                jobDatabase.getJobDao().insertJob(job);
                return null;
            }
        }.execute();
    }

    /* Updates a job currently in the database. Uses Async to prevent the UI freezing. */
    public static void updateJob(final Job job)
    {
        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... voids)
            {
                jobDatabase.getJobDao().updateJob(job);
                return null;
            }
        }.execute();
    }

    /* Deletes a job currently in the database. Uses Async to prevent the UI freezing. */
    public static void deleteJob(final Job job)
    {
        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... voids)
            {
                jobDatabase.getJobDao().deleteJob(job);
                return null;
            }
        }.execute();
    }

    /* Retrieves all jobs from the database. */
    public LiveData<List<Job>> getAllJobs()
    {
        return jobDatabase.getJobDao().getAllJobs();
    }

    /* Retrieves a single job where their id matches jobId. */
    public LiveData<Job> getJob(int jobId)
    {
        return jobDatabase.getJobDao().getJob(jobId);
    }

    /* Retrieves all jobs where name contains jobName */
    public LiveData<List<Job>> getJobsByName(String jobName)
    {
        return jobDatabase.getJobDao().getJobsByName(jobName);
    }

    /* Retrieves all jobs where their salary is greater than or equal to jobSalary */
    public LiveData<List<Job>> getJobsBySalary(double jobSalary)
    {
        return jobDatabase.getJobDao().getJobsBySalary(jobSalary);
    }

    /* Retrieves all jobs where the employment type is full-time. */
    public LiveData<List<Job>> getFullTimeJobs()
    {
        return jobDatabase.getJobDao().getFullTimeJobs();
    }

    /* Retrieves all jobs where the employment type is part-time. */
    public LiveData<List<Job>> getPartTimeJobs()
    {
        return jobDatabase.getJobDao().getPartTimeJobs();
    }

    /* Retrieves all jobs where the employment type is fixed-term. */
    public LiveData<List<Job>> getFixedTermJobs()
    {
        return jobDatabase.getJobDao().getFixedTermJobs();
    }

    /* Retrieves all jobs where the employment type is temporary. */
    public LiveData<List<Job>> getTemporaryJobs()
    {
        return jobDatabase.getJobDao().getTemporaryJobs();
    }

    /* Retrieves all jobs where the employment type is freelance. */
    public LiveData<List<Job>> getFreelanceJobs()
    {
        return jobDatabase.getJobDao().getFreelanceJobs();
    }

    /* Retrieves all jobs where the employment type is zero hour. */
    public LiveData<List<Job>> getZeroHourJobs()
    {
        return jobDatabase.getJobDao().getZeroHourJobs();
    }

    /* Retrieves all jobs where description contains jobWord */
    public LiveData<List<Job>> getJobsByDescription(String jobWord)
    {
        return jobDatabase.getJobDao().getJobsByDescription(jobWord);
    }

    /* Retrieves all jobs where the required skills contain jobSkill */
    public LiveData<List<Job>> getJobsBySkill(String jobSkill)
    {
        return jobDatabase.getJobDao().getJobsBySkill(jobSkill);
    }
}
