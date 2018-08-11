package com.friendfill.foodifie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.friendfill.foodifie.R;
import com.friendfill.foodifie.model.Table;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by FriendFill on 19-Jan-18.
 */

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder> {
    public Listener mListener;
    ArrayList<Table> tables;
    Context context;
    HashMap<Integer, String> status = new HashMap<>();
    HashMap<Integer, Integer> images = new HashMap<>();

    public TableAdapter(Context context, ArrayList<Table> tables) {
        this.context = context;
        this.tables = tables;
        initStatus();
        initImages();

        if (context instanceof Listener) {
            mListener = (Listener) context;
        }
    }

    private void initImages() {
        this.images.put(2, R.drawable.table2);
        this.images.put(4, R.drawable.table4);
        this.images.put(5, R.drawable.table5);
        this.images.put(10, R.drawable.table10);
    }

    private void initStatus() {
        this.status.put(0, "Available");
        this.status.put(1, "Occupied");
    }

    @Override
    public TableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_table_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        TableViewHolder vh = new TableViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(TableViewHolder holder, final int position) {
        // set the data in items
        holder.title.setText(tables.get(position).getTitle());
        holder.table_image.setActualImageResource(this.images.get(tables.get(position).getCapacity()));
        holder.status.setText(this.status.get(tables.get(position).getStatus()));
        if (tables.get(position).getStatus() == 0) {
            holder.text_container.setBackgroundColor(this.context.getResources().getColor(R.color.color_available));
        } else {
            holder.text_container.setBackgroundColor(this.context.getResources().getColor(R.color.color_occupied));

        }
        holder.subtitle.setText(tables.get(position).getCapacity() + " + " + tables.get(position).getAdjustment() + " Persons");
        // implement setOnClickListener event on item view.
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open another activity on item click

                if (mListener != null)
                    mListener.onClick(tables.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return tables.size();
    }

    public interface Listener {
        void onClick(Table table);
    }

    public class TableViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView title, subtitle, status;
        SimpleDraweeView table_image;
        FrameLayout container;
        LinearLayout text_container;

        public TableViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            container = (FrameLayout) itemView.findViewById(R.id.table_container);
            title = (TextView) itemView.findViewById(R.id.table_title);
            subtitle = (TextView) itemView.findViewById(R.id.table_subtitle);
            status = (TextView) itemView.findViewById(R.id.table_status);
            table_image = (SimpleDraweeView) itemView.findViewById(R.id.table_imageview);
            text_container = (LinearLayout) itemView.findViewById(R.id.text_container);
        }
    }
}
