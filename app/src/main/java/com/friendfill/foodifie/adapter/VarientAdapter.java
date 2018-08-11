package com.friendfill.foodifie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.friendfill.foodifie.R;
import com.friendfill.foodifie.model.Varient;

import java.util.ArrayList;

/**
 * Created by FriendFill on 19-Jan-18.
 */

public class VarientAdapter extends RecyclerView.Adapter<VarientAdapter.ItemViewHolder> implements Filterable {
    ArrayList<Varient> varients;
    Context context;
    int type;
    private ArrayList<Varient> mFilteredList;

    public VarientAdapter(Context context, ArrayList<Varient> varients, int type) {
        mFilteredList = new ArrayList<>();
        this.context = context;
        this.varients = varients;
        this.type = type;
        mFilteredList.addAll(varients);
    }

    @Override
    public int getItemViewType(int position) {
        return this.type;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = null;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_varient_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ItemViewHolder vh = new ItemViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        // set the data in items
        holder.varient_name.setText("(" + varients.get(position).getName() + ")");
        holder.varient_total.setText("Rs." + varients.get(position).getTotal());
        holder.varient_qty.setText(" X " + varients.get(position).getQty());
        holder.varient_price.setText("Rs." + varients.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        return varients.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    varients = mFilteredList;
                } else {

                    ArrayList<Varient> filteredList = new ArrayList<>();

                    for (Varient androidVersion : mFilteredList) {

                        if (androidVersion.getName().toLowerCase().contains(charString) || androidVersion.getName().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                        }
                    }

                    varients = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = varients;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                varients = (ArrayList<Varient>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView varient_name, varient_price, varient_qty, varient_total;

        public ItemViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            varient_name = (TextView) itemView.findViewById(R.id.varient_name);
            varient_price = (TextView) itemView.findViewById(R.id.varient_price);
            varient_qty = (TextView) itemView.findViewById(R.id.varient_qty);
            varient_total = (TextView) itemView.findViewById(R.id.varient_total);
        }
    }
}
