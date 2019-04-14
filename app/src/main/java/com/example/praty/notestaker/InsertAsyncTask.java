package com.example.praty.notestaker;

import android.os.AsyncTask;

public class InsertAsyncTask extends AsyncTask<Notes, Void, Void> {

    private NoteDao mNoteDao;

    public InsertAsyncTask(NoteDao mNoteDao) {
        this.mNoteDao = mNoteDao;
    }

    @Override
    protected Void doInBackground(Notes... notes) {
        mNoteDao.insertNotes(notes);
        return null;
    }
}
