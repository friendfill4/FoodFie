package com.friendfill.foodifie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.friendfill.foodifie.LoginActivity;
import com.friendfill.foodifie.MainActivity;
import com.friendfill.foodifie.R;
import com.friendfill.foodifie.fragment.OrderFragment;
import com.friendfill.foodifie.fragment.StaffFragment;
import com.friendfill.foodifie.model.MoreItem;
import com.prashantsolanki.secureprefmanager.SecurePrefManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by FriendFill on 19-Jan-18.
 */

public class MoreItemAdapter extends RecyclerView.Adapter<MoreItemAdapter.ItemViewHolder> {
    ArrayList<MoreItem> items;
    Context context;
    int type;
    HashMap<Integer, String> status = new HashMap<>();
    HashMap<Integer, Integer> images = new HashMap<>();

    public MoreItemAdapter(Context context, ArrayList<MoreItem> items, int type) {
        this.context = context;
        this.items = items;
        this.type = type;
        initImages();
    }

    private void initImages() {
        this.images.put(1, R.drawable.about);
        this.images.put(2, R.drawable.gallery);
        this.images.put(3, R.drawable.review);
        this.images.put(4, R.drawable.offers);
        this.images.put(5, R.drawable.staff);
        this.images.put(6, R.drawable.reservation);
        this.images.put(7, R.drawable.logout);
    }


    @Override
    public int getItemViewType(int position) {
        return this.type;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = null;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_more_item_view, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ItemViewHolder vh = new ItemViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        // set the data in items
        holder.item_image.setImageResource(this.images.get(items.get(position).getId()));
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open another activity on item click
                int i = items.get(position).getId();
                switch (i) {
                    case 5:
                        StaffFragment s = StaffFragment.newInstance("", "");
                        ((MainActivity) context).LoadFragment(s);
                        break;
                    case 6:
                        OrderFragment orderFragment = OrderFragment.newInstance("", "");
                        ((MainActivity) context).LoadFragment(orderFragment);
                        break;
                    case 7:
                        SecurePrefManager.with(context).set("isLoggedIn").value(false).go();
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                        context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
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
            item_image = (SimpleDraweeView) itemView.findViewById(R.id.item_imageview);
        }
    }

}
