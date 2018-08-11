package com.friendfill.foodifie;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.friendfill.foodifie.adapter.BillItemAdapter;
import com.friendfill.foodifie.adapter.BillReviewItemAdapter;
import com.friendfill.foodifie.adapter.ItemAutoCompleteAdapter;
import com.friendfill.foodifie.model.Bill;
import com.friendfill.foodifie.model.BillStatus;
import com.friendfill.foodifie.model.CartItem;
import com.friendfill.foodifie.model.Customer;
import com.friendfill.foodifie.model.Item;
import com.friendfill.foodifie.model.Table;
import com.friendfill.foodifie.network.APIClient;
import com.friendfill.foodifie.network.APIInterface;
import com.github.clans.fab.FloatingActionMenu;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillDetailActivity extends AppCompatActivity implements BillItemAdapter.AdapterInterface {
    ArrayList<Item> items, all_items;
    BillItemAdapter billItemAdapter;
    BillReviewItemAdapter billReviewItemAdapter;
    Bill bill;
    ItemAutoCompleteAdapter itemAutoCompleteAdapter;
    @BindView(R.id.bill_pending_container)
    LinearLayout bill_pending_container;

    @BindView(R.id.search_item_edittext_searchbar)
    AutoCompleteTextView search_item_edittext_searchbar;

    @BindView(R.id.bill_completed_container)
    LinearLayout bill_completed_container;
    @BindView(R.id.app_bar)
    AppBarLayout app_bar;
    @BindView(R.id.recyclerview_billitem)
    RecyclerView recyclerView;

    @BindView(R.id.recyclerview_pending_bill)
    RecyclerView recyclerview_pending_bill;
    @BindView(R.id.bill_no)
    TextView bill_no;
    @BindView(R.id.bill_customer_name)
    TextView bill_customer_name;
    @BindView(R.id.bill_grand_total)
    TextView bill_grand_total;

    @BindView(R.id.bill_subtotal)
    TextView bill_subtotal;
    @BindView(R.id.bill_discount)
    TextView bill_discount;

    @BindView(R.id.bill_tax)
    TextView bill_tax;


    @BindView(R.id.generate_bill_btn)
    FloatingActionButton generate_bill_btn;


    @BindView(R.id.bill_second_grandtotal)
    TextView bill_second_grandtotal;

    int mode = 0;
    @BindView(R.id.bill_customer_email)
    TextView billCustomerEmail;
    @BindView(R.id.bill_customer_phone)
    TextView billCustomerPhone;
    @BindView(R.id.bill_customer_address)
    TextView billCustomerAddress;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nsv)
    NestedScrollView nsv;
    @BindView(R.id.menu_edit_customer)
    com.github.clans.fab.FloatingActionButton menuEditCustomer;
    @BindView(R.id.menu_share_bill)
    com.github.clans.fab.FloatingActionButton menuShareBill;
    @BindView(R.id.menu_print_bill)
    com.github.clans.fab.FloatingActionButton menuPrintBill;
    @BindView(R.id.fab)
    FloatingActionMenu fab;
    @BindView(R.id.coordinateContainer)
    CoordinatorLayout coordinateContainer;
    @BindView(R.id.recyclerview_billreviewitem)
    RecyclerView recyclerviewBillreviewitem;
    @BindView(R.id.bill_review_container)
    LinearLayout billReviewContainer;
    @BindView(R.id.menu_review_bill)
    com.github.clans.fab.FloatingActionButton menuReviewBill;
    @BindView(R.id.selfie_bill_btn)
    FloatingActionButton selfieBillBtn;
    @BindView(R.id.menu_backto_bill)
    com.github.clans.fab.FloatingActionButton menuBacktoBill;
    @BindView(R.id.menu_save_review)
    com.github.clans.fab.FloatingActionButton menuSaveReview;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);
        ButterKnife.bind(this);
        ApplicationConfig.initBills();
        if (getIntent().getExtras() != null) {
            bill = (Bill) getIntent().getExtras().getSerializable("bill");
        }
        Items();
        items = bill.getItems();
        all_items = new ArrayList<>();
        bill_no.setText("#" + bill.getBill_no());
        bill_customer_name.setText("#" + bill.getCustomer().getName());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(bill.getBill_no());


        search_item_edittext_searchbar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ItemAdd(itemAutoCompleteAdapter.getItem(i));
                search_item_edittext_searchbar.setText("");
            }
        });


        generate_bill_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GenerateBill(bill.getId(), "completed");
            }
        });
        menuEditCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BillDetailActivity.this, BookTableActivity.class);
                i.putExtra("bill", bill);
                startActivity(i);
            }
        });
        menuPrintBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                == PackageManager.PERMISSION_GRANTED) {
                            GeneratePDF();

                        } else {

                            ActivityCompat.requestPermissions(BillDetailActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        }
                    } else { //permission is automatically granted on sdk<23 upon installation
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        menuShareBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GenerateBill(bill.getId(), "pending");
            }
        });

        menuReviewBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bill.setStatus(2);
                UpdateUI();
            }
        });
        menuSaveReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveReview(bill.getId(), items);
            }
        });

        menuBacktoBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bill.setStatus(1);
                UpdateUI();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bill();
        UpdateUI();

    }

    public void SetupSearchUI() {
        itemAutoCompleteAdapter = new ItemAutoCompleteAdapter(getApplicationContext(), R.layout.single_item_view_autocomplete, all_items);
        search_item_edittext_searchbar.setAdapter(itemAutoCompleteAdapter);
    }

    public void UpdateUI() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setAutoMeasureEnabled(true);
        items = bill.getItems();
        billCustomerAddress.setText(bill.getCustomer().getAddress());
        billCustomerEmail.setText(bill.getCustomer().getEmail());
        billCustomerPhone.setText(bill.getCustomer().getMobile());
        bill_no.setText(bill.getBill_no());
        bill_customer_name.setText(bill.getCustomer().getName());

        if (bill.getStatus() == 0) {
            generate_bill_btn.setVisibility(View.VISIBLE);
            selfieBillBtn.setVisibility(View.GONE);
            menuShareBill.setVisibility(View.GONE);
            menuPrintBill.setVisibility(View.GONE);
            menuReviewBill.setVisibility(View.GONE);
            menuBacktoBill.setVisibility(View.GONE);
            menuSaveReview.setVisibility(View.GONE);
            app_bar.setBackgroundColor(getResources().getColor(R.color.black_de));
            bill_pending_container.setVisibility(View.VISIBLE);
            bill_completed_container.setVisibility(View.GONE);
            recyclerviewBillreviewitem.setVisibility(View.GONE);
            recyclerview_pending_bill.setLayoutManager(linearLayoutManager);
            recyclerview_pending_bill.hasFixedSize();
            billItemAdapter = new BillItemAdapter(this, bill.getItems(), 1);
            recyclerview_pending_bill.setAdapter(billItemAdapter);
            recyclerview_pending_bill.setLayoutManager(linearLayoutManager);
        } else if (bill.getStatus() == 1) {
            menuShareBill.setVisibility(View.VISIBLE);
            menuPrintBill.setVisibility(View.VISIBLE);
            menuReviewBill.setVisibility(View.VISIBLE);
            generate_bill_btn.setVisibility(View.GONE);
            menuBacktoBill.setVisibility(View.GONE);
            menuSaveReview.setVisibility(View.GONE);
            selfieBillBtn.setVisibility(View.VISIBLE);
            app_bar.setBackgroundColor(getResources().getColor(R.color.color_available));
            bill_pending_container.setVisibility(View.GONE);
            bill_completed_container.setVisibility(View.VISIBLE);
            recyclerviewBillreviewitem.setVisibility(View.GONE);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.hasFixedSize();
            billItemAdapter = new BillItemAdapter(this, bill.getItems(), 0);
            recyclerView.setAdapter(billItemAdapter);
            recyclerView.setLayoutManager(linearLayoutManager);
            bill_grand_total.setText("Rs." + bill.getGrandtotal());
            bill_second_grandtotal.setText("Rs." + bill.getGrandtotal());
            bill_subtotal.setText("Rs." + bill.getSubtotal());
            bill_discount.setText("Rs." + bill.getDiscount());
            bill_tax.setText("Rs." + bill.getTax());
        } else if (bill.getStatus() == 2) {
            menuShareBill.setVisibility(View.GONE);
            menuPrintBill.setVisibility(View.GONE);
            menuReviewBill.setVisibility(View.GONE);
            generate_bill_btn.setVisibility(View.GONE);
            selfieBillBtn.setVisibility(View.VISIBLE);
            menuBacktoBill.setVisibility(View.VISIBLE);
            menuSaveReview.setVisibility(View.VISIBLE);
            app_bar.setBackgroundColor(getResources().getColor(R.color.color_available));
            bill_pending_container.setVisibility(View.GONE);
            bill_completed_container.setVisibility(View.GONE);
            recyclerviewBillreviewitem.setVisibility(View.VISIBLE);
            recyclerviewBillreviewitem.setLayoutManager(linearLayoutManager);
            recyclerviewBillreviewitem.hasFixedSize();
            billReviewItemAdapter = new BillReviewItemAdapter(this, bill.getItems(), 1);
            recyclerviewBillreviewitem.setAdapter(billReviewItemAdapter);
            recyclerviewBillreviewitem.setLayoutManager(linearLayoutManager);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return true;
    }

    public void AddItemToBill(Item item) {
        if (items.contains(item)) {
            int position = items.indexOf(item);
            Item old = items.get(position);
            old.setQty(old.getQty() + 1);
            items.set(position, old);
            billItemAdapter.notifyDataSetChanged();
        } else {
            item.setQty(1);
            items.add(item);
        }
    }

    public void UpdateItemToBill(Item item) {
        if (items.contains(item)) {
            int position = items.indexOf(item);
            if (item.getQty() == 0)
                items.remove(position);
            billItemAdapter.notifyDataSetChanged();
        } else {
            item.setQty(1);
            items.add(item);
        }
    }

    private void SaveReview(int bill_id, ArrayList<Item> items) {
        ApplicationConfig.setProgressDialog(BillDetailActivity.this, "Submitting...", "Please wait..");
        ApplicationConfig.progressDialog.show();
        try {
            APIInterface request = APIClient.getClient().create(APIInterface.class);
            Call<ResponseBody> call = request.SaveReview("application/json", items);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ApplicationConfig.progressDialog.dismiss();
                    try {
                        String json_response = response.body().string();
                        JSONObject json = new JSONObject(json_response);
                        Snackbar.make(coordinateContainer, Html.fromHtml(json.getString("message")), BaseTransientBottomBar.LENGTH_LONG).show();
                        if (json.getInt("status") == 1) {
                            bill.setStatus(1);
                            UpdateUI();
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

    private void GenerateBill(int bill_id, final String status) {
        ApplicationConfig.setProgressDialog(BillDetailActivity.this, "Updating..", "Please wait!!");
        ApplicationConfig.progressDialog.show();
        try {
            APIInterface request = APIClient.getClient().create(APIInterface.class);
            BillStatus billStatus = new BillStatus(bill_id, status);
            Call<ResponseBody> call = request.ChangeBillStatus("application/json", billStatus);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ApplicationConfig.progressDialog.dismiss();
                    try {
                        String json_response = response.body().string();
                        JSONObject json = new JSONObject(json_response);
                        Snackbar.make(coordinateContainer, json.getString("message"), BaseTransientBottomBar.LENGTH_LONG).show();
                        if (json.getInt("status") == 1) {

                            if (status.equals("completed")) {
                                bill.setStatus(1);
                                double subtotal = 0, tax = 0, discount = 0, grandtotal = 0;
                                for (int i = 0; i < items.size(); i++) {
                                    subtotal += (items.get(i).getPrice() * items.get(i).getQty());
                                }
                                bill.setSubtotal(subtotal);
                                tax = (subtotal * 18 / 100);
                                grandtotal = subtotal + tax - discount;
                                bill.setTax(tax);
                                bill.setGrandtotal(grandtotal);
                            } else {
                                bill.setStatus(0);
                            }

                            UpdateUI();
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

    public void GeneratePDF() throws IOException {
        // Assume block needs to be inside a Try/Catch block.
        /*File pdfDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), getResources().getString(R.string.app_name));
        if (!pdfDir.exists()) {
            pdfDir.mkdir();
        }
        OutputStream fOut = null;
        Integer counter = 0;
        File file = new File(pdfDir, getResources().getString(R.string.app_name)+".jpg");
        fOut = new FileOutputStream(file);

        Layout_to_Image layout_to_image=new Layout_to_Image(BillDetailActivity.this,bill_completed_container);
        Bitmap pictureBitmap = layout_to_image.convert_layout();
        // obtaining the Bitmap
        pictureBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
        fOut.flush(); // Not really required
        fOut.close(); // do not forget to close the stream

        MediaStore.Images.Media.insertImage(getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());*/


       /* //Assuming your rootView is called mRootView like so
        View mRootView = bill_completed_container;
        String bill_name = bill.getBill_no() + ".pdf";
//First Check if the external storage is writable
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            Toast.makeText(getApplicationContext(), "Not Mounted", Toast.LENGTH_LONG).show();
        }

//Create a directory for your PDF
        File pdfDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), getResources().getString(R.string.app_name));
        if (!pdfDir.exists()) {
            pdfDir.mkdir();
        }

//Then take the screen shot
        Bitmap screen;
        View v1 = mRootView.getRootView();
        v1.setDrawingCacheEnabled(true);
        screen = Bitmap.createBitmap(v1.getDrawingCache());
        v1.setDrawingCacheEnabled(false);

//Now create the name of your PDF file that you will generate
        File pdfFile = new File(pdfDir, bill_name);
        FileOpen.openFile(getApplicationContext(), pdfFile);*/
    }

    public void SetUpView() {

    }

    private void Bill() {
        try {
            ApplicationConfig.setProgressDialog(BillDetailActivity.this, "Fetching..", "Please wait");
            ApplicationConfig.progressDialog.show();
            APIInterface request = APIClient.getClient().create(APIInterface.class);
            Call<ResponseBody> call = request.BillDetail("application/json", "" + bill.getId());

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ApplicationConfig.progressDialog.dismiss();
                    try {
                        String json_response = response.body().string();
                        JSONObject jBill = new JSONObject(json_response);
                        if (jBill != null) {
                            int id = Integer.parseInt(jBill.getString("id"));
                            String bill_no = jBill.getString("bill_no");
                            Date bill_date = ApplicationConfig.StringToDate(jBill.getString("bill_date"));
                            double grandtotal = Double.parseDouble(jBill.getString("grandtotal"));

                            JSONObject jTable = jBill.getJSONObject("table");
                            int table_id = Integer.parseInt(jTable.getString("id"));
                            String name = jTable.getString("name");
                            String description = jTable.getString("description");
                            String status = jTable.getString("status");
                            int table_status_slug = (status.equals("occupied")) ? 1 : 0;
                            int capacity = Integer.parseInt(jTable.getString("capacity"));
                            int adjustment = Integer.parseInt(jTable.getString("adjustment"));
                            Table table = new Table(table_id, name, description, capacity, adjustment, table_status_slug);


                            JSONObject jCustomer = jBill.getJSONObject("customer");
                            int customer_id = Integer.parseInt(jCustomer.getString("id"));
                            String customer_name = jCustomer.getString("name");
                            String customer_email = jCustomer.getString("email");
                            String customer_phone = jCustomer.getString("phone");
                            String customer_address = jCustomer.getString("address");
                            Date customer_birthdate = ApplicationConfig.StringToDate(jCustomer.getString("birthdate"));
                            Customer customer = new Customer(customer_id, customer_name, customer_phone, customer_email, customer_address, customer_birthdate);


                            bill = new Bill(id, bill_no, bill_date, customer, table, grandtotal, new ArrayList<Item>());
                            int status_slug = (jBill.getString("status").equals("completed")) ? 1 : 0;
                            bill.setStatus(status_slug);

                            JSONArray data = jBill.getJSONArray("items");
                            if (data.length() != 0) {
                                ArrayList<Item> items = new ArrayList<>();
                                for (int j = 0; j < data.length(); j++) {
                                    JSONObject jItem = data.getJSONObject(j);
                                    JSONObject jItemDetail = jItem.getJSONObject("detail");
                                    int item_id = Integer.parseInt(jItem.getString("item_id"));
                                    String item_name = jItemDetail.getString("name");
                                    String item_description = jItemDetail.getString("description");
                                    String category = jItemDetail.getString("category");
                                    String serving = jItemDetail.getString("serving");
                                    String image = jItemDetail.getString("image");
                                    double rating = Double.parseDouble(jItemDetail.getString("rating"));
                                    double price = Double.parseDouble(jItem.getString("price"));
                                    double qty = Double.parseDouble(jItem.getString("qty"));
                                    Item item = new Item(item_id, item_name, item_description, category, serving, image, rating, price);
                                    item.setBill_id(id);
                                    item.setCustomer_id(customer_id);
                                    item.setQty(qty);
                                    items.add(item);
                                }
                                bill.setItems(items);

                            }


                            UpdateUI();
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

    private void Items() {
        try {
            APIInterface request = APIClient.getClient().create(APIInterface.class);
            Call<ResponseBody> call = request.SearchItem("application/json", "a", "1", "100");

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
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
                                    all_items.add(item);
                                }
                            }

                            SetupSearchUI();
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
                    Snackbar.make(coordinateContainer, "" + t.getLocalizedMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Snackbar.make(coordinateContainer, "" + e.getLocalizedMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
        }
    }

    private void ItemAdd(final Item item) {
        try {
            APIInterface request = APIClient.getClient().create(APIInterface.class);
            CartItem cartItem = new CartItem(bill.getId(), item.getId(), 1);
            Call<ResponseBody> call = request.AddItemToBill("application/json", cartItem);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String json_response = response.body().string();
                        JSONObject json = new JSONObject(json_response);
                        Snackbar.make(coordinateContainer, json.getString("message"), BaseTransientBottomBar.LENGTH_LONG).show();
                        if (json.getInt("status") == 1)
                            AddItemToBill(item);


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

    private void ItemUpdate(final Item item) {
        try {
            APIInterface request = APIClient.getClient().create(APIInterface.class);
            CartItem cartItem = new CartItem(bill.getId(), item.getId(), item.getQty());
            Call<ResponseBody> call = request.UpdateItemToBill("application/json", cartItem);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String json_response = response.body().string();
                        JSONObject json = new JSONObject(json_response);
                        Snackbar.make(coordinateContainer, json.getString("message"), BaseTransientBottomBar.LENGTH_LONG).show();
                        if (json.getInt("status") == 1)
                            UpdateItemToBill(item);


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

    @Override
    public void RemoveItem(Item item) {
        item.setQty(0);
        ItemUpdate(item);
    }

    @Override
    public void UpdateItem(Item item) {
        ItemUpdate(item);
    }
}
