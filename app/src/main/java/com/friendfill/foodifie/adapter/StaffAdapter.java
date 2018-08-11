package com.friendfill.foodifie.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.friendfill.foodifie.R;
import com.friendfill.foodifie.model.Staff;

import java.util.ArrayList;

/**
 * Created by FriendFill on 19-Jan-18.
 */

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ItemViewHolder> implements Filterable {
    ArrayList<Staff> staff, mFilteredList;
    Context context;
    int type;

    public StaffAdapter(Context context, ArrayList<Staff> staff, int type) {
        this.context = context;
        this.staff = staff;
        this.type = type;
        mFilteredList = new ArrayList<>(this.staff);
    }


    @Override
    public int getItemViewType(int position) {
        return this.type;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = null;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_staff_item, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ItemViewHolder vh = new ItemViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        // set the data in items
        holder.name.setText(staff.get(position).getName());
        holder.designation.setText(staff.get(position).getDesignation());
        holder.shift.setText(staff.get(position).getShift());
        Uri uri = Uri.parse(staff.get(position).getImage());
        holder.staff_image.setImageURI(uri);
        if (staff.get(position).isLoggedIn()) {
            holder.online_status.setBackground(context.getDrawable(R.drawable.online_circle));
        } else {
            holder.online_status.setBackground(context.getDrawable(R.drawable.offline_circle));
        }
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return staff.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    staff = mFilteredList;
                } else {

                    ArrayList<Staff> filteredList = new ArrayList<>();

                    for (Staff androidVersion : mFilteredList) {

                        if (androidVersion.getName().toLowerCase().contains(charString) || androidVersion.getEmail().toLowerCase().contains(charString) || androidVersion.getDesignation().toLowerCase().contains(charString) || androidVersion.getShift().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                        }
                    }

                    staff = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = staff;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                staff = (ArrayList<Staff>) filterResults.values;
                notifyDataSetChanged();
            }
        };

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout background, viewForeground, viewBackground_Right;
        // init the item view's
        TextView name, designation, shift, online_status;
        SimpleDraweeView staff_image;
        ImageView isLogged;
        FrameLayout container;

        public ItemViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            name = (TextView) itemView.findViewById(R.id.staff_name);
            designation = (TextView) itemView.findViewById(R.id.staff_designation);
            shift = (TextView) itemView.findViewById(R.id.staff_shift);
            container = (FrameLayout) itemView.findViewById(R.id.item_container);
            staff_image = (SimpleDraweeView) itemView.findViewById(R.id.staff_image);
            online_status = (TextView) itemView.findViewById(R.id.online_status);
            background = (LinearLayout) itemView.findViewById(R.id.viewBackground);
            viewForeground = (LinearLayout) itemView.findViewById(R.id.viewForeground);
            viewBackground_Right = (LinearLayout) itemView.findViewById(R.id.viewBackground_Right);
        }
    }

}
