package com.friendfill.foodifie.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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

import com.friendfill.foodifie.ApplicationConfig;
import com.friendfill.foodifie.BookTableActivity;
import com.friendfill.foodifie.R;
import com.friendfill.foodifie.adapter.BillAdapter;
import com.friendfill.foodifie.model.Bill;
import com.friendfill.foodifie.model.Category;
import com.friendfill.foodifie.model.Customer;
import com.friendfill.foodifie.model.Item;
import com.friendfill.foodifie.model.Table;
import com.friendfill.foodifie.network.APIClient;
import com.friendfill.foodifie.network.APIInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

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
 * Use the {@link BillFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BillFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recyclerView_menu)
    RecyclerView recyclerView_bill;
    @BindView(R.id.search_bill_edittext_searchbar)
    EditText search_item_edittext_searchbar;
    BillAdapter billAdapter;
    ArrayList<Bill> bills;
    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.card_view)
    CardView cardView;
    @BindView(R.id.coordinate_container)
    CoordinatorLayout coordinateContainer;
    private Category mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public BillFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static BillFragment newInstance(Category param1, String param2) {
        BillFragment fragment = new BillFragment();
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
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        ButterKnife.bind(this, view);
        mListener.SetTitleBarTitle(getResources().getString(R.string.bill_actibity_title));
        mListener.SetToolbarBack(this);
        getActivity().invalidateOptionsMenu();

        search_item_edittext_searchbar.requestFocus();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), BookTableActivity.class);
                startActivity(i);
            }
        });

        return view;
    }

    public void SetUpView() {
        billAdapter = new BillAdapter(getContext(), bills);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL);
        recyclerView_bill.setLayoutManager(staggeredGridLayoutManager);
        recyclerView_bill.setAdapter(billAdapter);
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
                billAdapter.getFilter().filter(newText);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
        bills = new ArrayList<>();
        Bill();
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
    public void onDestroyView() {
        super.onDestroyView();
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

    }

    private void Bill() {
        try {
            ApplicationConfig.setProgressDialog(getActivity(), "Fetching..", "Please wait");
            ApplicationConfig.progressDialog.show();
            APIInterface request = APIClient.getClient().create(APIInterface.class);
            Call<ResponseBody> call = request.Bill("application/json", "1", "100");

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
                                    JSONObject jBill = data.getJSONObject(j);
                                    int id = Integer.parseInt(jBill.getString("id"));
                                    String bill_no = jBill.getString("bill_no");
                                    Date bill_date = ApplicationConfig.StringToDate(jBill.getString("bill_date"));
                                    double grandtotal = Double.parseDouble(jBill.getString("grandtotal"));

                                    JSONObject jTable = jBill.getJSONObject("table");
                                    int table_id = Integer.parseInt(jTable.getString("id"));
                                    String name = jTable.getString("name");
                                    String description = jTable.getString("description");
                                    String status = jTable.getString("status");
                                    int status_slug = (status.equals("occupied")) ? 1 : 0;
                                    int capacity = Integer.parseInt(jTable.getString("capacity"));
                                    int adjustment = Integer.parseInt(jTable.getString("adjustment"));
                                    Table table = new Table(table_id, name, description, capacity, adjustment, status_slug);


                                    JSONObject jCustomer = jBill.getJSONObject("customer");
                                    int customer_id = Integer.parseInt(jCustomer.getString("id"));
                                    String customer_name = jCustomer.getString("name");
                                    String customer_email = jCustomer.getString("email");
                                    String customer_phone = jCustomer.getString("phone");
                                    String customer_address = jCustomer.getString("address");
                                    Date customer_birthdate = ApplicationConfig.StringToDate(jCustomer.getString("birthdate"));
                                    Customer customer = new Customer(customer_id, customer_name, customer_phone, customer_email, customer_address, customer_birthdate);

                                    Bill bill = new Bill(id, bill_no, bill_date, customer, table, grandtotal, new ArrayList<Item>());
                                    bills.add(bill);
                                }
                            }

                            SetUpView();
                        } else {
                            Snackbar.make(coordinateContainer, "No Item found try later", BaseTransientBottomBar.LENGTH_LONG).show();

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        Snackbar.make(coordinateContainer, "" + e.getLocalizedMessage(), BaseTransientBottomBar.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    ApplicationConfig.progressDialog.dismiss();
                    Snackbar.make(coordinateContainer, "" + t.getLocalizedMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            ApplicationConfig.progressDialog.dismiss();
            e.printStackTrace();
            Snackbar.make(coordinateContainer, "" + e.getLocalizedMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
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
