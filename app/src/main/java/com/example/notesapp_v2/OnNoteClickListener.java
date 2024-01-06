package com.example.notesapp_v2;


// это интерфейс, который вы создаете для обработки кликов на элементы списка в RecyclerView. Это своего рода "договор" или "правило",
// которое говорит: "Если кто-то нажимает на элемент списка, ему нужно предоставить конкретное действие для onNoteClick".
    public interface OnNoteClickListener {
    void onNoteClick(Note note);
}
