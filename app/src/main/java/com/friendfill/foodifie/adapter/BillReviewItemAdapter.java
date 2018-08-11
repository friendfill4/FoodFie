package com.friendfill.foodifie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.friendfill.foodifie.R;
import com.friendfill.foodifie.model.Item;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by FriendFill on 19-Jan-18.
 */

public class BillReviewItemAdapter extends RecyclerView.Adapter<BillReviewItemAdapter.ItemViewHolder> {
    ArrayList<Item> items;
    Context context;
    HashMap<Integer, String> status = new HashMap<>();
    HashMap<Integer, Integer> images = new HashMap<>();
    String[] review_string = new String[]{"", "Very Bad", "Average", "Awesome", "Delicious", "I love it!!"};
    int type;

    public BillReviewItemAdapter(Context context, ArrayList<Item> items, int type) {
        this.context = context;
        this.items = items;
        this.type = type;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = null;
        if (type == 0) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_bill_item_review, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_bill_item_review, parent, false);
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
        } else {
            Uri imageUri = Uri.parse(items.get(position).getImage());
            holder.simpleDraweeView.setImageURI(imageUri);
            holder.description.setText(items.get(position).getName());
            holder.ratingBar.setRating(Float.parseFloat("" + items.get(position).getRating()));
            holder.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    items.get(position).setRating(v);
                    items.get(position).setReview(review_string[(int) v]);
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
        TextView description;
        RatingBar ratingBar;
        SimpleDraweeView simpleDraweeView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            description = (TextView) itemView.findViewById(R.id.item_description);
            if (type == 1) {
                ratingBar = (RatingBar) itemView.findViewById(R.id.ratingbar_item);
                simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.item_image);
            }

        }
    }

}
