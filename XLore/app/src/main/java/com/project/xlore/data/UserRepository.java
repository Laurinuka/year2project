package com.project.xlore.data;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class UserRepository
{
    private static UserDatabase userDatabase;

    /* Creates a new instance of the database. */
    public UserRepository(Context context)
    {
        userDatabase = UserDatabase.getInstance(context);
    }

    /* Inserts a new user into the database. Uses Async to prevent the UI freezing. */
    public static void insertUser(final User user)
    {
        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... voids)
            {
                userDatabase.getUserDao().insertUser(user);
                return null;
            }
        }.execute();
    }

    /* Updates a user currently in the database. Uses Async to prevent the UI freezing. */
    public static void updateUser(final User user)
    {
        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... voids)
            {
                userDatabase.getUserDao().updateUser(user);
                return null;
            }
        }.execute();
    }

    /* Deletes a user currently in the database. Uses Async to prevent the UI freezing. */
    public static void deleteUser(final User user)
    {
        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... voids)
            {
                userDatabase.getUserDao().deleteUser(user);
                return null;
            }
        }.execute();
    }

    /* Retrieves all users from the database. */
    public LiveData<List<User>> getAllUsers()
    {
        return userDatabase.getUserDao().getAllUsers();
    }

    /* Retrieves a single user where their id matches userId. */
    public LiveData<User> getUser(int userId)
    {
        return userDatabase.getUserDao().getUser(userId);
    }

    /* Retrieves all users with 'Employee' role. */
    public LiveData<List<User>> getEmployeeUsers()
    {
        return userDatabase.getUserDao().getEmployeeUsers();
    }

    /* Retrieves all users with 'Employer' role. */
    public LiveData<List<User>> getEmployerUsers()
    {
        return userDatabase.getUserDao().getEmployerUsers();
    }

    /* Retrieves all users where their bio contains userWord. */
    public LiveData<List<User>> getUsersByBio(String userWord)
    {
        return userDatabase.getUserDao().getUsersByBio(userWord);
    }

    /* Retrieves all users where their skills contain userSkill. */
    public LiveData<List<User>> getUsersBySkills(String userSkill)
    {
        return userDatabase.getUserDao().getUsersBySkill(userSkill);
    }
}