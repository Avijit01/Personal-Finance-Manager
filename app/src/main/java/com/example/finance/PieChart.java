package com.example.finance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class PieChart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        //toolbar
        Toolbar toolbarPieChart = findViewById(R.id.toolbarPieChartId);
        setSupportActionBar(toolbarPieChart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
