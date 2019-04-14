package com.example.praty.notestaker;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Notes.class},version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static final String DATABASE_NAME="notes_db";

    private static NoteDatabase instance;

    static NoteDatabase getInstance(final Context context){
        if(instance==null){
            instance= Room.databaseBuilder(
                    context.getApplicationContext(),
                    NoteDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract NoteDao getDao();
}
