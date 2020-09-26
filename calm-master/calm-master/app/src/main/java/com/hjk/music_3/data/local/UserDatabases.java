package com.hjk.music_3.data.local;

import android.content.Context;


import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.hjk.music_3.data.local.dao.UserDao;
import com.hjk.music_3.data.local.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The Room Database that manages a local database.
 *
 * @author Yassin Ajdi.
 */
@Database(
        entities = {User.class},
        version = 1,
        exportSchema = false)
public abstract class UserDatabases extends RoomDatabase {
    public static final String DATABASE_NAME = "User.db";
    private static UserDatabases INSTANCE;
    private static final int NUMBER_OF_THREADS=4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract UserDao userDao();

    public static UserDatabases getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (UserDatabases.class) {
                if(INSTANCE==null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserDatabases.class,"user_database")
                            .addCallback(sRoomDatabaseCallback)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
            });
        }
    };
}
