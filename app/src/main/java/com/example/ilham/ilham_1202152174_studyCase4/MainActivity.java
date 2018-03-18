package com.example.ilham.ilham_1202152174_studyCase4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button list, cari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Inisialisasi variabel
        list =(Button)findViewById(R.id.btnListMhs);
        cari =(Button)findViewById(R.id.btnCariGbr);
        //method click tombol untuk menuju aktivitas ListNamaMahasiswa
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ListNamaMahasiswa.class));
            }
        });
        //method click tombol untuk menuju aktivitas CariGambar
        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CariGambar.class));
            }
        });
    }

}
