package com.dimpossitorus.showcase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button for open camera
        Button scanBarcode = (Button) findViewById(R.id.scan);

        //Edit Text for receiving Product Code input
        EditText inputCode = (EditText) findViewById(R.id.formCode);


        //Button for submitting Product Code
        Button submitCode = (Button) findViewById(R.id.submit);
        submitCode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Put Product Code on resultIntent
                //Intent showProduct = new Intent(MainActivity.this, ShowProduct.class);
                //showProduct.putExtra("code","test"); // Change it later, do not forget
                //startActivity();
            }


        });
    }
}
