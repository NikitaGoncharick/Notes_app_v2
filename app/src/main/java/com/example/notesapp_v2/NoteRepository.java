package com.example.notesapp_v2;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

//В этом классе мы создаём методы для вставки, обновления и удаления заметок.
// Операции с базой данных выполняются асинхронно с использованием ExecutorService.

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application){
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public void insert(Note note){
        NoteDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.insert(note);
            }
        });
    }
    public void update(Note note){
        NoteDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.update(note);
            }
        });
    }
    public void delete(Note note){
        NoteDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.delete(note);
            }
        });
    }
    public LiveData<List<Note>>getAllNotes(){
        return allNotes;
    }
}
