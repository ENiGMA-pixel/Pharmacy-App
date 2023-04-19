package com.example.pharmacy_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BuydetailActivity extends AppCompatActivity {

    TextView tvpackagename,tvtotalcost;
    EditText edDetails;
    Button btnAddtocart, btnBack;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buydetail);

        tvpackagename = findViewById(R.id.BMDtextview1);
        tvtotalcost= findViewById(R.id.CostBMD);
        edDetails = findViewById(R.id.edmultiline);
        btnAddtocart = findViewById(R.id.btngotocartBMD);
        btnBack = findViewById(R.id.btnbackBMD);

        edDetails.setKeyListener(null);

        Intent intent=getIntent();
        tvpackagename.setText(intent.getStringExtra("text1"));
        edDetails.setText(intent.getStringExtra("text2"));
        tvtotalcost.setText("Total Cost" + intent.getStringExtra("text3") + "/-");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BuydetailActivity.this,BuyActivity.class));
            }
        });

    }
}