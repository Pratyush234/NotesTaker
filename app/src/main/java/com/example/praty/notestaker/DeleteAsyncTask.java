package com.example.praty.notestaker;

import android.os.AsyncTask;

public class DeleteAsyncTask extends AsyncTask<Notes,Void,Void>{

    private NoteDao mNoteDao;

    public DeleteAsyncTask(NoteDao mNoteDao) {
        this.mNoteDao = mNoteDao;
    }

    @Override
    protected Void doInBackground(Notes... notes) {
        mNoteDao.delete(notes);
        return null;
    }
}
