package com.friendfill.foodifie.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.friendfill.foodifie.ApplicationConfig;
import com.friendfill.foodifie.R;
import com.friendfill.foodifie.adapter.ItemAdapter;
import com.friendfill.foodifie.model.Category;
import com.friendfill.foodifie.model.Item;
import com.friendfill.foodifie.network.APIClient;
import com.friendfill.foodifie.network.APIInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MenuMoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuMoreFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recyclerView_menu)
    RecyclerView recyclerView_item;
    ItemAdapter itemAdapter;
    ArrayList<Item> items;
    @BindView(R.id.linearlayout_container)
    RelativeLayout linearlayoutContainer;
    private Category mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public MenuMoreFragment() {
        // Required empty public constructor

        setHasOptionsMenu(true);
    }

    // TODO: Rename and change types and number of parameters
    public static MenuMoreFragment newInstance(Category param1, String param2) {
        MenuMoreFragment fragment = new MenuMoreFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        ButterKnife.bind(this, view);
        mListener.SetTitleBarTitle(mParam1.getName());
        mListener.SetToolbarBack(this);
        items = new ArrayList<>();
        Category();


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void SetUpView() {
        itemAdapter = new ItemAdapter(getContext(), items, 2);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL);
        recyclerView_item.setLayoutManager(staggeredGridLayoutManager);
        recyclerView_item.setAdapter(itemAdapter);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mListener.RemoveToolbarBack(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        super.onStop();
        mListener.RemoveToolbarBack(this);
    }

    private void Category() {
        try {
            ApplicationConfig.setProgressDialog(getActivity(), "Fetching..", "Please wait");
            ApplicationConfig.progressDialog.show();
            APIInterface request = APIClient.getClient().create(APIInterface.class);
            Call<ResponseBody> call = request.CategoryItem("application/json", "" + mParam1.getId(), "1", "100");

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ApplicationConfig.progressDialog.dismiss();
                    try {
                        String json_response = response.body().string();
                        JSONObject json = new JSONObject(json_response);
                        if (json != null) {
                            JSONArray data = json.getJSONArray("data");
                            if (data.length() != 0) {
                                for (int j = 0; j < data.length(); j++) {
                                    JSONObject jItem = data.getJSONObject(j);
                                    int id = Integer.parseInt(jItem.getString("id"));
                                    String name = jItem.getString("name");
                                    String description = jItem.getString("description");
                                    String category = jItem.getString("category");
                                    String serving = jItem.getString("serving");
                                    String image = jItem.getString("image");
                                    double rating = Double.parseDouble(jItem.getString("rating"));
                                    double price = Double.parseDouble(jItem.getString("price"));
                                    Item item = new Item(id, name, description, category, serving, image, rating, price);
                                    items.add(item);
                                }
                            }

                            SetUpView();
                        } else {
                            Snackbar.make(linearlayoutContainer, "No Item found try later", BaseTransientBottomBar.LENGTH_LONG).show();

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        Snackbar.make(linearlayoutContainer, "" + e.getLocalizedMessage(), BaseTransientBottomBar.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    ApplicationConfig.progressDialog.dismiss();
                    Snackbar.make(linearlayoutContainer, "" + t.getLocalizedMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            ApplicationConfig.progressDialog.dismiss();
            e.printStackTrace();
            Snackbar.make(linearlayoutContainer, "" + e.getLocalizedMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
        }
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
