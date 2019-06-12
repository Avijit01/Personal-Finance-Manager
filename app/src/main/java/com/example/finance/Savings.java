package com.example.finance;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class Savings extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {
    private MyDatabaseHelper databaseHelperSavings;
    private TextView TotalSavings;
    private TextView pickdateSavings,DateIndicatorSavings;
    public int Day5,Year5,Month5;
    String MonthstringSavings;
    private Button btnMonthSavings,btnYearSavings,btnOverAllSavings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);

        Toolbar toolbarS = findViewById(R.id.toolbarSavingsId);
        setSupportActionBar(toolbarS);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TotalSavings = (TextView) findViewById(R.id.TotalSavingsId);
        DateIndicatorSavings = (TextView) findViewById(R.id.DateIndicatorSavingsId) ;

        databaseHelperSavings = new MyDatabaseHelper(this);

        pickdateSavings = findViewById(R.id.PickdateSaveId);
        findViewById(R.id.btnPickdateSaveId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerSavings();
            }
        });

        loadDataSavings();

        btnMonthSavings = findViewById(R.id.btnMonthSaveId);
        btnMonthSavings.setOnClickListener(this);

        btnYearSavings = findViewById(R.id.btnYearSaveId);
        btnYearSavings.setOnClickListener(this);

        btnOverAllSavings = findViewById(R.id.btnOverAllSaveId);
        btnOverAllSavings.setOnClickListener(this);
    }



    public void loadDataSavings() {

        Cursor cursorSavings = databaseHelperSavings.TotalSavings();

        if (cursorSavings.getCount() == 0) {
            TotalSavings.setText("0");
        }
        else
        {
            while (cursorSavings.moveToNext()){
                if(cursorSavings.getString(0) == null)
                {
                    TotalSavings.setText("0 Tk.");
                }else
                    TotalSavings.setText(cursorSavings.getString(0)+" Tk.");

            }
        }

    }

    private void showDatePickerSavings(){
        DatePickerDialog datePickerDialogSavings = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialogSavings.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        if(month == 0){
            MonthstringSavings = "January";
        }
        if(month == 1){
            MonthstringSavings = "February";
        }
        if(month == 2){
            MonthstringSavings = "March";
        }
        if(month == 3){
            MonthstringSavings = "April";
        }
        if(month == 4){
            MonthstringSavings = "May";
        }
        if(month == 5){
            MonthstringSavings = "June";
        }
        if(month == 6){
            MonthstringSavings = "July";
        }
        if(month == 7){
            MonthstringSavings = "August";
        }
        if(month == 8){
            MonthstringSavings = "September";
        }
        if(month == 9){
            MonthstringSavings = "October";
        }
        if(month == 10){
            MonthstringSavings = "November";
        }
        if(month == 11){
            MonthstringSavings = "December";
        }

        String dateSavings = MonthstringSavings+", "+year;
        pickdateSavings.setText(dateSavings);
        Day5 = dayOfMonth;
        Month5 = month + 1;
        Year5 = year;
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.btnMonthSaveId){

            if(pickdateSavings.getText().equals("")){
                Toast.makeText(getApplicationContext(), "Please pick any date",Toast.LENGTH_LONG).show();
            }

            else {

                DateIndicatorSavings.setText(MonthstringSavings+", "+Year5);

                Cursor cursorTotalMonthSavings = databaseHelperSavings.TotalSavingsMonth(Month5,Year5);

                if (cursorTotalMonthSavings.getCount() == 0) {
                    TotalSavings.setText("0");
                }
                else
                {
                    while (cursorTotalMonthSavings.moveToNext()){
                        if(cursorTotalMonthSavings.getString(0) == null)
                        {
                            TotalSavings.setText("0 Tk.");
                        }else
                            TotalSavings.setText(cursorTotalMonthSavings.getString(0)+" Tk.");

                    }
                }
            }


        }
        if(v.getId() == R.id.btnYearSaveId){

            if(pickdateSavings.getText().equals("")){
                Toast.makeText(getApplicationContext(), "Please pick any date",Toast.LENGTH_LONG).show();
            }

            else {

                DateIndicatorSavings.setText("Year: "+Year5);

                Cursor cursorTotalYear = databaseHelperSavings.TotalSavingsYear(Year5);

                if (cursorTotalYear.getCount() == 0) {
                    TotalSavings.setText("0");
                }
                else
                {
                    while (cursorTotalYear.moveToNext()){
                        if(cursorTotalYear.getString(0) == null)
                        {
                            TotalSavings.setText("0 Tk.");
                        }else
                            TotalSavings.setText(cursorTotalYear.getString(0)+" Tk.");

                    }
                }
            }

        }

        if(v.getId() == R.id.btnOverAllSaveId)
        {
            if(pickdateSavings.getText().equals("")){
                Toast.makeText(getApplicationContext(), "Please pick any date",Toast.LENGTH_LONG).show();
            }

            else {
                DateIndicatorSavings.setText("OverAll");
                loadDataSavings();
            }
        }

    }
}

