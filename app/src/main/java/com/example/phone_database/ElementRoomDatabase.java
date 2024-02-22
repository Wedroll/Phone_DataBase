package com.example.phone_database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Element.class}, version = 3, exportSchema = false)
public abstract class ElementRoomDatabase extends RoomDatabase{

    public abstract ElementDao elementDao();

    private static volatile ElementRoomDatabase INSTANCE;

    static ElementRoomDatabase getDataBase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ElementRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ElementRoomDatabase.class, "PhoneDB")

                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NotNull SupportSQLiteDatabase db){
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                ElementDao dao = INSTANCE.elementDao();

                dao.insert(new Element(1,"Iphone", "12", "15", "www.google.com"));
            });
        }
    };
}
