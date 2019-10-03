package com.example.finance;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

//@color/colorAccent

public class Comparison extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    private TextView ComPickDate, IndicateDateCom;
    private TextView TotalIncomeCom, TotalExpenseCom, TotalSavingsCom;
    private Button btnMonthCom, btnYearCom, btnOverAllCom;
    public int DayCom, YearCom, MonthCom;
    public String MonthComString, DateString;
    private MyDatabaseHelper databaseHelperCom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison);

        Toolbar toolbar = findViewById(R.id.toolbarComparison);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TotalIncomeCom = (TextView) findViewById(R.id.ComTotalIncomeId);
        TotalExpenseCom = (TextView) findViewById(R.id.ComTotalExpenseId);
        TotalSavingsCom = (TextView) findViewById(R.id.ComTotalSavingsId);
        IndicateDateCom = (TextView) findViewById(R.id.ComDateIndicatorId);


        databaseHelperCom = new MyDatabaseHelper(this);

        ComPickDate = findViewById(R.id.ComPickdateId);
        findViewById(R.id.CombtnPickdateId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerCom();
            }
        });

        LoadDataComparisonIncome();
        LoadDataComparisonExpense();
        LoadDataComparisonSavings();

        btnMonthCom = findViewById(R.id.CombtnMonthId);
        btnMonthCom.setOnClickListener(this);

        btnYearCom = findViewById(R.id.CombtnYearId);
        btnYearCom.setOnClickListener(this);

        btnOverAllCom = findViewById(R.id.CombtnOverallId);
        btnOverAllCom.setOnClickListener(this);
    }



    public void LoadDataComparisonIncome() {
        Cursor cursor1 = databaseHelperCom.TotalIncome();

        if (cursor1.getCount() == 0) {
            TotalIncomeCom.setText("0");
        }
        else
        {
            while (cursor1.moveToNext()){
                if(cursor1.getString(0) == null)
                {
                    TotalIncomeCom.setText("0 Tk.");
                }else
                    TotalIncomeCom.setText(cursor1.getString(0)+" Tk.");

            }
        }

    }

    public void LoadDataComparisonExpense() {
        Cursor cursor1 = databaseHelperCom.TotalExpense();

        if (cursor1.getCount() == 0) {
            TotalExpenseCom.setText("0");
        }
        else
        {
            while (cursor1.moveToNext()){
                if(cursor1.getString(0) == null)
                {
                    TotalExpenseCom.setText("0 Tk.");
                }else
                    TotalExpenseCom.setText(cursor1.getString(0)+" Tk.");

            }
        }

    }

    public void LoadDataComparisonSavings() {
        Cursor cursor1 = databaseHelperCom.TotalSavings();

        if (cursor1.getCount() == 0) {
            TotalSavingsCom.setText("0");
        }
        else
        {
            while (cursor1.moveToNext()){
                if(cursor1.getString(0) == null)
                {
                    TotalSavingsCom.setText("0 Tk.");
                }else
                    TotalSavingsCom.setText(cursor1.getString(0)+" Tk.");

            }
        }

        Cursor cursors5 = databaseHelperCom.TotalIncome();
        Cursor cursors6 = databaseHelperCom.TotalExpense();

        if(TotalSavingsCom.getText().equals("0 Tk.")){

            while (cursors5.moveToNext()){
                if(cursors5.getString(0) != null)
                    TotalSavingsCom.setText(cursors5.getString(0)+" Tk.");

            }

            while (cursors6.moveToNext()){
                if(cursors6.getString(0) != null)
                    TotalSavingsCom.setText("-"+cursors6.getString(0)+" Tk.");
            }
        }

    }


    private void showDatePickerCom()
    {
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
            MonthComString = "January";
        }
        if(month == 1){
            MonthComString = "February";
        }
        if(month == 2){
            MonthComString = "March";
        }
        if(month == 3){
            MonthComString = "April";
        }
        if(month == 4){
            MonthComString = "May";
        }
        if(month == 5){
            MonthComString = "June";
        }
        if(month == 6){
            MonthComString = "July";
        }
        if(month == 7){
            MonthComString = "August";
        }
        if(month == 8){
            MonthComString = "September";
        }
        if(month == 9){
            MonthComString = "October";
        }
        if(month == 10){
            MonthComString = "November";
        }
        if(month == 11){
            MonthComString = "December";
        }

        DateString = MonthComString+", "+year;
        ComPickDate.setText(DateString);
        DayCom = dayOfMonth;
        MonthCom = month + 1;
        YearCom = year;
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.CombtnMonthId) {
            if(ComPickDate.getText().equals("")){
                Toast.makeText(getApplicationContext(), "Please pick any date",Toast.LENGTH_LONG).show();
            }

            else{
                IndicateDateCom.setText(DateString);

                IncomeComparisonMonth();
                ExpenseComparisonMonth();
                SavingsComparisonMonth();
            }
        }


        if(v.getId() == R.id.CombtnYearId) {
            if(ComPickDate.getText().equals("")){
                Toast.makeText(getApplicationContext(), "Please pick any date",Toast.LENGTH_LONG).show();
            }

            else{
                IndicateDateCom.setText("Year: "+YearCom);

                IncomeComparisonYear();
                ExpenseComparisonYear();
                SavingsComparisonYear();

            }
        }

        if(v.getId() == R.id.CombtnOverallId) {
            if(ComPickDate.getText().equals("")){
                Toast.makeText(getApplicationContext(), "Please pick any date",Toast.LENGTH_LONG).show();
            }

            else{
                IndicateDateCom.setText("OverAll");

                LoadDataComparisonIncome();
                LoadDataComparisonExpense();
                LoadDataComparisonSavings();
            }
        }


    }

    public void IncomeComparisonMonth()
    {
        Cursor cursortotalMonth = databaseHelperCom.TotalIncomeMonth(MonthCom,YearCom);

        if (cursortotalMonth.getCount() == 0) {
            TotalIncomeCom.setText("0");
        }
        else
        {
            while (cursortotalMonth.moveToNext()){
                if(cursortotalMonth.getString(0) == null)
                {
                    TotalIncomeCom.setText("0 Tk.");
                }else
                    TotalIncomeCom.setText(cursortotalMonth.getString(0)+" Tk.");

            }
        }
    }

    public void ExpenseComparisonMonth()
    {
        Cursor cursortotalMonthEx = databaseHelperCom.TotalExpenseMonth(MonthCom,YearCom);

        if (cursortotalMonthEx.getCount() == 0) {
            TotalExpenseCom.setText("0");
        }
        else
        {
            while (cursortotalMonthEx.moveToNext()){
                if(cursortotalMonthEx.getString(0) == null)
                {
                    TotalExpenseCom.setText("0 Tk.");
                }else
                    TotalExpenseCom.setText(cursortotalMonthEx.getString(0)+" Tk.");

            }
        }
    }

    public void SavingsComparisonMonth()
    {
        Cursor cursortotalMonthSavings = databaseHelperCom.TotalSavingsMonth(MonthCom,YearCom);

        if (cursortotalMonthSavings.getCount() == 0) {
            TotalExpenseCom.setText("0");
        }
        else
        {
            while (cursortotalMonthSavings.moveToNext()){
                if(cursortotalMonthSavings.getString(0) == null)
                {
                    TotalSavingsCom.setText("0 Tk.");
                }else
                    TotalSavingsCom.setText(cursortotalMonthSavings.getString(0)+" Tk.");

            }
        }

        Cursor cursors1 = databaseHelperCom.TotalIncomeMonth(MonthCom,YearCom);
        Cursor cursors2 = databaseHelperCom.TotalExpenseMonth(MonthCom,YearCom);

        if(TotalSavingsCom.getText().equals("0 Tk.")){

            while (cursors1.moveToNext()){
                if(cursors1.getString(0) != null)
                    TotalSavingsCom.setText(cursors1.getString(0)+" Tk.");

            }

            while (cursors2.moveToNext()){
                if(cursors2.getString(0) != null)
                    TotalSavingsCom.setText("-"+cursors2.getString(0)+" Tk.");
            }
        }
    }

    public void IncomeComparisonYear()
    {
        Cursor cursortotalYear = databaseHelperCom.TotalIncomeYear(YearCom);

        if (cursortotalYear.getCount() == 0) {
            TotalIncomeCom.setText("0");
        }
        else
        {
            while (cursortotalYear.moveToNext()){
                if(cursortotalYear.getString(0) == null)
                {
                    TotalIncomeCom.setText("0 Tk.");
                }else
                    TotalIncomeCom.setText(cursortotalYear.getString(0)+" Tk.");

            }
        }
    }

    public void ExpenseComparisonYear()
    {
        Cursor cursorEx = databaseHelperCom.TotalExpenseYear(YearCom);

        if (cursorEx.getCount() == 0) {
            TotalExpenseCom.setText("0");
        }
        else
        {
            while (cursorEx.moveToNext()){
                if(cursorEx.getString(0) == null)
                {
                    TotalExpenseCom.setText("0 Tk.");
                }else
                    TotalExpenseCom.setText(cursorEx.getString(0)+" Tk.");

            }
        }

    }

    public void SavingsComparisonYear()
    {
        Cursor cursorSav = databaseHelperCom.TotalSavingsYear(YearCom);

        if (cursorSav.getCount() == 0) {
            TotalSavingsCom.setText("0");
        }
        else
        {
            while (cursorSav.moveToNext()){
                if(cursorSav.getString(0) == null)
                {
                    TotalSavingsCom.setText("0 Tk.");
                }else
                    TotalSavingsCom.setText(cursorSav.getString(0)+" Tk.");
            }
        }

        Cursor cursors3 = databaseHelperCom.TotalIncomeYear(YearCom);
        Cursor cursors4 = databaseHelperCom.TotalExpenseYear(YearCom);

        if(TotalSavingsCom.getText().equals("0 Tk.")){

            while (cursors3.moveToNext()){
                if(cursors3.getString(0) != null)
                    TotalSavingsCom.setText(cursors3.getString(0)+" Tk.");

            }

            while (cursors4.moveToNext()){
                if(cursors4.getString(0) != null)
                    TotalSavingsCom.setText("-"+cursors4.getString(0)+" Tk.");
            }
        }
    }

}
