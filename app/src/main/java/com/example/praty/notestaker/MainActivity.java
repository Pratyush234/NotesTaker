package com.example.praty.notestaker;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecycler;
    private NotesAdapter mAdapter;
    private List<Notes> mNotes=new ArrayList<>();

    private NoteRepository mNoteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNoteRepository=new NoteRepository(this);
        Toolbar toolbar=findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Notes");

        FloatingActionButton button=(FloatingActionButton) findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, NotesActivity.class);
                startActivity(intent);
            }
        });

        mRecycler= findViewById(R.id.my_recycler);

       // setupNotesData();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        mRecycler.setLayoutManager(linearLayoutManager);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(mRecycler);

        mAdapter=new NotesAdapter(mNotes, new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //click listener
                Intent intent=new Intent(MainActivity.this, NotesActivity.class);
                intent.putExtra("noteobject",mNotes.get(position));
                startActivity(intent);
            }
        });
        mRecycler.setAdapter(mAdapter);

        NotesItemDecorator notesItemDecorator=new NotesItemDecorator(10);
        mRecycler.addItemDecoration(notesItemDecorator);

        retrieveNotes();
    }

    private void retrieveNotes() {
        mNoteRepository.retrieveNotesTask().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(@Nullable List<Notes> notes) {
                if(mNotes.size()>0){
                    mNotes.clear();
                }
                if(notes!=null){
                    mNotes.addAll(notes);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setupNotesData() {

        for(int i=0;i<100;i++){
            Notes note=new Notes("title #"+ i,"content#"+i,"jan 2019");
            mNotes.add(note);
        }


    }

    private void deleteNote(Notes note){
        mNotes.remove(note);
        mAdapter.notifyDataSetChanged();

        mNoteRepository.deleteNoteTask(note);
    }

    private ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            deleteNote(mNotes.get(viewHolder.getAdapterPosition()));

        }
    };
}
