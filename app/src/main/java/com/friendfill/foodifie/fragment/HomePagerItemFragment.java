package com.friendfill.foodifie.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.friendfill.foodifie.R;
import com.friendfill.foodifie.model.HomePagerItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePagerItemFragment extends Fragment {

    static String ARG_PARAM1 = "position";
    ArrayList<HomePagerItem> homePagerItems;
    int position;
    @BindView(R.id.home_page_item_imageview)
    SimpleDraweeView home_page_item_imageview;
    @BindView(R.id.home_page_item_textview_subtitle)
    TextView home_page_item_textview_subtitle;
    @BindView(R.id.home_page_item_textview_title)
    TextView home_page_item_textview_title;

    public HomePagerItemFragment() {
        // Required empty public constructor
        homePagerItems = new ArrayList<>();
        initItems();

    }

    // TODO: Rename and change types and number of parameters
    public static HomePagerItemFragment newInstance(int param1) {
        HomePagerItemFragment fragment = new HomePagerItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_pager_item, container, false);
        ButterKnife.bind(this, view);
        HomePagerItem homePagerItem = homePagerItems.get(position);
        home_page_item_textview_subtitle.setText(homePagerItem.getSubtitle());
        home_page_item_textview_title.setText(homePagerItem.getTitle());
        Uri imageUri = Uri.parse(homePagerItem.getImage_src());
        home_page_item_imageview.setImageURI(imageUri);
        return view;
    }


    public void initItems() {
        homePagerItems.add(new HomePagerItem(1, "High Protein Breakfast", "For Adults and kids (7 ITEMS)", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcShnOP4dwczL8D5nTjgPHc13kA6evwmTkeA5Iv85l5SIQOmg4-EGA"));
        homePagerItems.add(new HomePagerItem(2, "Delicious Lunch", "For Adults only (5 ITEMS)", "http://www.baltana.com/files/wallpapers-2/Food-HD-Images-04860.jpg"));
        homePagerItems.add(new HomePagerItem(3, "Romantic Dinner Date", "For Adults and kids (7 ITEMS)", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT4vn7O-FTSdlBQlReuCyDBqDdRtf3ZuxL8ipKKVFAGrEvfW3lN"));
        homePagerItems.add(new HomePagerItem(4, "Hungry Hub Special", "For Adults and kids (7 ITEMS)", "http://images.all-free-download.com/images/graphiclarge/food_picture_01_hd_pictures_167558.jpg"));
    }
}
