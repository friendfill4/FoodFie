package com.friendfill.foodifie.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.friendfill.foodifie.MainActivity;
import com.friendfill.foodifie.R;
import com.friendfill.foodifie.fragment.MenuMoreFragment;
import com.friendfill.foodifie.model.Category;

import java.util.ArrayList;

/**
 * Created by FriendFill on 19-Jan-18.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    ArrayList<Category> categories;
    Context context;
    RecyclerView.RecycledViewPool viewPool;
    int type;

    public CategoryAdapter(Context context, ArrayList<Category> categories, int type) {
        this.context = context;
        this.categories = categories;
        viewPool = new RecyclerView.RecycledViewPool();
        this.type = type;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_category_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        CategoryViewHolder vh = new CategoryViewHolder(v, type); // pass the view to View Holder
        vh.item_recyclerview.setRecycledViewPool(viewPool);
        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, final int position) {
        // set the data in items
        holder.title.setText(categories.get(position).getName());
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        holder.item_recyclerview.setHasFixedSize(true);
        holder.item_recyclerview.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL));
        ItemAdapter itemAdapter = new ItemAdapter(this.context, categories.get(position).getItems(), holder.type);
        holder.item_recyclerview.setAdapter(itemAdapter);
        holder.more_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open another activity on item click
                replaceFragment(categories.get(position));


            }
        });
    }

    private void replaceFragment(Category category) {
        if (this.context instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) context;
            MenuMoreFragment menuMoreFragment = MenuMoreFragment.newInstance(category, "");
            mainActivity.LoadFragment(menuMoreFragment);
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView title;
        RecyclerView item_recyclerview;
        TextView more_textview;
        int type;

        public CategoryViewHolder(View itemView, int type) {
            super(itemView);
            // get the reference of item view's
            this.type = type;
            more_textview = (TextView) itemView.findViewById(R.id.category_textview_more);
            title = (TextView) itemView.findViewById(R.id.category_textview_title);
            item_recyclerview = (RecyclerView) itemView.findViewById(R.id.category_recyclerview_item);
            item_recyclerview.setNestedScrollingEnabled(false);
        }
    }
}
