package com.example.bengkel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class BengkelDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bengkel_detail);

        Intent intent = getIntent();
        String namaBengkel = intent.getStringExtra(BengkelListActivity.NAMA_BENGKEL);
        String alamatBengkel = intent.getStringExtra(BengkelListActivity.ALAMAT_BENGKEL);

        TextView namaPlaceholder = findViewById(R.id.detail_nama_bengkel);
        TextView alamatPlaceholder = findViewById(R.id.detail_alamat_bengkel);

        namaPlaceholder.setText(namaBengkel);
        alamatPlaceholder.setText(alamatBengkel);
    }

    public void handleCallClick(View view) {
        // nomornya Ghoza :D
        String url = "https://api.whatsapp.com/send?phone=6281554085636";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}