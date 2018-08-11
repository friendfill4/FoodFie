package com.friendfill.foodifie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.friendfill.foodifie.BillDetailActivity;
import com.friendfill.foodifie.R;
import com.friendfill.foodifie.model.Bill;

import java.util.ArrayList;

/**
 * Created by FriendFill on 19-Jan-18.
 */

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ItemViewHolder> implements Filterable {
    ArrayList<Bill> bills;
    Context context;
    private ArrayList<Bill> mFilteredList;

    public BillAdapter(Context context, ArrayList<Bill> bills) {
        mFilteredList = new ArrayList<>();
        this.context = context;
        this.bills = bills;
        mFilteredList.addAll(bills);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_bill_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ItemViewHolder vh = new ItemViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        // set the data in items
        holder.bill_no.setText("#" + bills.get(position).getBill_no());
        holder.bill_grandtotal.setText("Rs. " + bills.get(position).getGrandtotal());
        holder.bill_table_no.setText(bills.get(position).getTable().getTitle());
        holder.customer_name.setText(bills.get(position).getCustomer().getName());

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open another activity on item click
                Intent intent = new Intent(context, BillDetailActivity.class);
                intent.putExtra("bill", bills.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bills.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    bills = mFilteredList;
                } else {

                    ArrayList<Bill> filteredList = new ArrayList<>();

                    for (Bill androidVersion : mFilteredList) {

                        if (androidVersion.getBill_no().toLowerCase().contains(charString) || androidVersion.getTable().getTitle().toLowerCase().contains(charString) || androidVersion.getCustomer().getName().toLowerCase().contains(charString) || androidVersion.getCustomer().getMobile().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                        }
                    }

                    bills = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = bills;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                bills = (ArrayList<Bill>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView bill_no, bill_table_no, customer_name, bill_grandtotal;
        CardView container;

        public ItemViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            container = (CardView) itemView.findViewById(R.id.bill_container);
            bill_grandtotal = (TextView) itemView.findViewById(R.id.bill_grand_total);
            bill_table_no = (TextView) itemView.findViewById(R.id.bill_table_no);
            bill_no = (TextView) itemView.findViewById(R.id.bill_no);
            customer_name = (TextView) itemView.findViewById(R.id.bill_customer_name);
        }
    }
}
