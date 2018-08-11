package com.friendfill.foodifie.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.friendfill.foodifie.R;
import com.friendfill.foodifie.model.Table;

import java.util.List;

/**
 * Created by FriendFill on 19-Jan-18.
 */

public class BillTableAdapter extends ArrayAdapter<String> {

    private final LayoutInflater mInflater;
    private final Context mContext;
    private final List<Table> items;
    private final int mResource;

    public BillTableAdapter(@NonNull Context context, @LayoutRes int resource,
                            @NonNull List objects) {
        super(context, resource, 0, objects);

        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        items = objects;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public @NonNull
    View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        final View view = mInflater.inflate(mResource, parent, false);

        TextView table_name = (TextView) view.findViewById(R.id.table_name);

        table_name.setText(items.get(position).getTitle());
        return view;
    }
}