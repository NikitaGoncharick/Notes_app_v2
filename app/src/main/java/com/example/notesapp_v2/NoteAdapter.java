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
    private OnNoteClickListener listener; //при создании ViewHolder, для каждого элемента списка, вы добавляете обработчик клика, который вызывает onNoteClick(note) этого слушателя.
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
        Note currentNote = notes.get(position);
        holder.bind(currentNote);
    }

    // Получение количества элементов в списке
    @Override
    public int getItemCount() {
        return notes.size();
    }

    // Установка слушателя кликов
    public void setOnNoteClickListener(OnNoteClickListener listener){
        this.listener = listener;
    }

    // Внутренний класс для ViewHolder
    public class NoteViewHolder extends RecyclerView.ViewHolder{

        private TextView noteTitleView;
        private TextView noteContentView;
        private TextView noteDateView;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            noteTitleView = itemView.findViewById(R.id.note_title);
            noteContentView = itemView.findViewById(R.id.note_content);
            noteDateView = itemView.findViewById(R.id.note_date);


            // Установка обработчика клика на каждый элемент списка
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onNoteClick(notes.get(position));
                    }
                }
            });
        }

        //Когда RecyclerView хочет отобразить заметку, он вызывает метод bind с объектом Note, который содержит данные заметки.
        // Метод для привязки данных заметки к элементам интерфейса
        public void bind(Note note){
            noteTitleView.setText(note.getTitle());
            noteContentView.setText(note.getContent());
            noteDateView.setText(note.getDate());
        }
    }
    public interface OnNoteClickListener{
        void onNoteClick(Note note);
    }
}
