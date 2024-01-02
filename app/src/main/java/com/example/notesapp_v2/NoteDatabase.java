package com.example.notesapp_v2;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase databse;
    public abstract NoteDao noteDao();
    private static final int NUMBER_OF_THREADS = 4;

    //Этот код создаёт пул из четырёх потоков для выполнения задач, связанных с базой данных.
    // Это позволяет нам выполнять операции вставки, обновления и удаления в фоновом режиме
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized NoteDatabase getDatabase(Context context){
        if (databse == null){
            databse = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "note_databse")
                    .fallbackToDestructiveMigration().build();
        }
        return databse;
    }
}
