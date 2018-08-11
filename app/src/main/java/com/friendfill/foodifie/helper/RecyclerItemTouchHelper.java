package com.friendfill.foodifie.helper;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.friendfill.foodifie.adapter.StaffAdapter;

/**
 * Created by FriendFill on 27-Jan-18.
 */

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {
    private RecyclerItemTouchHelperListener listener;
    private int type;
    private int swipeDirs, dragDirs;


    public RecyclerItemTouchHelper(int dragDirs, int swipeDirs, RecyclerItemTouchHelperListener listener, int type) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
        this.type = type;
        this.swipeDirs = swipeDirs;
        this.dragDirs = dragDirs;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            final View foregroundView = ((StaffAdapter.ItemViewHolder) viewHolder).viewForeground;
            if (swipeDirs == ItemTouchHelper.LEFT) {
                ((StaffAdapter.ItemViewHolder) (viewHolder)).background.setVisibility(View.VISIBLE);
                ((StaffAdapter.ItemViewHolder) (viewHolder)).viewBackground_Right.setVisibility(View.GONE);
            } else if (swipeDirs == ItemTouchHelper.RIGHT) {
                ((StaffAdapter.ItemViewHolder) (viewHolder)).background.setVisibility(View.GONE);
                ((StaffAdapter.ItemViewHolder) (viewHolder)).viewBackground_Right.setVisibility(View.VISIBLE);
            }
            getDefaultUIUtil().onSelected(foregroundView);
        }

    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((StaffAdapter.ItemViewHolder) viewHolder).viewForeground;
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final View foregroundView = ((StaffAdapter.ItemViewHolder) viewHolder).viewForeground;
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((StaffAdapter.ItemViewHolder) viewHolder).viewForeground;

        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    public interface RecyclerItemTouchHelperListener {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }
}