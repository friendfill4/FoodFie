package com.friendfill.foodifie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.friendfill.foodifie.R;
import com.friendfill.foodifie.model.Item;
import com.friendfill.foodifie.model.Review;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by FriendFill on 19-Jan-18.
 */

public class ItemReviewAdapter extends RecyclerView.Adapter<ItemReviewAdapter.ItemViewHolder> {
    ArrayList<Review> items;
    Context context;
    HashMap<Integer, String> status = new HashMap<>();
    HashMap<Integer, Integer> images = new HashMap<>();
    String[] review_string = new String[]{"", "Very Bad", "Average", "Awesome", "Delicious", "I love it!!"};
    int type;

    public ItemReviewAdapter(Context context, ArrayList<Review> items, int type) {
        this.context = context;
        this.items = items;
        this.type = type;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = null;
        if (type == 0) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_review, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_review, parent, false);
        }
        // set the view's size, margins, paddings and layout parameters
        ItemViewHolder vh = new ItemViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {

        // set the data in items
        if (type == 0) {
            holder.description.setText(items.get(position).getCustomer_name());
        } else {
            if (items.get(position).getCustomer_image() != null && !items.get(position).getCustomer_image().equals("")) {
                Uri imageUri = Uri.parse(items.get(position).getCustomer_image());
                holder.simpleDraweeView.setImageURI(imageUri);
            }
            holder.description.setText(items.get(position).getCustomer_name());
            holder.review_text.setText(Html.fromHtml(items.get(position).getReview()));
            holder.ratingBar.setRating(Float.parseFloat("" + items.get(position).getRate()));


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
        TextView description, review_text;
        RatingBar ratingBar;
        SimpleDraweeView simpleDraweeView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            description = (TextView) itemView.findViewById(R.id.user_name);
            if (type == 1) {
                ratingBar = (RatingBar) itemView.findViewById(R.id.ratingbar_item);
                simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.item_image);
                review_text = (TextView) itemView.findViewById(R.id.review_text);
            }

        }
    }

}
