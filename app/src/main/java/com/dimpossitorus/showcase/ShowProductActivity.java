package com.dimpossitorus.showcase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ShowProductActivity extends AppCompatActivity {

    //Log Tag
    private String CONN_TAG = "NETWORK";
    private String DEBUG_TAG = "DEBUG";

    //UI Component
    ImageView imageView;
    TextView title;
    TextView qty;
    TextView desc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        setContentView(R.layout.activity_show_product);

        imageView = (ImageView) findViewById(R.id.productImage);
        new downloadImage().execute(intent.getStringExtra("imgUrl"));

        title = (TextView) findViewById(R.id.productTitle);
        title.setText(intent.getStringExtra("title"));

        qty = (TextView) findViewById(R.id.quantity);
        qty.setText(String.valueOf(intent.getIntExtra("qty",0)));


        desc = (TextView) findViewById(R.id.desc);
        desc.setText(intent.getStringExtra("desc"));
    }

    // AsyncTask to download image
    private class downloadImage extends AsyncTask<String, Void, Bitmap> {
        InputStream inputStream;

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap result = null;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setConnectTimeout(10000);
                connection.connect();
                int response = connection.getResponseCode();
                Log.i(CONN_TAG, String.valueOf(response));
                inputStream = connection.getInputStream();
                result = BitmapFactory.decodeStream(inputStream);
            } catch (MalformedURLException e) {
                Log.d(DEBUG_TAG, "Error creating URL");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }

    }
}
