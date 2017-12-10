package com.girish.hackernews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.girish.hackernews.callbacks.ClickListener;

/**
 * Created by Girish on 09-Dec-17.
 */
public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

    ClickListener listener;
    GestureDetector gestureDetector;

    public RecyclerTouchListener(Context context, RecyclerView recyclerView, ClickListener listener) {
        this.listener = listener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
//                    Log.i("Touch", "SingleTapTouchEvent " + e.toString());
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
//                    Log.i("Touch", "LongPressTouchEvent " + e.toString());
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//            Log.i("Touch", "InterceptTouchEvent " + gestureDetector.onTouchEvent(e) + " " + e.toString());
        View view = rv.findChildViewUnder(e.getX(), e.getY());
        if (listener != null && view != null && gestureDetector.onTouchEvent(e))
            listener.onClick(view, rv.getChildLayoutPosition(view));
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//            Log.i("Touch", "TouchEvent " + e.toString());
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
