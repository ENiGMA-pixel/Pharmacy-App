package com.example.pharmacy_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class BuyActivity extends AppCompatActivity {
    private String[][] packages
            = {
            {"Lipitor ","(atorvastatin)","499","",""},
            {"Synthroid ","(levothyroxine)","299","",""},
            {"Metformin","","399","","",""},
            {"Nexium","(esomeprazole)","399","","",""},
            {"Advair Diskus", "(fluticasone and salmeterol)","699","","","",""},
            {"Crestor ", "(rosuvastatin)","599","","","",""},
            {"Zoloft ", "(sertraline)","699","","","",""},
            {"Xarelto  ", "(rivaroxaban)","999","","","",""},
            {"Lisinopril", "","899","","","",""},
            {"Plavix ", "(clopidogrel)","299","","","",""},
    };
    private String[] packagedetails
            = {

            "Lipitor\n" +
                    "used to lower cholesterol and reduce the risk of heart attack and stroke.\n",
            "Synthroid\n" +
                    "used to treat an underactive thyroid gland.",
            "Metformin\n" +
                    "used to treat type 2 diabetes.",
            "Nexium\n" +
                    "used to treat gastroesophageal reflux disease (GERD) and other digestive issues.",
            "Advair Diskus\n" +
                    "used to treat asthma and chronic obstructive pulmonary disease (COPD).",
            "Crestor\n" +
                    "used to lower cholesterol and reduce the risk of heart attack and stroke.",
            "Zoloft \n" +
                    "used to lower cholesterol and reduce the risk of heart attack and stroke.",
            "Xarelto\n" +
                    "used to lower cholesterol and reduce the risk of heart attack and stroke.",
            "Lisinopril\n" +
                    "used to lower cholesterol and reduce the risk of heart attack and stroke.",
            "Plavix\n" +
                    "used to lower cholesterol and reduce the risk of heart attack and stroke.",
    };
    HashMap<String,String> item;
    ArrayList list;
    SimpleAdapter sa;
    Button btngotocart,btnback;
    ListView listView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        btngotocart = findViewById(R.id.btngotocartBM);
        btnback = findViewById(R.id.btnbackBM);
        listView = findViewById(R.id.ListviewBM);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BuyActivity.this,MainActivity.class));
            }
        });

        list = new ArrayList();
        for(int i=0;i<packages.length; i++) {
            item = new HashMap<String, String>();
            item.put("Line1", packages[i][0]);
            item.put("Line2", packages[i][1]);
            item.put("Line5", packages[i][4]);
            item.put("Line4", packages[i][3]);
            item.put("Line3", "Total Cost:"+packages[i][2]+ "/-");
            list.add(item);
        }

        sa= new SimpleAdapter(this, list,
                R.layout.multi_line,
                new String[]{"Line1","Line2", "Line3", "Line4", "Lines" },
                new int[] {R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        listView.setAdapter(sa);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(BuyActivity.this, BuydetailActivity.class);
                it.putExtra("text1",packages [i][0]);
                it.putExtra("text2",packagedetails[i]);
                it.putExtra("text3",packages[i][2]);
                startActivity(it);
            }
        });
    }
}