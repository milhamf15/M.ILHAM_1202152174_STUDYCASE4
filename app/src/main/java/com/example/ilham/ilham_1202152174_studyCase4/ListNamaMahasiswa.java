package com.example.ilham.ilham_1202152174_studyCase4;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListNamaMahasiswa extends AppCompatActivity {
    ListView listMhs; //mendeklarasikan variabel yang dibutuhkan

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_nama_mahasiswa);
        setTitle("AsyncTask"); //set title pada tampilan layar
        listMhs = (ListView) findViewById(R.id.listview); //memanggil atribut yang ada di layout
    }
    //method saat button ditekan
    public void mulai(View view) {
        new getData(listMhs).execute(); //proses asynctask dimulai
    }
    //subclass assynctask
    class getData extends AsyncTask<String, Integer, String> {
        ListView listMhs;
        ArrayAdapter adapter;
        ArrayList<String> listNama;
        ProgressDialog dialog;

        //constructor saat asynctask diinisialisasi
        public getData(ListView listMhs) {
            this.listMhs = listMhs;
            dialog = new ProgressDialog(ListNamaMahasiswa.this);
            listNama = new ArrayList<>();
        }

        //method ketika proses asynctask belum dimulai
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //menampilkan proses dialog
            dialog.setTitle("Loading Data");
            dialog.setIndeterminate(true);
            dialog.setProgress(0);
            dialog.setMax(100);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(true);
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel Process", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialog.dismiss();
                    getData.this.cancel(true);
                }
            });

            dialog.show();
        }

        //method saat proses asynctask dijalankan
        @Override
        protected String doInBackground(String... strings) {
            adapter = new ArrayAdapter<>(ListNamaMahasiswa.this, android.R.layout.simple_list_item_1, listNama); //membuat adapter

            //menyimpan array pada sebuah variabel
            String[] mhs = getResources().getStringArray(R.array.namaMhs);
            //perulangan untuk menyimpan array
            for (int a = 0; a < mhs.length; a++) {
                final long persen = 100L * a / mhs.length;
                final String nama = mhs[a];
                try {
                    Runnable change = new Runnable() {
                        @Override
                        public void run() {
                            dialog.setMessage((int) persen+"% - Adding "+nama);
                        }
                    };
                    runOnUiThread(change);
                    Thread.sleep(300);
                    listNama.add(mhs[a]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        //method sesudah asynctask sudah dijalankan
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            listMhs.setAdapter(adapter);
            dialog.dismiss();
        }
    }
}
