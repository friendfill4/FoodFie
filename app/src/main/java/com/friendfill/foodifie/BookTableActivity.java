package com.friendfill.foodifie;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.friendfill.foodifie.adapter.BillTableAdapter;
import com.friendfill.foodifie.model.Bill;
import com.friendfill.foodifie.model.Cart;
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

public class BookTableActivity extends AppCompatActivity {
    Bill bill;
    @BindView(R.id.table_customer_name)
    EditText table_customer_name;
    @BindView(R.id.table_customer_address)
    EditText table_customer_address;
    @BindView(R.id.table_customer_phone)
    EditText table_customer_phone;
    @BindView(R.id.table_customer_email)
    EditText table_customer_email;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.table_table)
    Spinner table_table;
    BillTableAdapter billTableAdapter;
    ArrayList<Table> tables;
    @BindView(R.id.book_table_btn)
    TextView book_table_btn;
    @BindView(R.id.book_update_table_btn)
    TextView book_update_table_btn;
    int mode = 1;
    @BindView(R.id.coordinate_container)
    CoordinatorLayout coordinateContainer;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_table);
        ButterKnife.bind(this);
        if (getIntent().getExtras() != null) {
            mode = getIntent().getExtras().getInt("mode");
        }
        if (mode == 0) {
            book_update_table_btn.setVisibility(View.GONE);
            book_table_btn.setVisibility(View.VISIBLE);
        } else {
            book_update_table_btn.setVisibility(View.VISIBLE);
            book_table_btn.setVisibility(View.GONE);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*ApplicationConfig.initBills();*/
        book_update_table_btn.setVisibility(View.GONE);
        book_table_btn.setVisibility(View.VISIBLE);
        if (getIntent().getExtras() != null) {
            bill = (Bill) getIntent().getExtras().getSerializable("bill");
            table_customer_name.setText(bill.getCustomer().getName());
            table_customer_phone.setText(bill.getCustomer().getMobile());
            table_customer_email.setText(bill.getCustomer().getEmail());
            table_customer_address.setText(bill.getCustomer().getAddress());
            book_update_table_btn.setVisibility(View.VISIBLE);
            book_table_btn.setVisibility(View.GONE);
        }
        /*bill = ApplicationConfig.bills.get(1);*/
       /* */
        tables = new ArrayList<>();
        Table();
        /*int pos = tables.indexOf(bill.getTable());
        table_table.setSelection(pos);
        */
        book_table_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetCustomerDetail();

            }
        });
        book_update_table_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateBill();
            }
        });
    }

    private void UpdateBill() {
        String name = table_customer_name.getText().toString();
        String address = table_customer_name.getText().toString();
        String mobile = table_customer_phone.getText().toString();
        String email = table_customer_email.getText().toString();
        Table selected_table = tables.get(table_table.getSelectedItemPosition());
        Table table = selected_table;
        UpdateBill(name, email, mobile, address, "" + table.getId(), "" + bill.getId());
    }

    private void SetCustomerDetail() {
        String name = table_customer_name.getText().toString();
        String address = table_customer_name.getText().toString();
        String mobile = table_customer_phone.getText().toString();
        String email = table_customer_email.getText().toString();
        Table selected_table = tables.get(table_table.getSelectedItemPosition());
        Table table = selected_table;
       /* bill.setCustomer(customer);
        bill.setTable(table);*/
        CreateBill(name, email, mobile, address, "" + table.getId());


    }

    private void CreateBill(String name, String email, String mobile, String address, String table) {
        try {
            APIInterface request = APIClient.getClient().create(APIInterface.class);
            Cart cart = new Cart(name, email, mobile, address, "01-01-2017", table);
            Call<ResponseBody> call = request.AddItemToBill("application/json", cart);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String json_response = response.body().string();
                        JSONObject json = new JSONObject(json_response);
                        Snackbar.make(coordinateContainer, json.getString("message"), BaseTransientBottomBar.LENGTH_LONG).show();
                        if (json.getInt("status") == 1) finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Snackbar.make(coordinateContainer, "" + e.getLocalizedMessage(), BaseTransientBottomBar.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Snackbar.make(coordinateContainer, "" + t.getLocalizedMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Snackbar.make(coordinateContainer, "" + e.getLocalizedMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
        }
    }


    private void UpdateBill(String name, String email, String mobile, String address, String table, String bill_id) {
        try {
            APIInterface request = APIClient.getClient().create(APIInterface.class);
            Cart cart = new Cart(name, email, mobile, address, "01-01-2017", table, bill_id);
            Call<ResponseBody> call = request.UpdateBill("application/json", cart);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String json_response = response.body().string();
                        JSONObject json = new JSONObject(json_response);
                        Snackbar.make(coordinateContainer, json.getString("message"), BaseTransientBottomBar.LENGTH_LONG).show();
                        if (json.getInt("status") == 1) finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Snackbar.make(coordinateContainer, "" + e.getLocalizedMessage(), BaseTransientBottomBar.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Snackbar.make(coordinateContainer, "" + t.getLocalizedMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Snackbar.make(coordinateContainer, "" + e.getLocalizedMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
        }
    }


    private void Table() {
        try {
            ApplicationConfig.setProgressDialog(BookTableActivity.this, "Fetching..", "Please wait");
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

    public void SetUpView() {
        billTableAdapter = new BillTableAdapter(this, R.layout.single_table_select, tables);
        table_table.setAdapter(billTableAdapter);

    }
}
