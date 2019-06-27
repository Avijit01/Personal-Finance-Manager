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

public class Expense extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {
    private Button btnaddexpense;
    private ListView listViewEx;
    private MyDatabaseHelper databaseHelperEx;
    private TextView TotalExpense,DateIndicatorExpense;
    private TextView pickdateEx;
    public int Day4,Year4,Month4;
    public String MonthstringEx;
    private Button btnDayEx,btnMonthEx,btnYearEx,buttonEditExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        Toolbar toolbarEx = findViewById(R.id.toolbarExpenseId);
        setSupportActionBar(toolbarEx);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TotalExpense = (TextView) findViewById(R.id.TotalExpenseId);
        DateIndicatorExpense = (TextView) findViewById(R.id.DateIndicatorExpenseId);

        btnaddexpense = (Button) findViewById(R.id.addExId);
        btnaddexpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddExpense();
            }
        });

        buttonEditExpense = (Button) findViewById(R.id.EditDeleteExId);
        buttonEditExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditExpense();
            }
        });

        listViewEx = (ListView) findViewById(R.id.listViewIdEx);
        databaseHelperEx = new MyDatabaseHelper(this);

        pickdateEx = findViewById(R.id.PickdateIdEx);
        findViewById(R.id.btnPickdateIdEx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerEx();
            }
        });

        loadDataEx();


        btnDayEx = findViewById(R.id.btnDayIdEx);
        btnDayEx.setOnClickListener(this);

        btnMonthEx = findViewById(R.id.btnMonthIdEx);
        btnMonthEx.setOnClickListener(this);

        btnYearEx = findViewById(R.id.btnYearIdEx);
        btnYearEx.setOnClickListener(this);
    }



    public void openAddExpense(){
        Intent intent = new Intent(this, AddExpense.class);
        startActivity(intent);
    }

    public void openEditExpense(){
        Intent intent = new Intent(this, EditExpense.class);
        startActivity(intent);
    }

    public void loadDataEx() {

        ArrayList<String> listDataEx = new ArrayList<>();

        Cursor cursor2 = databaseHelperEx.showAllDataEx();

        if (cursor2.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "No data is available in database",Toast.LENGTH_LONG).show();
        }
        else
        {

            while (cursor2.moveToNext()){
                listDataEx.add("Date:            "+cursor2.getString(0)+"\n"+"Category:    "+cursor2.getString(1)+"\n"+"Amount:      "+cursor2.getString(2)+" Tk."+"\n"+"Entry:           "+cursor2.getString(3));

            }
        }

        Cursor cursor3 = databaseHelperEx.TotalExpense();

        if (cursor3.getCount() == 0) {
            TotalExpense.setText("0");
        }
        else
        {
            while (cursor3.moveToNext()){
                if(cursor3.getString(0) == null)
                {
                    TotalExpense.setText("0 Tk.");
                }else
                    TotalExpense.setText(cursor3.getString(0)+" Tk.");

            }
        }



        final ArrayAdapter<String> adapterEx = new ArrayAdapter<String>(this,R.layout.list_item,R.id.textViewId2,listDataEx);
        listViewEx.setAdapter(adapterEx);

        //optional
        listViewEx.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String value = adapterEx.getItem(position).toString();
                //Toast.makeText(getApplicationContext(),value,Toast.LENGTH_LONG).show();
            }
        });


    }

    private void showDatePickerEx(){
        DatePickerDialog datePickerDialogEx = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialogEx.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        if(month == 0){
            MonthstringEx = "January";
        }
        if(month == 1){
            MonthstringEx = "February";
        }
        if(month == 2){
            MonthstringEx = "March";
        }
        if(month == 3){
            MonthstringEx = "April";
        }
        if(month == 4){
            MonthstringEx = "May";
        }
        if(month == 5){
            MonthstringEx = "June";
        }
        if(month == 6){
            MonthstringEx = "July";
        }
        if(month == 7){
            MonthstringEx = "August";
        }
        if(month == 8){
            MonthstringEx = "September";
        }
        if(month == 9){
            MonthstringEx = "October";
        }
        if(month == 10){
            MonthstringEx = "November";
        }
        if(month == 11){
            MonthstringEx = "December";
        }

        String dateExList = dayOfMonth+" "+MonthstringEx+", "+year;
        pickdateEx.setText(dateExList);
        Day4 = dayOfMonth;
        Month4 = month + 1;
        Year4 = year;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnDayIdEx){

            if(pickdateEx.getText().equals("")){
                Toast.makeText(getApplicationContext(), "Please pick any date",Toast.LENGTH_LONG).show();
            }

            else{

                DateIndicatorExpense.setText(Day4+" "+MonthstringEx+", "+Year4);

                ArrayList<String> listDataDayEx = new ArrayList<>();

                Cursor cursordayEx = databaseHelperEx.showDayDataEx(Day4, Month4,Year4);

                if (cursordayEx.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "No data is available in database",Toast.LENGTH_LONG).show();
                }
                else
                {

                    while (cursordayEx.moveToNext()){
                        listDataDayEx.add("Date: "+cursordayEx.getString(0)+"\n"+"Category: "+cursordayEx.getString(1)+"\n"+"Amount: "+cursordayEx.getString(2)+" Tk."+"\n"+"Entry: "+cursordayEx.getString(3));

                    }
                }

                final ArrayAdapter<String> adapterEx1 = new ArrayAdapter<String>(this,R.layout.list_item,R.id.textViewId2,listDataDayEx);
                listViewEx.setAdapter(adapterEx1);

                listViewEx.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String value = adapterEx1.getItem(position).toString();
                        Toast.makeText(getApplicationContext(),value,Toast.LENGTH_LONG).show();
                    }
                });

                Cursor cursortotaldayEx = databaseHelperEx.TotalExpenseday(Day4,Month4,Year4);

                if (cursortotaldayEx.getCount() == 0) {
                    TotalExpense.setText("0");
                }
                else
                {
                    while (cursortotaldayEx.moveToNext()){
                        if(cursortotaldayEx.getString(0) == null)
                        {
                            TotalExpense.setText("0 Tk.");
                        }else
                            TotalExpense.setText(cursortotaldayEx.getString(0)+" Tk.");

                    }
                }
            }


        }

        if(v.getId() == R.id.btnMonthIdEx){

            if(pickdateEx.getText().equals("")){
                Toast.makeText(getApplicationContext(), "Please pick any date",Toast.LENGTH_LONG).show();
            }

            else {

                DateIndicatorExpense.setText(MonthstringEx+", "+Year4);

                //Toast.makeText(getApplicationContext(),Month4+", "+Year4,Toast.LENGTH_LONG).show();
                ArrayList<String> listDataMonthEx = new ArrayList<>();

                Cursor cursormonthEx = databaseHelperEx.showMonthDataEx(Month4,Year4);

                if (cursormonthEx.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "No data is available in database",Toast.LENGTH_LONG).show();
                }
                else {

                    while (cursormonthEx.moveToNext()){
                        listDataMonthEx.add("Date: "+cursormonthEx.getString(0)+"\n"+"Category: "+cursormonthEx.getString(1)+"\n"+"Amount: "+cursormonthEx.getString(2)+" Tk."+"\n"+"Entry: "+cursormonthEx.getString(3));

                    }
                }

                final ArrayAdapter<String> adapterex2 = new ArrayAdapter<String>(this,R.layout.list_item,R.id.textViewId2,listDataMonthEx);
                listViewEx.setAdapter(adapterex2);

                listViewEx.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String value = adapterex2.getItem(position).toString();
                        Toast.makeText(getApplicationContext(),value,Toast.LENGTH_LONG).show();
                    }
                });

                Cursor cursortotalmonth = databaseHelperEx.TotalExpenseMonth(Month4,Year4);

                if (cursortotalmonth.getCount() == 0) {
                    TotalExpense.setText("0");
                }
                else
                {
                    while (cursortotalmonth.moveToNext()){
                        if(cursortotalmonth.getString(0) == null)
                        {
                            TotalExpense.setText("0 Tk.");
                        }else
                            TotalExpense.setText(cursortotalmonth.getString(0)+" Tk.");

                    }
                }
            }


        }
        if(v.getId() == R.id.btnYearIdEx){

            if(pickdateEx.getText().equals("")){
                Toast.makeText(getApplicationContext(), "Please pick any date",Toast.LENGTH_LONG).show();
            }

            else {

                DateIndicatorExpense.setText("Year: "+Year4);

                ArrayList<String> listDataYearEx = new ArrayList<>();

                Cursor cursoryearEx = databaseHelperEx.showYearDataEx(Year4);

                if (cursoryearEx.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "No data is available in database",Toast.LENGTH_LONG).show();
                }
                else
                {

                    while (cursoryearEx.moveToNext()){
                        listDataYearEx.add("Date: "+cursoryearEx.getString(0)+"\n"+"Category: "+cursoryearEx.getString(1)+"\n"+"Amount: "+cursoryearEx.getString(2)+" Tk."+"\n"+"Entry: "+cursoryearEx.getString(3));

                    }
                }

                final ArrayAdapter<String> adapteryearEx = new ArrayAdapter<String>(this,R.layout.list_item,R.id.textViewId2,listDataYearEx);
                listViewEx.setAdapter(adapteryearEx);

                listViewEx.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String value3 = adapteryearEx.getItem(position).toString();
                        Toast.makeText(getApplicationContext(),value3,Toast.LENGTH_LONG).show();
                    }
                });

                Cursor cursortotalyearEx = databaseHelperEx.TotalExpenseYear(Year4);

                if (cursortotalyearEx.getCount() == 0) {
                    TotalExpense.setText("0");
                }
                else
                {
                    while (cursortotalyearEx.moveToNext()){
                        if(cursortotalyearEx.getString(0) == null)
                        {
                            TotalExpense.setText("0 Tk.");
                        }else
                            TotalExpense.setText(cursortotalyearEx.getString(0)+" Tk.");

                    }
                }
            }

        }
    }
}

