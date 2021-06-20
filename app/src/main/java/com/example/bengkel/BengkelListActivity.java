package com.example.bengkel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.bengkel.bengkellist.BengkelListAdapter;

public class BengkelListActivity extends AppCompatActivity {
    private GridView bengkelListContainer;
    private int[] images = {R.drawable.navbar_button_add, R.drawable.navbar_button_add,
                            R.drawable.navbar_button_add, R.drawable.navbar_button_add, };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bengkel_list);

        Intent intent = getIntent();
        String bengkelType = intent.getStringExtra(HomeActivity.BENGKEL_TYPE);

        TextView bengkelListTitle = findViewById(R.id.bengkelListTitle);
        bengkelListTitle.setText("Ready to fix your " + bengkelType);

        initBengkelListContainer();
    }

    private void initBengkelListContainer() {
        BengkelListAdapter adapter = new BengkelListAdapter(getApplicationContext(), images);
        adapter.setTemplateLayoutId(R.layout.item_bengkel_list);
        adapter.setTemplateId(R.id.icon);

        bengkelListContainer = findViewById(R.id.bengkel_list_container);
        bengkelListContainer.setAdapter(adapter);

        bengkelListContainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BengkelListActivity.this, BengkelDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}