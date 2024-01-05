package com.example.notesapp_v2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

//----------------- NoteViewModel обеспечивает разделение бизнес-логики приложения от UI, повышая тестируемость, читаемость и поддерживаемость кода--------------//

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository repository; // Представьте Repository как менеджера, который управляет всеми данными в вашем приложении.
    // Он работает как посредник между источниками данных (например, базой данных, сетевыми запросами) и остальной частью приложения.
    //ViewModel запрашивает данные у Repository, а Repository заботится о том, чтобы получить эти данные,
    private LiveData<List<Note>> allNotes; // LiveData содержащая в себе данные из бд, данные автоматически обновятся в UI, когда произойдут изменения в бд
    public NoteViewModel(@NonNull Application application) { // Конструктор ViewModel
        super(application);

        //Инициализация Базы Данных: В вашем NoteRepository нам нужен Context для инициализации Room базы данных (NoteDatabase).
        // Передавая application, вы предоставляете NoteRepository необходимую информацию для создания или доступа к базе данных.
        // cуществующтй в течение всего жизненного цикла  приложения

        repository = new NoteRepository(application); // Инициализация репозитория ( центральный узел для управления данными )
        allNotes = repository.getAllNotes(); // Получение данных из репозитория
    }

    // ---------------- ВЫЗОВ АСИНХРОННЫХ МЕТОДОВ В NoteRepository -------------------------- //
    public void insert(Note note){
        repository.insert(note);
    }
    public void update(Note note){
        repository.update(note);
    }
    public void delete(Note note){
        repository.delete(note);
    }

    //LiveData<List<Note>>: Это возвращаемый тип метода.
    // Он указывает, что метод возвращает объект типа LiveData,
    // который в свою очередь содержит список (List) объектов типа Note.
    public LiveData<List<Note>> getAllNotes(){ // Метод для получения всех заметок
        return allNotes;
    }
}
