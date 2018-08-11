package com.friendfill.foodifie.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import com.friendfill.foodifie.R;
import com.friendfill.foodifie.adapter.StaffAdapter;
import com.friendfill.foodifie.helper.RecyclerItemTouchHelper;
import com.friendfill.foodifie.model.Staff;
import com.friendfill.foodifie.network.APIClient;
import com.friendfill.foodifie.network.APIInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StaffFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StaffFragment extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.search_item_edittext_searchbar)
    EditText searchItemEdittextSearchbar;
    @BindView(R.id.card_view)
    CardView cardView;
    @BindView(R.id.recyclerView_staff)
    RecyclerView recyclerViewStaff;
    Unbinder unbinder;
    @BindView(R.id.linearlayout_container)
    LinearLayout linearlayoutContainer;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<Staff> staff;
    private StaffAdapter staffAdapter;

    private OnFragmentInteractionListener mListener;

    public StaffFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StaffFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StaffFragment newInstance(String param1, String param2) {
        StaffFragment fragment = new StaffFragment();
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
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.fragment_staff, container, false);
        unbinder = ButterKnife.bind(this, v);
        mListener.SetTitleBarTitle(getResources().getString(R.string.staff_activity_title));
        mListener.SetToolbarBack(this);
        getActivity().invalidateOptionsMenu();
        staff = new ArrayList<>();
        Staff();
        ItemTouchHelper.SimpleCallback simpleCallback_left = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this, 0);
        new ItemTouchHelper(simpleCallback_left).attachToRecyclerView(recyclerViewStaff);
        ItemTouchHelper.SimpleCallback simpleCallback_right = new RecyclerItemTouchHelper(0, ItemTouchHelper.RIGHT, this, 1);
        new ItemTouchHelper(simpleCallback_right).attachToRecyclerView(recyclerViewStaff);
        searchItemEdittextSearchbar.requestFocus();
        searchItemEdittextSearchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String newText = searchItemEdittextSearchbar.getText().toString();
                staffAdapter.getFilter().filter(newText);

            }
        });
        return v;
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

    public void SetView() {
        staffAdapter = new StaffAdapter(getContext(), staff, 0);
        recyclerViewStaff.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewStaff.setAdapter(staffAdapter);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof StaffAdapter.ItemViewHolder) {
            if (direction == ItemTouchHelper.LEFT) {
                String phone = staff.get(viewHolder.getAdapterPosition()).getPhone();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
            } else if (direction == ItemTouchHelper.RIGHT) {
                String email = staff.get(viewHolder.getAdapterPosition()).getEmail();
                String name = staff.get(viewHolder.getAdapterPosition()).getName();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:" + email + "?subject=" + getResources().getString(R.string.app_name) + "&body=Hello " + name);
                intent.setData(data);
                startActivity(intent);

            }

            staffAdapter.notifyDataSetChanged();
        }
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

    private void Staff() {
        try {
            ApplicationConfig.setProgressDialog(getActivity(), "Fetching..", "Please wait");
            ApplicationConfig.progressDialog.show();
            APIInterface request = APIClient.getClient().create(APIInterface.class);
            Call<ResponseBody> call = request.Staff("application/json");

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ApplicationConfig.progressDialog.dismiss();
                    try {
                        String json_response = response.body().string();
                        JSONArray json = new JSONArray(json_response);
                        if (json.length() > 0) {

                            for (int i = 0; i < json.length(); i++) {
                                JSONObject jStaff = json.getJSONObject(i);
                                int id = jStaff.getInt("id");
                                String name = jStaff.getString("name");
                                String designation = jStaff.getString("designation");
                                String shift = jStaff.getString("shift");
                                String email = jStaff.getString("email");
                                String phone = jStaff.getString("phone");
                                String image = jStaff.getString("image");
                                Staff staffObj = new Staff(id, name, designation, shift, email, phone, false, image);
                                staff.add(staffObj);
                            }
                            SetView();
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
