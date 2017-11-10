package com.rrioo.smartlife.ui.main.adapter.home;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/10/12 10:58 1.0
 * @time 2017/10/12 10:58
 * @project secQreNew3.0 com.rrioo.smartlife.ui.main.adapter.home
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/10/12 10:58
 */

public abstract class SimpleItemTouchListener extends RecyclerView.SimpleOnItemTouchListener implements GestureDetector.OnGestureListener{

    private GestureDetectorCompat mGestureDetector;
    private RecyclerView recyclerView;

    public SimpleItemTouchListener(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        mGestureDetector = new GestureDetectorCompat(recyclerView.getContext(),this);
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
//        super.onTouchEvent(rv, e);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        return super.onInterceptTouchEvent(rv, e);
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
        View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
        if (view!=null) {
            RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
            onItemLongClick(recyclerView,viewHolder);
        }
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    public abstract void onItemLongClick(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder);
}
