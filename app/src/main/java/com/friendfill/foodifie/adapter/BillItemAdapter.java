package com.friendfill.foodifie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.friendfill.foodifie.BillDetailActivity;
import com.friendfill.foodifie.R;
import com.friendfill.foodifie.model.Item;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by FriendFill on 19-Jan-18.
 */

public class BillItemAdapter extends RecyclerView.Adapter<BillItemAdapter.ItemViewHolder> {
    ArrayList<Item> items;
    Context context;
    HashMap<Integer, String> status = new HashMap<>();
    HashMap<Integer, Integer> images = new HashMap<>();
    int type;

    public BillItemAdapter(Context context, ArrayList<Item> items, int type) {
        this.context = context;
        this.items = items;
        this.type = type;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = null;
        if (type == 0) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_bill_item_view, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_bill_item_edit_view, parent, false);
        }
        // set the view's size, margins, paddings and layout parameters
        ItemViewHolder vh = new ItemViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {

        // set the data in items
        if (type == 0) {
            holder.description.setText(items.get(position).getName() + " X " + items.get(position).getQty());
            holder.totalprice.setText("" + items.get(position).getQty() * items.get(position).getPrice());
        } else {
            Uri imageUri = Uri.parse(items.get(position).getImage());
            holder.simpleDraweeView.setImageURI(imageUri);

            holder.description.setText(items.get(position).getName());
            holder.qty.setText("" + items.get(position).getQty());
            holder.totalprice.setText("" + items.get(position).getQty() * items.get(position).getPrice());

            holder.inc_qty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Item item = items.get(position);
                    double old_qty = item.getQty();
                    old_qty++;
                    item.setQty(old_qty);
                    AdapterInterface adapterInterface = ((BillDetailActivity) context);
                    adapterInterface.UpdateItem(item);

                }
            });

            holder.desc_qty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Item item = items.get(position);
                    double old_qty = item.getQty();
                    old_qty--;
                    if (old_qty <= 0)
                        old_qty = 0;
                    item.setQty(old_qty);
                    AdapterInterface adapterInterface = ((BillDetailActivity) context);
                    adapterInterface.UpdateItem(item);

                }
            });

            holder.remove_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Item item = items.get(position);
                    AdapterInterface adapterInterface = ((BillDetailActivity) context);
                    adapterInterface.RemoveItem(item);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface AdapterInterface {
        void RemoveItem(Item item);

        void UpdateItem(Item item);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView description, totalprice, inc_qty, desc_qty, qty;
        SimpleDraweeView simpleDraweeView;
        ImageView remove_item;

        public ItemViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            description = (TextView) itemView.findViewById(R.id.item_description);
            totalprice = (TextView) itemView.findViewById(R.id.item_total);
            if (type == 1) {
                inc_qty = (TextView) itemView.findViewById(R.id.inc_qty);
                desc_qty = (TextView) itemView.findViewById(R.id.desc_qty);
                qty = (TextView) itemView.findViewById(R.id.qty);
                remove_item = (ImageView) itemView.findViewById(R.id.remove_item);
                simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.item_image);
            }

        }
    }

}
