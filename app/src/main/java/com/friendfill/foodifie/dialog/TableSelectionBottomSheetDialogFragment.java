package com.friendfill.foodifie.dialog;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.friendfill.foodifie.ItemDescriptionActivity;
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
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by FriendFill on 25-Jan-18.
 */

public class TableSelectionBottomSheetDialogFragment extends BottomSheetDialogFragment implements TableAdapter.Listener {
    @BindView(R.id.recyclerview_tables)
    RecyclerView recyclerviewTables;
    Unbinder unbinder;
    ArrayList<Table> tables;

    public TableSelectionBottomSheetDialogFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bottomsheet_table_selection, container, false);
        unbinder = ButterKnife.bind(this, v);
        tables = new ArrayList<>();
        Table();

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(Table table) {
        ((ItemDescriptionActivity) getActivity()).TableSelection(table);
    }


    private void Table() {
        try {
            APIInterface request = APIClient.getClient().create(APIInterface.class);
            Call<ResponseBody> call = request.Table("application/json", "1", "100");

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

                            TableAdapter tableAdapter = new TableAdapter(getContext(), tables);
                            recyclerviewTables.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                            recyclerviewTables.setAdapter(tableAdapter);

                        } else {

                        }


                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
