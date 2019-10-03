package com.example.finance;

import android.app.DatePickerDialog;
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

import java.util.Calendar;

public class EditExpense extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    String[] ExpenseNamesEdit;
    private Spinner spinnerEditEx;
    private TextView datetextExpenseEdit;
    private EditText EditExAmount, EditExentry;
    private Button EditSaveEx, EditDeleteEx;
    private Button btnCheckEx;
    int DayEditEx, MonthEditEx, YearEditEx;
    private MyDatabaseHelper myDatabaseHelperEditEx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);

        myDatabaseHelperEditEx = new MyDatabaseHelper(this);
        //SQLiteDatabase sqLiteDatabase = myDatabaseHelperEdit.getWritableDatabase();

        //spinner
        ExpenseNamesEdit = getResources().getStringArray(R.array.ExpenseCategory);
        spinnerEditEx = (Spinner) findViewById(R.id.spinnerEditExpenseId);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.sample_view, R.id.textViewsampleId, ExpenseNamesEdit);
        spinnerEditEx.setAdapter(adapter);

        //toolbar
        Toolbar toolbarEditEx = findViewById(R.id.toolbarEditExpenseId);
        setSupportActionBar(toolbarEditEx);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditExAmount = (EditText) findViewById(R.id.amountEditExpenseId);
        EditExentry = (EditText) findViewById(R.id.EntryExEditId);

        EditSaveEx = (Button) findViewById(R.id.EditExpenseId);
        EditSaveEx.setOnClickListener(this);

        EditDeleteEx = (Button) findViewById(R.id.DeleteExpenseId);
        EditDeleteEx.setOnClickListener(this);

        btnCheckEx = (Button) findViewById(R.id.CheckExEditId);
        btnCheckEx.setOnClickListener(this);

        datetextExpenseEdit = findViewById(R.id.dateEditExpenseId);
        findViewById(R.id.btnCalenderEditExId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialogEditExpense();
            }
        });
    }

    private void showDatePickerDialogEditExpense() {
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
        datetextExpenseEdit.setText(data);
        DayEditEx = dayOfMonth;
        MonthEditEx = month + 1;
        YearEditEx = year;
    }

    @Override
    public void onClick(View v) {

        String spinnerEditExpense = spinnerEditEx.getSelectedItem().toString();
        String amountEditExpense = EditExAmount.getText().toString();
        String entryEditExpense = EditExentry.getText().toString();
        String dateEditExpense = datetextExpenseEdit.getText().toString();
        int dayEditExpense = DayEditEx;
        int monthEditExpense = MonthEditEx;
        int yearEditExpense = YearEditEx;

        if (v.getId() == R.id.EditExpenseId) {
            Boolean isUpdated = myDatabaseHelperEditEx.updateExpense(entryEditExpense, spinnerEditExpense, amountEditExpense, dateEditExpense, dayEditExpense, monthEditExpense, yearEditExpense);

            if (isUpdated == true) {
                Toast.makeText(getApplicationContext(), "data is updated", Toast.LENGTH_LONG).show();
                spinnerEditEx.setSelection(0);
                EditExAmount.setText("");
                EditExentry.setText("EX-");
                datetextExpenseEdit.setText("");
            } else {
                Toast.makeText(getApplicationContext(), "data is not updated", Toast.LENGTH_LONG).show();
            }
        }

        if (v.getId() == R.id.DeleteExpenseId) {
            int value = myDatabaseHelperEditEx.deleteExpense(entryEditExpense);
            if (value < 0) {
                Toast.makeText(getApplicationContext(), "data is not deleted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "data is successfully deleted", Toast.LENGTH_LONG).show();
            }
        }

        if (v.getId() == R.id.CheckExEditId) {
            //Cursor cursor8 = myDatabaseHelperEdit.Edit(entryEditIncome);

            //if (cursor8.getCount() == 0) {
            EditExentry.setText("Still Working");
            EditExAmount.setText("Still Working");
            //}

            //else {
            //   while (cursorEdit.moveToNext())
            //   {
            //       if(cursorEdit.getString(0) == null) {
            //           Editentry.setText("Its Okay");
            //      }
            //       else{
            //           datetextIncomeEdit.setText(cursorEdit.getString(0));
            //       }

            //  }
            //}


        }

    }


}
