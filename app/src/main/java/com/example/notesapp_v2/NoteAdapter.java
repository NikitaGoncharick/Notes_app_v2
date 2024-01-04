package com.example.notesapp_v2;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
        }
        public void bind(Note note){
            noteTitleView.setText(note.getTitle());
            noteContentView.setText(note.getContent());
            noteDateView.setText(note.getDate());
        }
    }
}
