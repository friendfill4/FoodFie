package com.friendfill.foodifie.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.friendfill.foodifie.R;
import com.friendfill.foodifie.adapter.OrderItemCategoryAdapter;
import com.friendfill.foodifie.model.Category;
import com.friendfill.foodifie.model.Item;
import com.friendfill.foodifie.model.Varient;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Unbinder unbinder;
    @BindView(R.id.tv_order_id)
    TextView tvOrderId;
    @BindView(R.id.tv_invoice_id)
    TextView tvInvoiceId;
    @BindView(R.id.tv_payment_method)
    TextView tvPaymentMethod;
    @BindView(R.id.tv_order_items)
    TextView tvOrderItems;
    @BindView(R.id.tv_cart_total)
    TextView tvCartTotal;
    @BindView(R.id.tv_pay_by_wallet)
    TextView tvPayByWallet;
    @BindView(R.id.tv_coupon_discount)
    TextView tvCouponDiscount;
    @BindView(R.id.tv_sub_total)
    TextView tvSubTotal;
    @BindView(R.id.tv_delivery_charge)
    TextView tvDeliveryCharge;
    @BindView(R.id.tv_final_total)
    TextView tvFinalTotal;
    @BindView(R.id.tv_order_date)
    TextView tvOrderDate;
    @BindView(R.id.tv_delivery_slot_date)
    TextView tvDeliverySlotDate;
    @BindView(R.id.tv_delivery_slot_time)
    TextView tvDeliverySlotTime;
    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;
    @BindView(R.id.tv_customer_name)
    TextView tvCustomerName;
    @BindView(R.id.tv_customer_address_1)
    TextView tvCustomerAddress1;
    @BindView(R.id.tv_customer_address_2)
    TextView tvCustomerAddress2;
    @BindView(R.id.ll_order_summary)
    LinearLayout llOrderSummary;
    @BindView(R.id.rv_order_items)
    RecyclerView rvOrderItems;
    @BindView(R.id.ll_order_items)
    LinearLayout llOrderItems;
    @BindView(R.id.tv_tab_summary)
    TextView tvTabSummary;
    @BindView(R.id.tv_tab_items)
    TextView tvTabItems;
    OrderItemCategoryAdapter categoryAdapter;
    ArrayList<Item> items;
    ArrayList<Category> categories;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public OrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.setDebug(true);
        unbinder = ButterKnife.bind(this, v);
        mListener.SetTitleBarTitle(getResources().getString(R.string.staff_activity_title));
        mListener.SetToolbarBack(this);
        getActivity().invalidateOptionsMenu();
        tvTabSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvTabSummary.setBackgroundColor(getResources().getColor(R.color.color_available));
                tvTabItems.setBackgroundColor(getResources().getColor(R.color.gray));
                tvTabSummary.setTextColor(getResources().getColor(R.color.white));
                tvTabItems.setTextColor(getResources().getColor(R.color.black_de));
                llOrderItems.setVisibility(View.GONE);
                llOrderSummary.setVisibility(View.VISIBLE);
            }
        });
        tvTabItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvTabItems.setBackgroundColor(getResources().getColor(R.color.color_available));
                tvTabSummary.setBackgroundColor(getResources().getColor(R.color.gray));
                tvTabSummary.setTextColor(getResources().getColor(R.color.black_de));
                tvTabItems.setTextColor(getResources().getColor(R.color.white));
                llOrderSummary.setVisibility(View.GONE);
                llOrderItems.setVisibility(View.VISIBLE);
            }
        });

        categories = new ArrayList<>();
        ArrayList<Item> items = new ArrayList<>();
        ArrayList<Varient> varients = new ArrayList<>();
        varients.add(new Varient(1, 1, "10 Kg", 2.00, 200.00, 400.00));
        varients.add(new Varient(2, 1, "15 Kg", 4.00, 300.00, 400.00));
        varients.add(new Varient(3, 2, "20 Kg", 4.00, 100.00, 400.00));
        varients.add(new Varient(4, 3, "13 Kg", 5.00, 400.00, 400.00));

        items.add(new Item(1, "Yellow Capsicum", "", "Fruits", "", "", 1, 1000, varients));
        items.add(new Item(2, "Bringle", "", "Fruits", "", "", 0, 250, varients));
        items.add(new Item(3, "Lady Finger", "", "Fruits", "", "", 0, 350, varients));
        items.add(new Item(4, "Red Capsicum", "", "Fruits", "", "", 0, 450, varients));
        categories.add(new Category(1, "Fruits & Vegetables", "", items));
        categories.add(new Category(2, "Staples", "", items));
        categories.add(new Category(3, "Other", "", items));
        SetUpView();
        return v;
    }

    private void SetUpView() {
        categoryAdapter = new OrderItemCategoryAdapter(getContext(), categories, 1);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL);
        rvOrderItems.setLayoutManager(staggeredGridLayoutManager);
        rvOrderItems.setAdapter(categoryAdapter);

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
        unbinder.unbind();
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
