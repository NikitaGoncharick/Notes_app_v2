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
    public static final String EXTRA_TITLE = "com.example.notesapp_v2.EXTRA_TITLE"; // ключ передачи контента топ версии
    public static final String EXTRA_CONTENT = "com.example.notesapp_v2.EXTRA_CONTENT";
    EditText editTextTitle, editTextContent;
    ImageView saveButton;

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
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_CONTENT, content);

        setResult(RESULT_OK, data); //?
        finish(); // Закрытие активности

    }

}