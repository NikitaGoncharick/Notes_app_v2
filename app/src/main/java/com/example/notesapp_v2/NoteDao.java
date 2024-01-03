package com.example.notesapp_v2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao { //Здесь мы определяем методы для взаимодействия с базой данных
    //NoteDao должен быть ограничен только определением интерфейсов для доступа к данным, без содержания бизнес-логики или дополнительной обработки данных. !!
    @Insert // Room автоматически генерирует необходимый код для выполнения соответствующих операций базы данных
    void insert(Note note);
    @Update
    void update(Note note);
    @Delete
    void delete(Note note);

    //------------------------------------------------------------------------------------------------------------//

    //Когда вы вызываете метод getAllNotes(), Room выполняет SQL запрос, указанный в @Query
    //Результат этого запроса - список заметок, упорядоченный по дате - затем помещается в объект LiveData

    //таким образом вызов метода getAllNotes это команда Room  через транслятор в лице Query о
    // записи в коробку LiveData данных, в соответствии и порядком с запросом "select * from notes order by date desc"
    @Query("select * from notes order by date desc")
    LiveData<List<Note>> getAllNotes(); // getAllNotes метод который будет отдавать все, что внутри коробки LiveData

    //------------------------------------------------------------------------------------------------------------//
}
