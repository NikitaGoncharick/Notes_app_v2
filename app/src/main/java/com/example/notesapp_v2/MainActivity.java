package com.example.notesapp_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //давай разработаем приложение Заметки в андроид студио на языке java
    //простой интерфейс. Обязательное использование ROOM  и MVVP главная страница должна представлять собой изначально
    // пусутю страницу с добаленным на нее reyclerView и в нижней части экрана floating button, нажимая на который будет
    // открывать новая активити, на котрой можно заполнить данные для новой заметки
    // ( название, текст заметки + добавление системной даты и кнопка сохоанить) после чего активити должна
    // закрыться а на основной странице адаптер создаст нам новую заметку и созранить данные
}