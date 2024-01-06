package com.example.notesapp_v2;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {
    // --------------------------------- КОНСТАНТЫ ДЛЯ ПЕРЕДАЧИ ЗНАЧЕНИЙ ВНУТРИ INTENT ПО ВСЕМУ ПРИЛОЖЕНИЮ --------------------
    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_CONTENT = "EXTRA_CONTENT";
    //--------------------------------- --------------------------------- ------------------------------------------------------
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "content")
    private String content;
    @ColumnInfo(name = "date")
    private String date;


    public Note(String title, String content) {
        this.title = title;
        this.content = content;
        //this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
