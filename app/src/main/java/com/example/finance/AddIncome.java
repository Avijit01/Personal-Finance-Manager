package com.example.finance;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddIncome extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    String[] incomenames;
    private Spinner spinner;
    private TextView datetext;
    private EditText amount;
    private Button save;
    int Day,Month,Year;

    MyDatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);

        myDatabaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = myDatabaseHelper.getWritableDatabase();


        //spinner
        incomenames = getResources().getStringArray(R.array.names1);
        spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.sample_view,R.id.textViewsampleId,incomenames);
        spinner.setAdapter(adapter);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbarAddIncomeId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        amount = (EditText) findViewById(R.id.amount);
        save = (Button) findViewById(R.id.save1);
        save.setOnClickListener(this);

        datetext = findViewById(R.id.date);
        findViewById(R.id.btnCalendar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

    }

    private void showDatePickerDialog(){
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

        String data = dayOfMonth + "/" + (month + 1) + "/" + year;
        datetext.setText(data);
        Day = dayOfMonth;
        Month = month+1;
        Year = year;
    }


    @Override
    public void onClick(View v) {
        String spinner1 = spinner.getSelectedItem().toString();
        String amount1 = amount.getText().toString();
        String date1 = datetext.getText().toString();
        int day1 = Day;
        int month1 = Month;
        int year1 = Year;


        if(v.getId() == R.id.save1)
        {


            if(spinner1.equals("Select Category") || amount1.equals("") || date1.equals(""))
            {
                Toast.makeText(getApplicationContext(),"Please Insert all data correctly!",Toast.LENGTH_LONG).show();
            }

            else if(date1.charAt(1) != '/' && date1.charAt(2) != '/'){
                Toast.makeText(getApplicationContext(),"Invalid Date Format!",Toast.LENGTH_LONG).show();
            }

            else{
                long rowId = myDatabaseHelper.insertData(spinner1,amount1,date1,day1,month1,year1);

                if(rowId == -1)
                {
                    Toast.makeText(getApplicationContext(),"Unsuccessful",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Row "+rowId+" Successfully Inserted",Toast.LENGTH_LONG).show();
                    amount.setText("");
                    datetext.setText("");
                    spinner.setSelection(0);
                }
            }

        }

    }
}
