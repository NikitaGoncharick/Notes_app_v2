package com.example.notesapp_v2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddEditNoteActivity extends AppCompatActivity {
    EditText editTextTitle, editTextContent;
    ImageView saveButton;
    private int noteID = -1; // Инициализируем ID как -1, что означает "нет ID"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextContent = findViewById(R.id.edit_text_content);
        saveButton = findViewById(R.id.save_imageView);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra(Note.EXTRA_ID)){
            //setTitle("EDIT");
            noteID = intent.getIntExtra(Note.EXTRA_ID, -1);
            editTextTitle.setText(intent.getStringExtra(Note.EXTRA_TITLE));
            editTextContent.setText(intent.getStringExtra(Note.EXTRA_CONTENT));
        }
    }

    public void saveNote( ){
        String title = editTextTitle.getText().toString();
        String content = editTextContent.getText().toString();

        if (title.trim().isEmpty() || content.trim().isEmpty()){
            Toast.makeText(this, "EMPTY FIELDS" , Toast.LENGTH_SHORT).show();
            return; // используется для выхода из метода, если пользователь не ввел заголовок или содержимое заметки.
                    // Это предотвращает продолжение выполнения кода, который сохраняет данные и завершает активность,
        }
        // Создание интента для передачи данных обратно в MainActivity
        Intent data = new Intent();
        data.putExtra(Note.EXTRA_TITLE, title);
        data.putExtra(Note.EXTRA_CONTENT, content);
        if (noteID != -1){
            data.putExtra(Note.EXTRA_ID, noteID);
        }

        setResult(RESULT_OK, data); //?
        finish(); // Закрытие активности

    }

}