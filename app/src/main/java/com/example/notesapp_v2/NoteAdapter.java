package com.example.notesapp_v2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<Note> notes; // Список заметок
    private OnNoteClickListener listener; //то переменная, которая будет хранить ссылку на объект, реализующий интерфейс
                                        //при создании ViewHolder, для каждого элемента списка, вы добавляете обработчик клика, который вызывает onNoteClick(note) этого слушателя.
    public NoteAdapter(List<Note> notes){ //Это используется для первоначального заполнения RecyclerView данными.
        this.notes = notes;
    }
    public void setNotes(List<Note> notes){
        this.notes = notes;
         notifyDataSetChanged();//Этот вызов уведомляет RecyclerView, что данные изменились, и RecyclerView должен перерисовать себя, чтобы отобразить новые данные.
    }

    // Создание нового ViewHolder для каждого элемента списка
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false); //дизайн элементов списка прописан тут
        return new NoteViewHolder(itemView);
    }

    // Привязка данных заметки к ViewHolder
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note currentNote = notes.get(position); // здесь currentNote становится заметкой, которая должна быть отображена в текущем ViewHolder.
        // Эта строка кода не просто получает позицию элемента в списке. Она извлекает сам объект Note из списка notes, после чего он будет наполнен элементами из метода bind

        holder.bind(currentNote); //После того как получена нужная заметка, вызывается метод bind на объекте NoteViewHolder.
        //Внутри ViewHolder, метод bind принимает объект Note и использует его данные (например, заголовок и содержание заметки) для заполнения соответствующих виджетов
        // в макете этого ViewHolder.

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder{

        private TextView noteTitleView;
        private TextView noteContentView;
        private TextView noteDateView;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            noteTitleView = itemView.findViewById(R.id.note_title);
            noteContentView = itemView.findViewById(R.id.note_content);
            noteDateView = itemView.findViewById(R.id.note_date);


            //-------------------------------- ПЕРВОЕ ЧТО  ПРОСИХОДИТ ПРИ КЛИКЕ НА СУЩЕСТВУЮЩУЮ ЗАМЕТКУ!!----------------------------------------------
            itemView.setOnClickListener(new View.OnClickListener() { // Встроенная функция установки обработчика клика на каждый элемент списка
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition(); // 1 - получили ПОЗИЦИЮ!
                    if (listener != null && position != RecyclerView.NO_POSITION){ // проверка, установлен ли слушатель(ПО ДЕФЛТКУ У НАС-ДА) и является ли позиция действительной
                        listener.onNoteClick(notes.get(position));// Этот вызов активирует метод onNoteClick, который был определен в MainActivity И ЧЕРЕЗ ИНТЕРФЕЙС
                                                                //передан в адаптер, что бы его можно было тут непрописывая вызвать
                    }
                    //При клике определяется позиция элемента в списке и, если слушатель кликов (listener) установлен, вызывается onNoteClick с заметкой на этой позиции.
                }
            });
            //-----------------------------------------------------------------------------------------------------------------------------------------
        }
        //Когда RecyclerView хочет отобразить заметку, он вызывает метод bind с объектом Note, который содержит данные заметки.
        // Метод для привязки данных заметки к элементам интерфейса
        public void bind(Note note){
            noteTitleView.setText(note.getTitle());
            noteContentView.setText(note.getContent());
            noteDateView.setText(note.getDate());
        }
    }
    //------------------------------------------------------Интерфейс для обработки кликов-------------------------------------------------------------
    public interface OnNoteClickListener{
        void onNoteClick(Note note);
        //Прописываем интерфейс, что бы MainActivity реализовала нам его логику, когда мы отдадим ей данные
    }

    // Установка Обработчика Событий
    public void setOnNoteClickListener(OnNoteClickListener listener){
        this.listener = listener;
        // ЭТО НЕ САМОСТОЯТЕЛЬНЫЙ БЛОК КОДА!!!!
        // это по сути ссылка на объект, который "знает", как выполнить метод onNoteClick.
        // ссылка образуется, когда при клике,setOnNoteClickListener, в классе main, обработает нажатие и передаст информацию о том что это он отвечат за реализацию

        // и скажет адаптеру: "Вот реализация, которую я хочу, чтобы ты использовал для обработки кликов на элементы списка (listener)
        // а listener, в свою очередь, это переменная которая хранит ссылку на объект, реализующий интерфейс onNoteClick в main.
    }


}
