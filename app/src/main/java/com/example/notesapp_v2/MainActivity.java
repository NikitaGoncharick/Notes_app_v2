package com.example.notesapp_v2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

//ОБЩАЯ СХЕМА ПРИЛОЖЕНИЯ:
//0
//Получение данных от пользователя в классе AddEditNoteActivity
//1
//Проверка Результата: Сначала MainActivity проверяет, откуда пришел результат. Он убеждается, что результат пришел именно
// от AddEditNoteActivity (это гарантируется проверкой requestCode). Он также проверяет, что все прошло хорошо (resultCode равен RESULT_OK).
//2
// Если все условия верны, MainActivity извлекает данные из Intent, который был отправлен из AddEditNoteActivity.
// Это делается с помощью метода getStringExtra
//3
//Создание Новой Заметки: Далее, MainActivity использует эти данные для создания новой заметки.
// Он создает объект Note, заполняя его заголовком и содержанием.
//4
//Обновление ViewModel: После создания новой заметки MainActivity передает эту заметку в ViewModel.
// ViewModel отвечает за управление данными, включая их сохранение в базу данных.
//5
//Автоматическое Обновление UI: RecyclerView в MainActivity автоматически обновляется, потому что он подписан на LiveData в ViewModel

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_ADD_NOTE = 1; // Код запроса для добавления заметки
    public static final int REQUEST_CODE_EDIT_NOTE = 2;

    private NoteViewModel noteViewModel;
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fb_add);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        noteAdapter = new NoteAdapter(new ArrayList<Note>());
        recyclerView.setAdapter(noteAdapter);

        //-------------------------------------------------------MVVM + LiveData--------------------------------------------------------

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class); // Инициализация NoteViewModel (Передача Repository  в ViewModel)
        // noteViewModel.getAllNotes() возвращает LiveData<List<Note>>. LiveData – это обертка, которая предоставляет данные так, чтобы MainActivity могла наблюдать за изменениями
        // в этих данных. Когда данные в базе данных изменяются, LiveData автоматически уведомляет об этих изменениях,
        // и RecyclerView в вашем MainActivity обновляется соответственно.

        // НАБЛЮДЕНИЕ ЗА ИЗМЕНЕНИЯМИ В СПИСКЕ ЗАМЕТОК

        // после срабатывания noteViewModel.insert(newNote); в onActivityResult система получит новые данные
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) { //Здесь вы подписываетесь на изменения в LiveData<List<Note>>. Каждый раз, когда данные в базе данных изменяются
                                                        // noteAdapter обновляется новым списком заметок.
                // Код, который выполнится при обновлении данных в LiveData
                noteAdapter.setNotes(notes); // Обновление адаптера новыми данными. Метод прописан в NoteAdapter
            }
        });
        //--------------------------------------------------------------------------------------------------------------
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_NOTE);
            }
        });
        //                          ОБРАБОТКА НАЖАТИЯ НА ЭЛЕМЕНТЫ RECYCLERVIEW
        noteAdapter.setOnNoteClickListener(new NoteAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {
                Intent intent = new Intent(MainActivity.this,AddEditNoteActivity.class);
                intent.putExtra(Note.EXTRA_ID, note.getId());
                intent.putExtra(Note.EXTRA_TITLE, note.getTitle());
                intent.putExtra(Note.EXTRA_CONTENT, note.getContent());
                startActivityForResult(intent, REQUEST_CODE_EDIT_NOTE);
                // REQUEST_CODE_EDIT_NOTE - это ключ проверки, который будет отправлен в AddEditNoteActivity при срабатывании клика и сохранится в памяти
                // после чего, когда в активити сработает finish() система вернет обратно этот сохраенный код проверки, провертит его в onActivityResult
                // и в случае его возвращения выполнит действия.
            }
        });
    }

    //---------------------------------------ПОЛУЧЕНИЕ ДАННЫХ из AddEditNoteActivity--------------------------------------------------

    //AddEditNoteActivity возвращает результат, проверяется код запроса (REQUEST_CODE_ADD_NOTE) и код результата (RESULT_OK).
    // Если условие выполняется, данные извлекаются из Intent и используются для создания нового объекта Note,
    // который затем сохраняется в базу данных через ViewModel.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK && data == null) {
            Log.d("FFFF", "No data returned or bad result code");
            return;
        }
        String title = data.getStringExtra(Note.EXTRA_TITLE);
        String content = data.getStringExtra(Note.EXTRA_CONTENT);

        Log.d("FFFF", "DATA: " + title + content); // проверка через лог пришли ли данные

        if (requestCode == REQUEST_CODE_ADD_NOTE) {
            // Добавление новой заметки
            Note newNote = new Note(title, content);
            noteViewModel.insert(newNote); // Сохраняем заметку в базе данных

        } else if (requestCode == REQUEST_CODE_EDIT_NOTE) {
            // Обновление существующей заметки
            int id = data.getIntExtra(Note.EXTRA_ID, -1);
            if (id != -1) {
                Note updateNote = new Note(title, content);
                updateNote.setId(id);
                noteViewModel.update(updateNote);
            }
        } else {
            Log.d("FFFF", "EMPTY: ");
        }
    }
    //------------------------------------------------------------------------------------------------------------------------
}