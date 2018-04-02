package com.example.niyati.demoxmljson;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.niyati.demoxmljson.JSON.ParseJsonActivity;
import com.example.niyati.demoxmljson.XML.ParseXMLActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.parse_xml).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ParseXMLActivity.class));
            }
        });
        findViewById(R.id.parse_json).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ParseJsonActivity.class));
            }
        });
    }
}
