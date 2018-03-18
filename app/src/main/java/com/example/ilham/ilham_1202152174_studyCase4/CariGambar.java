package com.example.ilham.ilham_1202152174_studyCase4;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class CariGambar extends AppCompatActivity {

    //Deklarasi
    private EditText txtImageURL;
    private Button btnImageLoad;
    private ImageView lblImage;
    private ProgressDialog loading;
    //jika gambar sudah di load
    private int ImageIsLoaded=0;

    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.activity_carigambar);

        setTitle("Cari Gambar");

        //Inisialisasi Variabel
        txtImageURL=(EditText)findViewById(R.id.txtImgURL);
        btnImageLoad=(Button)findViewById(R.id.btnImgLoad);
        lblImage=(ImageView)findViewById(R.id.lblImg);
        //Aksi Klik pada Tombol
        btnImageLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Memanggil Method yang dapat mengupdate
                loadImage();
            }
        });

        //Cek apakah bundle kosong
        if(saved!=null){
            //Cek apakah didalam bundle nilai kunci sebagai penunjuk data sudah di load sebelumnya
            if(saved.getInt("IMAGE_IS_LOADED")!=0 && !saved.getString("EXTRA_TEXT_URL").isEmpty()){
                txtImageURL.setText(""+saved.getString("EXTRA_TEXT_URL"));
                loadImage();
            }
        }
    }


    //Method yang berguna untuk melakukan penyimpanan sesuatu pada package
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Simpan untuk URL TEXT agar input tidak dilakukan kembali
        outState.putString("EXTRA_TEXT_URL",txtImageURL.getText().toString());
        //Simpan pada savedinstancestate
        outState.putInt("IMAGE_IS_LOADED",ImageIsLoaded);
    }


    //method yang digunakan untuk mengupdate tampilan mengubah gambar dengan menggunakan asynctask url

    private void loadImage(){
        //Mengambil nilai input (String)
        String ImgUrl = txtImageURL.getText().toString();
        //Aksi Asynctask untuk melakukan pencarian/load gambar dari internet
        new LoadImageTask().execute(ImgUrl);
    }



    // Class Asynctask dengan paramater deklarasi:
    // Input: String -> Alamat Gambar, Progress: Integer -> Persentase, ProsesResult: Bitmap -> Gambar
    public class LoadImageTask extends AsyncTask<String, Integer, Bitmap>{

        //Method yang dilakukan sebelum aksi dilakukan
        // Pada aplikasi, tahap ini digunakan untuk memunculkan progress bar beserta propertynya

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading=new ProgressDialog(CariGambar.this);
            loading.setMessage("Waiting ...");
            loading.setMax(100);
            loading.incrementProgressBy(1);
            loading.show();
        }

        //Method ini digunakan untuk mencari gambar dari internet berdasarkan
        // alamat URL gambar valid dengan mengubahnya menjadi bitmap

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(params[0]);
                bitmap = BitmapFactory.decodeStream((InputStream)url.getContent());
            } catch (IOException e) {
                Log.e("doInBackground() - ", e.getMessage());
            }
            return bitmap;
        }

        //Method ini digunakan saat proses pengambilan data berlangsung dan menampilkan progressbar

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            try {
                Thread.sleep(1000);
                loading.setMessage("Fetching...");
                loading.incrementProgressBy(values[0]);
                loading.setProgress(values[0]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            //Set ImageView
            lblImage.setImageBitmap(bitmap);
            //Parameter Sudah di load
            ImageIsLoaded=1;
            //Menghilangkan Loading(ProgressBar)
            loading.dismiss();
        }
    }

}
