package com.example.praty.notestaker;

import android.app.Activity;
import android.hardware.input.InputManager;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NotesActivity extends AppCompatActivity implements
        View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener,
        View.OnClickListener,
        TextWatcher

{
    private static final String TAG = "NotesActivity";
    private Notes mNote;
    private Notes mFinalNote;
    private LineEditText mLineEditText;
    private TextView mViewTitle;
    private EditText mEditTitle;
    RelativeLayout mBackArrowContainer,mCheckContainer;
    ImageButton mBackArrow, mCheck;

    private static final int EDIT_ENABLE_MODE=1;
    private static final int EDIT_DISABLE_MODE=0;


    private GestureDetector mGestureDetector;

    private boolean isNewNote;
    private int mMode;
    private NoteRepository mNoteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);


        mLineEditText = (LineEditText) findViewById(R.id.note_text);
        mViewTitle = (TextView) findViewById(R.id.note_text_title);
        mEditTitle = (EditText) findViewById(R.id.note_edit_title);
        mBackArrowContainer=(RelativeLayout) findViewById(R.id.back_arrow_container);
        mCheckContainer=(RelativeLayout) findViewById(R.id.check_container);
        mBackArrow=(ImageButton) findViewById(R.id.toolbar_back_arrow);
        mCheck=(ImageButton) findViewById(R.id.toolbar_check);
        mNoteRepository=new NoteRepository(this);

        if(checkIfNewNote()){
            //Edit mode
            setNewNoteProperties();
            enableEditMode();
        }

        else{
            //View mode
            disableContentInteraction();
            setNoteProperties();

        }

        handleListeners();

    }

    private void saveChanges(){
        if(isNewNote){
            saveNewNote();
        }
        else{
            updateNote();

        }
    }

    private void updateNote() {
        mNoteRepository.updateNote(mFinalNote);
    }

    private void saveNewNote(){
        mNoteRepository.insertTask(mFinalNote);

    }

    private void disableContentInteraction(){
        mLineEditText.setKeyListener(null);
        mLineEditText.setFocusable(false);
        mLineEditText.setFocusableInTouchMode(false);
        mLineEditText.setCursorVisible(false);
        mLineEditText.clearFocus();
    }

    private void enableContentInteraction(){
        mLineEditText.setKeyListener(new EditText(this).getKeyListener());
        mLineEditText.setFocusable(true);
        mLineEditText.setFocusableInTouchMode(true);
        mLineEditText.setCursorVisible(true);
        mLineEditText.requestFocus();
    }

    private void hideSoftKeyBoard(){
        InputMethodManager imm=(InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view=this.getCurrentFocus();
        if(view==null){
            view=new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    private void enableEditMode(){
        mBackArrowContainer.setVisibility(View.GONE);
        mCheckContainer.setVisibility(View.VISIBLE);

        mViewTitle.setVisibility(View.GONE);
        mEditTitle.setVisibility(View.VISIBLE);

        mMode=EDIT_ENABLE_MODE;
        enableContentInteraction();
    }

    private void disableEditMode(){
        mBackArrowContainer.setVisibility(View.VISIBLE);
        mCheckContainer.setVisibility(View.GONE);

        mViewTitle.setVisibility(View.VISIBLE);
        mEditTitle.setVisibility(View.GONE);
        mMode=EDIT_DISABLE_MODE;
        disableContentInteraction();

        String temp=mLineEditText.getText().toString();
        temp=temp.replace("\n","");
        temp=temp.replace(" ","");
        if(temp.length()>0){
            mFinalNote.setTitle(mEditTitle.getText().toString());
            mFinalNote.setContent(mLineEditText.getText().toString());
            String timestamp=Utility.getCurrentTimeStamp();
            mFinalNote.setTimestamp(timestamp);

            if(!mFinalNote.getContent().equals(mNote.getContent()) ||
                    !mFinalNote.getTitle().equals(mNote.getTitle())){
                saveChanges();
            }
        }



    }

    private void handleListeners(){
        mLineEditText.setOnTouchListener(this);
        mGestureDetector=new GestureDetector(this, this);
        mCheck.setOnClickListener(this);
        mViewTitle.setOnClickListener(this);
        mBackArrow.setOnClickListener(this);
        mEditTitle.addTextChangedListener(this);


    }

    private void setNewNoteProperties() {
        mEditTitle.setText("Note Title");
        mViewTitle.setText("Note Title");

        mNote=new Notes();
        mFinalNote=new Notes();

        mNote.setTitle("Note Title");
        mFinalNote.setTitle("Note Title");

    }

    private void setNoteProperties() {
        mViewTitle.setText(mNote.getTitle());
        mEditTitle.setText(mNote.getTitle());
        mLineEditText.setText(mNote.getContent());
    }

    private boolean checkIfNewNote() {
        if(getIntent().hasExtra("noteobject")){
            mNote=getIntent().getParcelableExtra("noteobject");
            mFinalNote=new Notes();
            mFinalNote.setTitle(mNote.getTitle());
            mFinalNote.setTimestamp(mNote.getTimestamp());
            mFinalNote.setContent(mNote.getContent());
            mFinalNote.setId(mNote.getId());


            isNewNote=false;
            mMode=EDIT_DISABLE_MODE;
            return false;

        }

        isNewNote=true;
        mMode=EDIT_ENABLE_MODE;
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {

        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.d(TAG, "onDoubleTapEvent: called");
        enableEditMode();
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){


            case R.id.toolbar_check:{
                hideSoftKeyBoard();
                disableEditMode();
                break;
            }

            case R.id.note_text_title:{
                enableEditMode();
                mEditTitle.requestFocus();
                mEditTitle.setSelection(mEditTitle.length());
                break;
            }

            case R.id.toolbar_back_arrow:{
                finish();
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {

        if(mMode==EDIT_ENABLE_MODE){
            onClick(mCheck);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mViewTitle.setText(s.toString());

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
