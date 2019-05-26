package com.project.xlore.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao
{
    /* Inserts a new user into the database. */
    @Insert
    void insertUser(User user);

    /* Updates a user currently in the database. */
    @Update
    void updateUser(User user);

    /* Deletes a user currently in the database. */
    @Delete
    void deleteUser(User user);

    /* Retrieves all users from the database. */
    @Query("SELECT *, `rowid` FROM user")
    LiveData<List<User>> getAllUsers();

    /* Retrieves a single user where their id matches userId. */
    @Query("SELECT *, `rowid` FROM user WHERE `rowid`=:userId")
    LiveData<User> getUser(int userId);

    /* Retrieves all users with 'Employee' role. */
    @Query("SELECT *, `rowid` FROM user WHERE role='Employee'")
    LiveData<List<User>> getEmployeeUsers();

    /* Retrieves all users with 'Employer' role. */
    @Query("SELECT *, `rowid` FROM user WHERE role='Employer'")
    LiveData<List<User>> getEmployerUsers();

    /* Retrieves all users where their bio contains userWord. */
    @Query("SELECT *, `rowid` FROM user WHERE bio LIKE :userWord")
    LiveData<List<User>> getUsersByBio(String userWord);

    /* Retrieves all users where their skills contain userSkill. */
    @Query("SELECT *, `rowid` FROM user WHERE skills LIKE :userSkill")
    LiveData<List<User>> getUsersBySkill(String userSkill);
}
