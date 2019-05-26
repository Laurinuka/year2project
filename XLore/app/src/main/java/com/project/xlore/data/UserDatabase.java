package com.project.xlore.data;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class UserDatabase extends RoomDatabase
{
    private static final String DB_NAME = "user_database.db";
    private static volatile UserDatabase INSTANCE;

    /* Retrieves the UserDao so that it's queries can be accessed. */
    public abstract UserDao getUserDao();

    /* Checks if an instance of the database has already been created. If not, it is created. */
    public static synchronized UserDatabase getInstance(Context context)
    {
        if (INSTANCE == null)
            INSTANCE = create(context);
        return INSTANCE;
    }

    /* Builds the database and adds migration to avoid data loss. */
    private static UserDatabase create(final Context context)
    {
        copyDatabase(context);
        return Room.databaseBuilder(context, UserDatabase.class, DB_NAME).addMigrations(MIGRATION_1_2).build();
    }

    /* Migrates the database contents during version changes. */
    static final Migration MIGRATION_1_2 = new Migration(1, 2)
    {
        @Override
        public void migrate(SupportSQLiteDatabase database) { }
    };

    /* Pre-populates the database the first time the application starts on a new device.
     * This is done by taking the user_database.db file found within the assets folder
     * and copying it to the local database on the users device.
     * If the database has already been created no action is taken. Otherwise, the directory
     * path for both the assets file and local database folder are retrieved, with the assets
     * files contents being copied into the local database file. */
    private static void copyDatabase(Context context)
    {
        final File dbPath = context.getDatabasePath(DB_NAME);
        if (dbPath.exists()) return;
        dbPath.getParentFile().mkdirs();

        try
        {
            final InputStream input = context.getAssets().open(DB_NAME);
            final OutputStream output = new FileOutputStream(dbPath);
            byte[] buffer = new byte[8192];
            int length;

            while ((length = input.read(buffer, 0, 8192)) > 0)
                output.write(buffer, 0, length);

            output.flush();
            output.close();
            input.close();
        }
        catch (IOException exception)
        {
            Log.d(UserDatabase.class.getSimpleName(), "File Failed To Open", exception);
            exception.printStackTrace();
        }
    }
}
