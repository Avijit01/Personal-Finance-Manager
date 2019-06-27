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

public class income extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {
    private Button buttonaddincome, buttonEditIncome;
    private ListView listView;
    private MyDatabaseHelper databaseHelper;
    private TextView TotalIncome,DateIndicatorIncome;
    private TextView pickdate;
    public int Day2,Year2,Month2;
    public String Monthstring,date2;
    private Button btnDay,btnMonth,btnYear;

    //Edit


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TotalIncome = (TextView) findViewById(R.id.TotalIncomeId);
        DateIndicatorIncome = (TextView) findViewById(R.id.DateIndicatorIncomeId);

        buttonaddincome = (Button) findViewById(R.id.add1);
        buttonaddincome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddIncome();
            }
        });

        buttonEditIncome = (Button) findViewById(R.id.EditId);
        buttonEditIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditIncome();
            }
        });



        listView = (ListView) findViewById(R.id.listViewId1);
        databaseHelper = new MyDatabaseHelper(this);

        pickdate = findViewById(R.id.PickdateId);
        findViewById(R.id.btnPickdateId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        loadData();


        btnDay = findViewById(R.id.btnDayId);
        btnDay.setOnClickListener(this);

        btnMonth = findViewById(R.id.btnMonthId);
        btnMonth.setOnClickListener(this);

        btnYear = findViewById(R.id.btnYearId);
        btnYear.setOnClickListener(this);
    }



    public void openAddIncome(){
        Intent intent = new Intent(this, AddIncome.class);
        startActivity(intent);
    }

    public void openEditIncome(){
        Intent intent = new Intent(this, EditIncome.class);
        startActivity(intent);
    }


    public void loadData() {

        ArrayList<String> listData = new ArrayList<>();


        Cursor cursor = databaseHelper.showAllData();

        if (cursor.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "No data is available in database",Toast.LENGTH_LONG).show();
        }
        else
            {

            while (cursor.moveToNext()){
                listData.add("Date:            "+cursor.getString(0)+"\n"+"Category:    "+cursor.getString(1)+"\n"+"Amount:      "+cursor.getString(2)+" Tk."+"\n"+"Entry:           "+cursor.getString(3));
            }
        }

        Cursor cursor1 = databaseHelper.TotalIncome();

        if (cursor1.getCount() == 0) {
            TotalIncome.setText("0");
        }
        else
        {
            while (cursor1.moveToNext()){
                if(cursor1.getString(0) == null)
                {
                    TotalIncome.setText("0 Tk.");
                }else
                TotalIncome.setText(cursor1.getString(0)+" Tk.");

            }
        }



        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.textViewId2,listData);
        listView.setAdapter(adapter);

        //optional
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String value = adapter.getItem(position).toString();
                //Toast.makeText(getApplicationContext(),value,Toast.LENGTH_LONG).show();
            }
        });


    }

    private void showDatePicker(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        if(month == 0){
            Monthstring = "January";
        }
        if(month == 1){
            Monthstring = "February";
        }
        if(month == 2){
            Monthstring = "March";
        }
        if(month == 3){
            Monthstring = "April";
        }
        if(month == 4){
            Monthstring = "May";
        }
        if(month == 5){
            Monthstring = "June";
        }
        if(month == 6){
            Monthstring = "July";
        }
        if(month == 7){
            Monthstring = "August";
        }
        if(month == 8){
            Monthstring = "September";
        }
        if(month == 9){
            Monthstring = "October";
        }
        if(month == 10){
            Monthstring = "November";
        }
        if(month == 11){
            Monthstring = "December";
        }

        date2 = dayOfMonth+" "+Monthstring+", "+year;
        pickdate.setText(date2);
        Day2 = dayOfMonth;
        Month2 = month + 1;
        Year2 = year;
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.btnDayId){

            if(pickdate.getText().equals("")){
                Toast.makeText(getApplicationContext(), "Please pick any date",Toast.LENGTH_LONG).show();
            }

            else{

                DateIndicatorIncome.setText(date2);

                ArrayList<String> listDataDay = new ArrayList<>();

                Cursor cursorday = databaseHelper.showDayData(Day2, Month2,Year2);

                if (cursorday.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "No data is available in database",Toast.LENGTH_LONG).show();
                }
                else
                {

                    while (cursorday.moveToNext()){
                        listDataDay.add("Date: "+cursorday.getString(0)+"\n"+"Category: "+cursorday.getString(1)+"\n"+"Amount: "+cursorday.getString(2)+" Tk."+"\n"+"Entry: "+cursorday.getString(3)+" Tk.");

                    }
                }

                final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,R.layout.list_item,R.id.textViewId2,listDataDay);
                listView.setAdapter(adapter1);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String value = adapter1.getItem(position).toString();
                        Toast.makeText(getApplicationContext(),value,Toast.LENGTH_LONG).show();
                    }
                });

                Cursor cursortotalday = databaseHelper.TotalIncomeday(Day2,Month2,Year2);

                if (cursortotalday.getCount() == 0) {
                    TotalIncome.setText("0");
                }
                else
                {
                    while (cursortotalday.moveToNext()){
                        if(cursortotalday.getString(0) == null)
                        {
                            TotalIncome.setText("0 Tk.");
                        }else
                            TotalIncome.setText(cursortotalday.getString(0)+" Tk.");

                    }
                }
            }


        }

        if(v.getId() == R.id.btnMonthId){

            if(pickdate.getText().equals("")){
                Toast.makeText(getApplicationContext(), "Please pick any date",Toast.LENGTH_LONG).show();
            }

            else {

                DateIndicatorIncome.setText(Monthstring+", "+Year2);

                //Toast.makeText(getApplicationContext(),Month2+", "+Year2,Toast.LENGTH_LONG).show();
                ArrayList<String> listDataMonth = new ArrayList<>();

                Cursor cursormonth = databaseHelper.showMonthData(Month2,Year2);

                if (cursormonth.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "No data is available in database",Toast.LENGTH_LONG).show();
                }
                else {

                    while (cursormonth.moveToNext()){
                        listDataMonth.add("Date: "+cursormonth.getString(0)+"\n"+"Category: "+cursormonth.getString(1)+"\n"+"Amount: "+cursormonth.getString(2)+" Tk."+"\n"+"Entry: "+cursormonth.getString(3));

                    }
                }

                final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,R.layout.list_item,R.id.textViewId2,listDataMonth);
                listView.setAdapter(adapter2);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String value = adapter2.getItem(position).toString();
                        Toast.makeText(getApplicationContext(),value,Toast.LENGTH_LONG).show();
                    }
                });

                Cursor cursortotalmonth = databaseHelper.TotalIncomeMonth(Month2,Year2);

                if (cursortotalmonth.getCount() == 0) {
                    TotalIncome.setText("0");
                }
                else
                {
                    while (cursortotalmonth.moveToNext()){
                        if(cursortotalmonth.getString(0) == null)
                        {
                            TotalIncome.setText("0 Tk.");
                        }else
                            TotalIncome.setText(cursortotalmonth.getString(0)+" Tk.");

                    }
                }
            }


        }
        if(v.getId() == R.id.btnYearId){

            if(pickdate.getText().equals("")){
                Toast.makeText(getApplicationContext(), "Please pick any date",Toast.LENGTH_LONG).show();
            }

            else {

                DateIndicatorIncome.setText("Year: "+Year2);

                ArrayList<String> listDataYear = new ArrayList<>();

                Cursor cursoryear = databaseHelper.showYearData(Year2);

                if (cursoryear.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "No data is available in database",Toast.LENGTH_LONG).show();
                }
                else
                {

                    while (cursoryear.moveToNext()){
                        listDataYear.add("Date: "+cursoryear.getString(0)+"\n"+"Category: "+cursoryear.getString(1)+"\n"+"Amount: "+cursoryear.getString(2)+" Tk."+"\n"+"Entry: "+cursoryear.getString(3));

                    }
                }

                final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,R.layout.list_item,R.id.textViewId2,listDataYear);
                listView.setAdapter(adapter3);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String value3 = adapter3.getItem(position).toString();
                        Toast.makeText(getApplicationContext(),value3,Toast.LENGTH_LONG).show();
                    }
                });

                Cursor cursortotalday = databaseHelper.TotalIncomeYear(Year2);

                if (cursortotalday.getCount() == 0) {
                    TotalIncome.setText("0");
                }
                else
                {
                    while (cursortotalday.moveToNext()){
                        if(cursortotalday.getString(0) == null)
                        {
                            TotalIncome.setText("0 Tk.");
                        }else
                            TotalIncome.setText(cursortotalday.getString(0)+" Tk.");

                    }
                }
            }

        }
    }
}
