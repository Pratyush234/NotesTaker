package com.example.praty.notestaker;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class NotesItemDecorator extends RecyclerView.ItemDecoration{

    private final int verticalSpaceHeight;

    public NotesItemDecorator(int verticalSpaceHeight) {
        this.verticalSpaceHeight = verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom=verticalSpaceHeight;
    }
}
