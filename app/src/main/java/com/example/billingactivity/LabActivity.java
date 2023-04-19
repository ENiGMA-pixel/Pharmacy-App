package com.example.billingactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LabActivity extends AppCompatActivity {

    private String[][] packages
            = {
                    {"Package 1: Full Body Checkup","","","","1999"},
                    {"Package 2: Blood Glucose Fasting","","","","299"},
                    {"Package 3: COVID-19 Antibody IgG","","","","699"},
                    {"Package 4: Thyroid Check","","","","799"},
                    {"Package 5: Immunity Check", "499","","","","699"},
            };
    private String[] packagedetails
            = {

            "Blood Glucose Fasting\n" +
                    "Complete Hemogram\n" +
                    "HbA1c\n" +
                    "Iron Studies\n" +
                    "Kidney Function Test\n" +
                    "LOH Lactate Dehydrogenase, Serum\n" +
                    "Lipid Profile\n" +
                    "Liver Function Test",
            "Blood Glucose Fasting",
            "COVID-19 Antibody - IgG",
            "Thyroid Profile-Total (T3, T4 & TSH Ultra-sensitive)",
            "Complete Hemogram\n" +
                    "CRP (C Reactive Protein) Quantitative, Serum\n" +
                    "Iron Studies\n" +
                    "Kidney Function Test\n" +
                    "Vitamin D Total-25 Hydroxy\n" +
                    "Liver Function Test\n" +
                    "Lipid Profile"
    };
    HashMap<String,String> item;
    ArrayList list;
    SimpleAdapter sa;
    Button btngotocart,btnback;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab);

        btngotocart = findViewById(R.id.btngotocartLT);
        btnback = findViewById(R.id.btnbackLT);
        listView = findViewById(R.id.ListviewLT);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LabActivity.this,MainActivity.class));
            }
        });

        list = new ArrayList();
        for(int i=0;i<packages.length; i++) {
            item = new HashMap<String, String>();
            item.put("Line1", packages[i][0]);
            item.put("Line2", packages[i][1]);
            item.put("Line3", packages[i][2]);
            item.put("Line4", packages[i][3]);
            item.put("Line5", "Total Cost:"+packages[1][4] + "/-");
            list.add(item);
        }

        sa= new SimpleAdapter(this, list,
                R.layout.multi_line,
                new String[]{"Line1","Line2", "Line3", "Line4", "Lines" },
                new int[] {R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        listView.setAdapter(sa);
    }
}