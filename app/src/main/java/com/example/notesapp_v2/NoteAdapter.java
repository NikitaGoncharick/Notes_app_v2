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
    private OnNoteClickListener listener;
    public NoteAdapter(List<Note> notes){
        this.notes = notes;
    }
    public void setNotes(List<Note> notes){
        this.notes = notes;
        notifyDataSetChanged(); // Уведомляем адаптер о том, что данные изменились
    }
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //вы создаете новый ViewHolder
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) { //вы привязываете данные заметки к ViewHolder.
        Note currentNote = notes.get(position);
        holder.bind(currentNote);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setOnNoteClickListener(OnNoteClickListener listener){ //позволяет установить реализацию OnNoteClickListener
        this.listener = listener;
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
        public void bind(Note note){
            noteTitleView.setText(note.getTitle());
            noteContentView.setText(note.getContent());
            noteDateView.setText(note.getDate());
        }
    }
}
