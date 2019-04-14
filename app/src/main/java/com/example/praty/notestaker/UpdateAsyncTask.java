package com.example.praty.notestaker;

import android.os.AsyncTask;

public class UpdateAsyncTask extends AsyncTask<Notes,Void,Void>{

    private NoteDao mNoteDao;

    public UpdateAsyncTask(NoteDao mNoteDao) {
        this.mNoteDao = mNoteDao;
    }

    @Override
    protected Void doInBackground(Notes... notes) {
        mNoteDao.update(notes);
        return null;
    }
}
