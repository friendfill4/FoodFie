package com.friendfill.foodifie.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.friendfill.foodifie.R;
import com.friendfill.foodifie.adapter.ItemAdapter;
import com.friendfill.foodifie.model.Category;
import com.friendfill.foodifie.model.Item;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchItemFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchItemFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recyclerView_menu)
    RecyclerView recyclerView_item;
    @BindView(R.id.search_item_edittext_searchbar)
    EditText search_item_edittext_searchbar;
    ItemAdapter itemAdapter;
    ArrayList<Item> items;
    private Category mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public SearchItemFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SearchItemFragment newInstance(Category param1, String param2) {
        SearchItemFragment fragment = new SearchItemFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = (Category) getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_item, container, false);
        ButterKnife.bind(this, view);
        mListener.SetTitleBarTitle(getResources().getString(R.string.search_activity_title));
        mListener.SetToolbarBack(this);
        getActivity().invalidateOptionsMenu();
        initItems();
        itemAdapter = new ItemAdapter(getContext(), items, 2);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL);
        recyclerView_item.setLayoutManager(staggeredGridLayoutManager);
        recyclerView_item.setAdapter(itemAdapter);
        search_item_edittext_searchbar.requestFocus();
        search_item_edittext_searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String newText = search_item_edittext_searchbar.getText().toString();
                itemAdapter.getFilter().filter(newText);

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
    }

    private void initItems() {
        items = new ArrayList<>();
        items.add(new Item(1, "Panner Tikka", "Lorem Ipsum Pasrunc", "Punjabi", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcShnOP4dwczL8D5nTjgPHc13kA6evwmTkeA5Iv85l5SIQOmg4-EGA", 120.00));
        items.add(new Item(2, "Danhi Handi", "Lorem Ipsum Pasrunc", "Punjabi", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT4vn7O-FTSdlBQlReuCyDBqDdRtf3ZuxL8ipKKVFAGrEvfW3lN", 150.00));
        items.add(new Item(3, "Italian Pizza", "Lorem Ipsum Pasrunc", "Pizza", "http://www.baltana.com/files/wallpapers-2/Food-HD-Images-04860.jpg", 240.00));
        items.add(new Item(4, "Veg.Momoes", "Lorem Ipsum Pasrunc", "Momoes", "http://images.all-free-download.com/images/graphiclarge/food_picture_01_hd_pictures_167558.jpg", 290.00));
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mListener.RemoveToolbarBack(this);
            mListener.RemoveFragment(this);
        }
        return false;
    }

    @Override
    public void onStop() {
        super.onStop();
        mListener.RemoveToolbarBack(this);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
       /* MenuInflater inflater=getActivity().getMenuInflater();
        inflater.inflate(R.menu.main_search, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setIconified(false);
        search(searchView);
        super.onCreateOptionsMenu(menu, inflater);*/

    }

    private void search(SearchView searchView) {
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setHintTextColor(getResources().getColor(R.color.white));
        searchEditText.requestFocus();
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.setIconifiedByDefault(false);
        searchView.requestFocusFromTouch();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                itemAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

        void SetTitleBarTitle(String title);

        void SetToolbarBack(Fragment fragment);

        void RemoveToolbarBack(Fragment fragment);

        void RemoveFragment(Fragment fragment);
    }
}
