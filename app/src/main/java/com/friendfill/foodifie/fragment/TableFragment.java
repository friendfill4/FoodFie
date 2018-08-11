package com.friendfill.foodifie.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.friendfill.foodifie.ApplicationConfig;
import com.friendfill.foodifie.R;
import com.friendfill.foodifie.adapter.TableAdapter;
import com.friendfill.foodifie.model.Table;
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
 * Use the {@link TableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TableFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recyclerView_table)
    RecyclerView recyclerView_table;
    TableAdapter tableAdapter;
    ArrayList<Table> tables;
    @BindView(R.id.linearlayout_container)
    RelativeLayout linearlayoutContainer;
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public TableFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TableFragment newInstance(String param1, String param2) {
        TableFragment fragment = new TableFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Fragment Life", "Fragment Table On Create");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("Fragment Life", "Fragment Table On CreateView");
        View view = inflater.inflate(R.layout.fragment_table, container, false);
        ButterKnife.bind(this, view);
        mListener.SetTitleBarTitle(getResources().getString(R.string.table_activity_title));
        mListener.SetNavigationItem(R.id.menu_item_table);
        tables = new ArrayList<>();
        Table();
        return view;
    }

    public void SetUpView() {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView_table.setLayoutManager(staggeredGridLayoutManager);
        tableAdapter = new TableAdapter(getActivity(), tables);
        recyclerView_table.setAdapter(tableAdapter);
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
        Log.d("Fragment Life", "Fragment Table On Attach");
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
        Log.d("Fragment Life", "Fragment Table On Detach");
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void Table() {
        try {
            ApplicationConfig.setProgressDialog(getActivity(), "Fetching..", "Please wait");
            ApplicationConfig.progressDialog.show();
            APIInterface request = APIClient.getClient().create(APIInterface.class);
            Call<ResponseBody> call = request.Table("application/json", "1", "100");

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
                                    JSONObject jTable = data.getJSONObject(j);
                                    int id = Integer.parseInt(jTable.getString("id"));
                                    String name = jTable.getString("name");
                                    String description = jTable.getString("description");
                                    String status = jTable.getString("status");
                                    int status_slug = (status.equals("occupied")) ? 1 : 0;
                                    int capacity = Integer.parseInt(jTable.getString("capacity"));
                                    int adjustment = Integer.parseInt(jTable.getString("adjustment"));
                                    Table table = new Table(id, name, description, capacity, adjustment, status_slug);
                                    tables.add(table);
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

        void SetNavigationItem(int item);

    }
}
