package com.friendfill.foodifie;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.friendfill.foodifie.adapter.ItemReviewAdapter;
import com.friendfill.foodifie.adapter.TableAdapter;
import com.friendfill.foodifie.dialog.TableSelectionBottomSheetDialogFragment;
import com.friendfill.foodifie.model.CartItem;
import com.friendfill.foodifie.model.Item;
import com.friendfill.foodifie.model.Review;
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

public class ItemDescriptionActivity extends AppCompatActivity implements TableAdapter.Listener {
    Item item;
    ArrayList<Review> reviews;
    ItemReviewAdapter itemReviewAdapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.item_imageview)
    SimpleDraweeView itemImageview;
    @BindView(R.id.item_description)
    TextView itemDescription;
    @BindView(R.id.item_description_container)
    CoordinatorLayout item_description_container;
    TableSelectionBottomSheetDialogFragment tableSelectionBottomSheetDialogFragment;
    @BindView(R.id.textview_item_category)
    TextView textviewItemCategory;
    @BindView(R.id.textview_item_serving)
    TextView textviewItemServing;
    @BindView(R.id.ratingbar_item)
    RatingBar ratingbarItem;
    @BindView(R.id.item_review_recyclerview)
    RecyclerView itemReviewRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_description);
        ButterKnife.bind(this);
        if (getIntent().getExtras() != null)
            item = (Item) getIntent().getExtras().getSerializable("item");
        Uri uri = Uri.parse(item.getImage());
        itemImageview.setImageURI(uri);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        itemDescription.setText(Html.fromHtml(item.getDescription()));
        toolbar.setTitle(item.getName());
        toolbarLayout.setTitle(item.getName());
        textviewItemServing.setText(item.getServing());
        textviewItemCategory.setText(item.getCategory());
        ratingbarItem.setRating(Float.parseFloat("" + item.getRating()));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tableSelectionBottomSheetDialogFragment = new TableSelectionBottomSheetDialogFragment();
                tableSelectionBottomSheetDialogFragment.show(getSupportFragmentManager(), tableSelectionBottomSheetDialogFragment.getTag());
            }
        });
        GetReview("" + item.getId());

    }

    private void ItemAdd(final Item item, final Table table) {
        try {
            APIInterface request = APIClient.getClient().create(APIInterface.class);
            CartItem cartItem = new CartItem(0, item.getId(), table.getId(), 1);
            Call<ResponseBody> call = request.AddItemToTable("application/json", cartItem);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String json_response = response.body().string();
                        JSONObject json = new JSONObject(json_response);
                        if (json.getInt("status") == 1)
                            tableSelectionBottomSheetDialogFragment.dismiss();

                        Toast.makeText(ItemDescriptionActivity.this, json.getString("message"), Toast.LENGTH_LONG).show();


                    } catch (Exception e) {
                        e.printStackTrace();
                        Snackbar.make(item_description_container, "" + e.getLocalizedMessage(), BaseTransientBottomBar.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Snackbar.make(item_description_container, "" + t.getLocalizedMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Snackbar.make(item_description_container, "" + e.getLocalizedMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
        }
    }

    private void GetReview(final String item_id) {
        try {
            APIInterface request = APIClient.getClient().create(APIInterface.class);
            Call<ResponseBody> call = request.GetReview("application/json", item_id);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String json_response = response.body().string();
                        JSONArray json = new JSONArray(json_response);
                        reviews = new ArrayList<>();
                        for (int i = 0; i < json.length(); i++) {
                            JSONObject cReview = json.getJSONObject(i);
                            int id = cReview.getInt("id");
                            int bill_id = cReview.getInt("bill_id");
                            int customer_id = cReview.getInt("customer_id");
                            int rate = cReview.getInt("rate");
                            String review = cReview.getString("review");
                            String customer_name = cReview.getJSONObject("customer").getString("name");
                            Review reviewObj = new Review(id, bill_id, customer_id, customer_name, rate, review);
                            reviews.add(reviewObj);
                            setReviewView();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Snackbar.make(item_description_container, "" + e.getLocalizedMessage(), BaseTransientBottomBar.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Snackbar.make(item_description_container, "" + t.getLocalizedMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Snackbar.make(item_description_container, "" + e.getLocalizedMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
        }
    }

    public void setReviewView() {
        itemReviewAdapter = new ItemReviewAdapter(getApplicationContext(), reviews, 1);
        itemReviewRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        itemReviewRecyclerview.setAdapter(itemReviewAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public boolean TableSelection(Table table) {
        ItemAdd(item, table);
        return true;
    }

    @Override
    public void onClick(Table table) {
        ItemAdd(item, table);
    }
}
