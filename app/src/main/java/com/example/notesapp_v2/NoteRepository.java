package com.example.notesapp_v2;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

//----------------------------------------------ОБРАБОТКА ЛОГИКИ NoteDAO-------------------------------------------------//
//--------------------NoteRepository взаимодействует с базой данных через NoteDao и обновляет данные---------------------//

// NoteRepository - это как связующее звено между базой данных и остальной частью приложения.
// Представьте, что NoteRepository это посредник, который берет на себя всю работу по общению с базой данных.
// + NoteRepository позволяет абстрагироваться от конкретного источника данных!!
// + NoteRepository может управлять асинхронными операциями

public class  NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;  // Представьте LiveData как магическую коробку, в которой хранятся все ваши заметки.
                                             // Каждый раз, когда вы добавляете, удаляете или изменяете заметку в вашем приложении,
                                            // магическая коробка автоматически обновляется с новым списком заметок

                                            // Ваш пользовательский интерфейс (например, экран со списком заметок) "подписывается"
                                            // на эту магическую коробку. Это означает, что он автоматически получает уведомления
                                             // каждый раз, когда что-то в коробке меняется.

    public NoteRepository(Application application){
        NoteDatabase database = NoteDatabase.getDatabase(application); // объявили базу данных
        noteDao = database.noteDao(); // подключили запросы к базе данных
        allNotes = noteDao.getAllNotes(); // наполнили ..волшебную коробку.. данными из базы данных
    }

    public void insert(Note note){
        NoteDatabase.databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.insert(note);
            }
        });
    }
    public void update(Note note){
        NoteDatabase.databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.update(note);
            }
        });
    }
    public void delete(Note note){
        NoteDatabase.databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.delete(note);
            }
        });
    }

    //метод возвращает объект LiveData, который содержит список (List) заметок (Note). LiveData - это особый тип данных в Android,
    // который автоматически обновляется, когда изменяются данные внутри него.
    public LiveData<List<Note>>getAllNotes(){
        return allNotes;
    }
}
