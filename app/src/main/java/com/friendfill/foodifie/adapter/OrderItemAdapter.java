package com.friendfill.foodifie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.friendfill.foodifie.R;
import com.friendfill.foodifie.model.Item;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by FriendFill on 19-Jan-18.
 */

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ItemViewHolder> implements Filterable {
    ArrayList<Item> items;
    Context context;
    int type;
    HashMap<Integer, String> status = new HashMap<>();
    HashMap<Integer, Integer> images = new HashMap<>();
    private ArrayList<Item> mFilteredList;

    public OrderItemAdapter(Context context, ArrayList<Item> items, int type) {
        mFilteredList = new ArrayList<>();
        this.context = context;
        this.items = items;
        this.type = type;
        mFilteredList.addAll(items);
    }

    @Override
    public int getItemViewType(int position) {
        return this.type;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = null;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_order_item_view, parent, false);
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
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        holder.varient_recyclerview_item.setHasFixedSize(true);
        holder.varient_recyclerview_item.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        VarientAdapter varientAdapter = new VarientAdapter(this.context, items.get(position).getVarients(), 0);
        holder.varient_recyclerview_item.setAdapter(varientAdapter);


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
        TextView title, textView;
        SimpleDraweeView item_image;
        LinearLayout container;
        RecyclerView varient_recyclerview_item;

        public ItemViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            varient_recyclerview_item = (RecyclerView) itemView.findViewById(R.id.varient_recyclerview_item);
            container = (LinearLayout) itemView.findViewById(R.id.item_container);
            title = (TextView) itemView.findViewById(R.id.item_title_textview);
            item_image = (SimpleDraweeView) itemView.findViewById(R.id.item_imageview);
        }
    }
}
