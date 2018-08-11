package com.friendfill.foodifie.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.friendfill.foodifie.ItemDescriptionActivity;
import com.friendfill.foodifie.R;
import com.friendfill.foodifie.model.Item;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by FriendFill on 19-Jan-18.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> implements Filterable {
    ArrayList<Item> items;
    Context context;
    int type;
    HashMap<Integer, String> status = new HashMap<>();
    HashMap<Integer, Integer> images = new HashMap<>();
    private ArrayList<Item> mFilteredList;

    public ItemAdapter(Context context, ArrayList<Item> items, int type) {
        mFilteredList = new ArrayList<>();
        this.context = context;
        this.items = items;
        this.type = type;
        mFilteredList.addAll(items);
        initStatus();
        initImages();
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
    public int getItemViewType(int position) {
        return this.type;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = null;
        if (type == 1) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_view_alert, parent, false);

        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_view, parent, false);
        }
        // set the view's size, margins, paddings and layout parameters
        ItemViewHolder vh = new ItemViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        // set the data in items
        holder.title.setText(items.get(position).getName());
        Uri imageUri = Uri.parse(items.get(position).getImage());
        holder.item_image.setImageURI(imageUri);
        holder.subtitle.setText("Rs." + items.get(position).getPrice());
        // implement setOnClickListener event on item view.
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open another activity on item click
                Intent intent = new Intent(context, ItemDescriptionActivity.class);
                intent.putExtra("item", items.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    items = mFilteredList;
                } else {

                    ArrayList<Item> filteredList = new ArrayList<>();

                    for (Item androidVersion : mFilteredList) {

                        if (androidVersion.getName().toLowerCase().contains(charString) || androidVersion.getName().toLowerCase().contains(charString) || androidVersion.getCategory().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                        }
                    }

                    items = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = items;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                items = (ArrayList<Item>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView title, subtitle, status;
        SimpleDraweeView item_image;
        FrameLayout container;

        public ItemViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            container = (FrameLayout) itemView.findViewById(R.id.item_container);
            title = (TextView) itemView.findViewById(R.id.item_title_textview);
            subtitle = (TextView) itemView.findViewById(R.id.item_subtitle_textview);
            item_image = (SimpleDraweeView) itemView.findViewById(R.id.item_imageview);
        }
    }
}
