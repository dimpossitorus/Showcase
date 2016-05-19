package com.dimpossitorus.showcase;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //Log Tag
    private String CONN_TAG = "NETWORK";
    private String DEBUG_TAG = "DEBUG";

    // Network Management
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;

    private String productCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        // Checking if device is connected to network
        // If not, inform the user via toast
        if (networkInfo == null || !(networkInfo.isConnected())) {
            Toast.makeText(getApplicationContext(), "There is no connection", Toast.LENGTH_LONG).show();
        }
        else {
            Log.d(DEBUG_TAG, "There is connection");
        }

        //Button for open camera
        Button scanBarcode = (Button) findViewById(R.id.scan);
        scanBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start the camera intent to scan barcode
            }
        });

        //Edit Text for receiving Product Code input
        final EditText inputCode = (EditText) findViewById(R.id.formCode);


        //Button for submitting Product Code
        Button submitCode = (Button) findViewById(R.id.submit);
        submitCode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Put Product Code on resultIntent
                if (inputCode.getText()!=null) {
                    productCode = inputCode.getText().toString();
                }
                Log.d(DEBUG_TAG, "Product code is "+productCode);
            }


        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    // AsyncTask to download JSON product data
    private class requestData extends AsyncTask<String, Void, String> {
        String product;
        InputStream inputStream;

        // Response instance
        private Response respond;

        @Override
        protected String doInBackground(String... params) {
            product = params[0].toString();
            String address = "https://www.eannovate.com/api/api_intern.php?action=get_product_data&barcode=";
            address += product;
            Log.d(DEBUG_TAG, address);
            String result ="";
            try {
                URL url = new URL(address);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setConnectTimeout(10000);
                connection.connect();
                int response = connection.getResponseCode();
                Log.i(CONN_TAG, String.valueOf(response));
                inputStream = connection.getInputStream();
                result = IOUtils.toString(inputStream,"UTF-8");
            } catch (MalformedURLException e) {
                Log.d(DEBUG_TAG, "Error creating URL");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            JSONObject _response = null;
            try {
                _response = new JSONObject(result);
                respond = new Response(_response);
            } catch (JSONException e) {
                Log.d(DEBUG_TAG, "Error Creating respond");
                e.printStackTrace();
            }
            if (respond!=null && respond.getStatus()==200) {
                Toast.makeText(getApplicationContext(), "Connection Successful. Message : "+respond.getMessage(), Toast.LENGTH_LONG).show();
                Intent showProduct = new Intent(MainActivity.this, ShowProductActivity.class);
                showProduct.putExtra("title",respond.getTitle()); // Change it later, do not forget
                showProduct.putExtra("desc", respond.getDescription());
                showProduct.putExtra("qty", respond.getQty());
                showProduct.putExtra("imgUrl", respond.getImgUrl());
                startActivity(showProduct);
            }
            else {
                Toast.makeText(getApplicationContext(), "Connection Failed.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
