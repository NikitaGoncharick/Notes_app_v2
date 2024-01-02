package com.example.notesapp_v2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao { //Здесь мы определяем методы для вставки, обновления, удаления заметок и запроса всех заметок
    @Insert
    void insert(Note note);
    @Update
    void update(Note note);
    @Delete
    void delete(Note note);
    @Query("select * from notes order by date desc")
    LiveData<List<Note>> getAllNotes();
}
