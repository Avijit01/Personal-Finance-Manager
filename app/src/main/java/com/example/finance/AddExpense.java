package com.example.finance;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AddExpense extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    String[] Expensenames;
    private Spinner spinner2;
    private TextView datetext2,entry2;
    private EditText amountExpense;
    private Button save2;
    int DayEx,MonthEx,YearEx;

    MyDatabaseHelper myDatabaseHelper2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        myDatabaseHelper2 = new MyDatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase2 = myDatabaseHelper2.getWritableDatabase();


        //spinner
        Expensenames = getResources().getStringArray(R.array.ExpenseCategory);
        spinner2 = (Spinner) findViewById(R.id.spinner2Id);
        ArrayAdapter<String> adapterEx = new ArrayAdapter<String>(this,R.layout.sample_view,R.id.textViewsampleId,Expensenames);
        spinner2.setAdapter(adapterEx);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbarAddExpenseId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        amountExpense = (EditText) findViewById(R.id.amountExpenseId);
        entry2 = (EditText) findViewById(R.id.Entry2Id);
        save2 = (Button) findViewById(R.id.btnsaveExpenseId);
        save2.setOnClickListener(this);

        datetext2 = findViewById(R.id.dateExpenseId);
        findViewById(R.id.btnExCalendarId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog2();
            }
        });

    }

    private void showDatePickerDialog2(){
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

        String data = dayOfMonth + "/" + (month + 1) + "/" + year;
        datetext2.setText(data);
        DayEx = dayOfMonth;
        MonthEx = month+1;
        YearEx = year;
    }


    @Override
    public void onClick(View v) {
        String spinnerExpense = spinner2.getSelectedItem().toString();
        String amount2 = amountExpense.getText().toString();
        String date2 = datetext2.getText().toString();
        String entryEx = entry2.getText().toString();
        int dayex = DayEx;
        int monthex = MonthEx;
        int yearex = YearEx;


        if(v.getId() == R.id.btnsaveExpenseId)
        {


            if(spinnerExpense.equals("Select Category") || amount2.equals("") || date2.equals("") || entryEx.equals(""))
            {
                Toast.makeText(getApplicationContext(),"Please Insert all data correctly!",Toast.LENGTH_LONG).show();
            }

            else if(date2.charAt(1) != '/' && date2.charAt(2) != '/'){
                Toast.makeText(getApplicationContext(),"Invalid Date Format!",Toast.LENGTH_LONG).show();
            }

            else{
                long rowId = myDatabaseHelper2.insertDataEx(spinnerExpense,amount2,date2,dayex,monthex,yearex,entryEx);

                if(rowId == -1)
                {
                    Toast.makeText(getApplicationContext(),"Unsuccessful",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Row "+rowId+" Successfully Inserted",Toast.LENGTH_LONG).show();
                    amountExpense.setText("");
                    datetext2.setText("");
                    spinner2.setSelection(0);
                    entry2.setText("EX-");
                }
            }

        }

    }
}
