package com.friendfill.foodifie.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.friendfill.foodifie.ApplicationConfig;
import com.friendfill.foodifie.R;
import com.friendfill.foodifie.adapter.CategoryAdapter;
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
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recyclerView_menu)
    RecyclerView recyclerView_category;
    CategoryAdapter categoryAdapter;
    ArrayList<Item> items;
    ArrayList<Category> categories;
    @BindView(R.id.linearlayout_container)
    RelativeLayout linearlayoutContainer;
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public MenuFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Fragment Life", "Fragment Menu On Create");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Fragment Life", "Fragment Menu On CreateView");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        ButterKnife.bind(this, view);
        mListener.SetTitleBarTitle(getResources().getString(R.string.menu_activity_title));
        mListener.SetNavigationItem(R.id.menu_item_menu);
        categories = new ArrayList<>();
        Category();
        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void SetUpView() {
        categoryAdapter = new CategoryAdapter(getContext(), categories, 1);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL);
        recyclerView_category.setLayoutManager(staggeredGridLayoutManager);
        recyclerView_category.setAdapter(categoryAdapter);

    }

    @Override
    public void onAttach(Context context) {
        Log.d("Fragment Life", "Fragment Menu On Attach");
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
        Log.d("Fragment Life", "Fragment Menu On Detach");
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    private void Category() {
        try {
            ApplicationConfig.setProgressDialog(getActivity(), "Fetching..", "Please wait");
            ApplicationConfig.progressDialog.show();
            APIInterface request = APIClient.getClient().create(APIInterface.class);
            Call<ResponseBody> call = request.Category("application/json");

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ApplicationConfig.progressDialog.dismiss();
                    try {
                        String json_response = response.body().string();
                        JSONArray json = new JSONArray(json_response);
                        if (json.length() != 0) {
                            for (int i = 0; i < json.length(); i++) {
                                JSONObject jCategory = json.getJSONObject(i);
                                ArrayList<Item> items = new ArrayList<>();
                                JSONArray data = jCategory.getJSONObject("items").getJSONArray("data");
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
                                int category_id = Integer.parseInt(jCategory.getString("id"));
                                String category_name = jCategory.getString("name");
                                String category_description = jCategory.getString("description");
                                Category category = new Category(category_id, category_name, category_description, items);
                                categories.add(category);
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

        void SetNavigationItem(int item);

    }

}
