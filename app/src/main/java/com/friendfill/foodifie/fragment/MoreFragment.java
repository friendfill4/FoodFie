package com.friendfill.foodifie.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.friendfill.foodifie.R;
import com.friendfill.foodifie.adapter.MoreItemAdapter;
import com.friendfill.foodifie.model.Category;
import com.friendfill.foodifie.model.MoreItem;
import com.prashantsolanki.secureprefmanager.SecurePrefManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MoreFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoreFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recyclerView_menu)
    RecyclerView recyclerView_item;
    MoreItemAdapter itemAdapter;
    ArrayList<MoreItem> items;
    private Category mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public MoreFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MoreFragment newInstance(Category param1, String param2) {
        MoreFragment fragment = new MoreFragment();
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
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        ButterKnife.bind(this, view);
        mListener.SetTitleBarTitle(getResources().getString(R.string.more_activity_title));
        mListener.SetToolbarBack(this);
        getActivity().invalidateOptionsMenu();
        initItems();
        itemAdapter = new MoreItemAdapter(getContext(), items, 2);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL);
        recyclerView_item.setLayoutManager(staggeredGridLayoutManager);
        recyclerView_item.setAdapter(itemAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
    }

    private void initItems() {
        items = new ArrayList<>();
        items.add(new MoreItem(1, "About us"));
        items.add(new MoreItem(2, "Gallery"));
        items.add(new MoreItem(3, "Reviews"));
        items.add(new MoreItem(4, "Offers"));
        items.add(new MoreItem(5, "Staff"));
        items.add(new MoreItem(6, "Reservation"));
        if (SecurePrefManager.with(getContext()).get("isLoggedIn").defaultValue(false).go())
            items.add(new MoreItem(7, "Logout"));
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
