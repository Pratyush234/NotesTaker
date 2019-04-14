package com.example.praty.notestaker;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    long[] insertNotes(Notes... notes);

    @Query("SELECT * FROM notes")
    LiveData<List<Notes>> getNotes();

    @Query("SELECT * FROM notes WHERE title LIKE :title")
    List<Notes> getNoteWithCustomQuery(String title);

    @Delete
    int delete(Notes... notes);

    @Update
    int update(Notes... notes);
}
