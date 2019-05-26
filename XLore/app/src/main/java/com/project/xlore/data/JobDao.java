package com.project.xlore.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface JobDao
{
    /* Inserts a new job into the database. */
    @Insert
    void insertJob(Job job);

    /* Updates a job currently in the database. */
    @Update
    void updateJob(Job job);

    /* Deletes a job currently in the database. */
    @Delete
    void deleteJob(Job job);

    /* Retrieves all jobs from the database. */
    @Query("SELECT *, `rowid` FROM job")
    LiveData<List<Job>> getAllJobs();

    /* Retrieves a single job where their id matches jobId. */
    @Query("SELECT *, `rowid` FROM job WHERE `rowid`=:jobId")
    LiveData<Job> getJob(int jobId);

    /* Retrieves all jobs where name contains jobName*/
    @Query("SELECT *, `rowid` FROM job WHERE name LIKE :jobName")
    LiveData<List<Job>> getJobsByName(String jobName);

    /* Retrieves all jobs where the salary is greater than jobSalary. */
    @Query("SELECT *, `rowid` FROM job WHERE salary >= :jobSalary")
    LiveData<List<Job>> getJobsBySalary(double jobSalary);

    /* Retrieves all jobs where location is within jobMiles range. */
    //TODO: Add query for checking job location.

    /* Retrieves all jobs where the employment type is full-time. */
    @Query("SELECT *, `rowid` FROM job WHERE type='Full-time'")
    LiveData<List<Job>> getFullTimeJobs();

    /* Retrieves all jobs where the employment type is part-time. */
    @Query("SELECT *, `rowid` FROM job WHERE type='Part-time'")
    LiveData<List<Job>> getPartTimeJobs();

    /* Retrieves all jobs where the employment type is fixed-term. */
    @Query("SELECT *, `rowid` FROM job WHERE type='Fixed-term'")
    LiveData<List<Job>> getFixedTermJobs();

    /* Retrieves all jobs where the employment type is temporary. */
    @Query("SELECT *, `rowid` FROM job WHERE type='Temporary'")
    LiveData<List<Job>> getTemporaryJobs();

    /* Retrieves all jobs where the employment type is freelance. */
    @Query("SELECT *, `rowid` FROM job WHERE type='Freelance'")
    LiveData<List<Job>> getFreelanceJobs();

    /* Retrieves all jobs where the employment type is zero hour. */
    @Query("SELECT *, `rowid` FROM job WHERE type='Zero hour'")
    LiveData<List<Job>> getZeroHourJobs();

    /* Retrieves all jobs where the closing date is after jobDate. */
    //TODO: Add query for checking closing date.

    /* Retrieves all jobs where description contains jobWord */
    @Query("SELECT *, `rowid` FROM job WHERE description LIKE :jobWord")
    LiveData<List<Job>> getJobsByDescription(String jobWord);

    /* Retrieves all jobs where the required skills contain jobSkill */
    @Query("SELECT *, `rowid` FROM job WHERE skills LIKE :jobSkill")
    LiveData<List<Job>> getJobsBySkill(String jobSkill);
}
