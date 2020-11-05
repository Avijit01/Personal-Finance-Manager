package com.example.finance;

import android.database.Cursor;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class PieChart extends AppCompatActivity {

    com.github.mikephil.charting.charts.PieChart pieChart;
    private MyDatabaseHelper databaseHelperpie;
    public String Income,Expense,Savings;
    public int IncomeInt,ExpenseInt,SavingsInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        //toolbar
        Toolbar toolbarPieChart = findViewById(R.id.toolbarPieChartId);
        setSupportActionBar(toolbarPieChart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseHelperpie = new MyDatabaseHelper(this);

        pieChart = (com.github.mikephil.charting.charts.PieChart) findViewById(R.id.piechart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);

        pieChart.setDragDecelerationFrictionCoef(0.99f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(35f);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(45f);

        LoadIncome();
        LoadExpense();
        LoadSavings();

        ArrayList<PieEntry> yValues = new ArrayList<>();

        yValues.add(new PieEntry(IncomeInt,"Income  "));
        yValues.add(new PieEntry(ExpenseInt,"Expense    "));
        yValues.add(new PieEntry(SavingsInt,"Savings"));

        pieChart.animateY(1000, Easing.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(Color.rgb(0,150,30),Color.RED,Color.BLUE);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);

    }

    public void LoadIncome() {
        Cursor cursor1 = databaseHelperpie.TotalIncome();


        while (cursor1.moveToNext()){
            if(cursor1.getString(0) == null)
            {
                Income = "0";
                IncomeInt = Integer.parseInt(Income);
            }else {
                Income = (cursor1.getString(0));
                IncomeInt = Integer.parseInt(Income);
            }

        }
    }

    public void LoadExpense() {
        Cursor cursor1 = databaseHelperpie.TotalExpense();


        while (cursor1.moveToNext()){
            if(cursor1.getString(0) == null)
            {
                Expense = "0";
                ExpenseInt = Integer.parseInt(Expense);
            }else {
                Expense = (cursor1.getString(0));
                ExpenseInt = Integer.parseInt(Expense);
            }

        }
    }

    public void LoadSavings() {
        Cursor cursor1 = databaseHelperpie.TotalSavings();


        while (cursor1.moveToNext()){
            if(cursor1.getString(0) == null)
            {
                Savings = "0";
                SavingsInt = Integer.parseInt(Savings);
            }else {
                Savings = (cursor1.getString(0));
                SavingsInt = Integer.parseInt(Savings);
            }

        }
    }



}
