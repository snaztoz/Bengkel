package com.example.bengkel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bengkel.bengkellist.BengkelListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BengkelListActivity extends AppCompatActivity {
    public class Data {
        public String nama;
        public String alamat;
        public double jarak;
    }

    public static final String NAMA_BENGKEL = "com.example.bengkel.NAMA_BENGKEL";
    public static final String ALAMAT_BENGKEL = "com.example.bengkel.ALAMAT_BENGKEL";

    private GridView bengkelListContainer;
    private int image = R.drawable.navbar_button_history;
    private List<Data> dataList = new ArrayList<Data>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bengkel_list);

        Intent intent = getIntent();
        String bengkelType = intent.getStringExtra(HomeActivity.BENGKEL_TYPE);
        double location_lat = intent.getDoubleExtra(HomeActivity.LOCATION_LAT, 0.0);
        double location_long = intent.getDoubleExtra(HomeActivity.LOCATION_LONG, 0.0);

        TextView bengkelListTitle = findViewById(R.id.bengkelListTitle);
        bengkelListTitle.setText("Ready to fix your " + bengkelType);

        final String url =
                String.format("http://10.0.2.2:5001/bengkel-b18a8/us-central1/getNearbyBengkel?lat=%f&long=%f",
                                location_lat, location_long);
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("result");
                            int dataCount = results.length() < 4 ? results.length() : 4;
                            System.out.println("panjang: " + dataCount);
                            for (int i = 0; i < dataCount; i++) {
                                JSONObject entry = results.getJSONObject(i);
                                Data data = new Data();
                                data.nama = entry.getString("nama");
                                data.alamat = entry.getString("alamat");
                                data.jarak = entry.getDouble("distance");
                                dataList.add(data);
                            }
                            initBengkelListContainer();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                        System.out.println("GAGAL MENGAMBIL DATA");
                    }
                });

        queue.add(request);
    }

    private void initBengkelListContainer() {
        System.out.println("constructing");
        BengkelListAdapter adapter =
                new BengkelListAdapter(getApplicationContext(), dataList, image);
        adapter.setTemplateLayoutId(R.layout.item_bengkel_list);
        adapter.setTemplateImageId(R.id.icon);
        adapter.setTemplateNameId(R.id.nama_bengkel);

        bengkelListContainer = findViewById(R.id.bengkel_list_container);
        bengkelListContainer.setAdapter(adapter);

        bengkelListContainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BengkelListActivity.this, BengkelDetailActivity.class);
                intent.putExtra(NAMA_BENGKEL, dataList.get(position).nama);
                intent.putExtra(ALAMAT_BENGKEL, dataList.get(position).alamat);
                startActivity(intent);
            }
        });
    }
}