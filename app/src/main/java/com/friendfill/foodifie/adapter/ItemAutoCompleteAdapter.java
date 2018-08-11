package com.friendfill.foodifie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.friendfill.foodifie.R;
import com.friendfill.foodifie.model.Item;

import java.util.ArrayList;

/**
 * Created by FriendFill on 23-Jan-18.
 */

public class ItemAutoCompleteAdapter extends ArrayAdapter<Item> {
    ArrayList<Item> items;
    ArrayList<Item> suggestions;
    ArrayList<Item> tempItem;
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                suggestions.clear();
                for (Item item : tempItem) {
                    if (item.getName().toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                        suggestions.add(item);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.count = suggestions.size();
                filterResults.values = suggestions;
                return filterResults;
            } else {
                return new FilterResults();
            }

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<Item> i = (ArrayList<Item>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (Item item : i) {
                    add(item);
                    notifyDataSetChanged();
                }
            }

        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            Item item = (Item) resultValue;
            return item.getName();
        }
    };

    public ItemAutoCompleteAdapter(@NonNull Context context, int resource, ArrayList<Item> items) {
        super(context, resource);
        this.items = items;
        this.tempItem = new ArrayList<Item>(items);
        this.suggestions = new ArrayList<Item>(items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Item item = getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_item_view_autocomplete, parent, false);
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) convertView.findViewById(R.id.item_imageview);
        TextView textView_item_name, textView_item_price;
        textView_item_name = (TextView) convertView.findViewById(R.id.textview_item_name);
        textView_item_price = (TextView) convertView.findViewById(R.id.textview_item_price);

        textView_item_name.setText(item.getName());
        textView_item_price.setText("" + item.getPrice());
        Uri uri = Uri.parse(item.getImage());
        simpleDraweeView.setImageURI(uri);
        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }
}
