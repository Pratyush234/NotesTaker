package com.example.praty.notestaker;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;

public class NoteRepository {

    private NoteDatabase mNoteDatabase;


    public NoteRepository(Context context) {
        mNoteDatabase=NoteDatabase.getInstance(context);
    }

    public void insertTask(Notes note){
        new InsertAsyncTask(mNoteDatabase.getDao()).execute(note);

    }

    public void updateNote(Notes note){
        new UpdateAsyncTask(mNoteDatabase.getDao()).execute(note);

    }

    public LiveData<List<Notes>> retrieveNotesTask(){
        return mNoteDatabase.getDao().getNotes();
    }

    public void deleteNoteTask(Notes note){
        new DeleteAsyncTask(mNoteDatabase.getDao()).execute(note);
    }
}
