package com.example.finance;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class EditIncome extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    String[] incomenamesEdit;
    private Spinner spinnerEdit;
    private TextView datetextIncomeEdit;
    private EditText Editamount, Editentry;
    private Button EditSave, EditDelete;
    private Button btnCheck;
    int DayEdit, MonthEdit, YearEdit;

    MyDatabaseHelper myDatabaseHelperEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_income);

        myDatabaseHelperEdit = new MyDatabaseHelper(this);
        //SQLiteDatabase sqLiteDatabase = myDatabaseHelperEdit.getWritableDatabase();

        //spinner
        incomenamesEdit = getResources().getStringArray(R.array.names1);
        spinnerEdit = (Spinner) findViewById(R.id.spinnerEditIncomeId);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.sample_view, R.id.textViewsampleId, incomenamesEdit);
        spinnerEdit.setAdapter(adapter);

        //toolbar
        Toolbar toolbarEdit = findViewById(R.id.toolbarEditIncomeId);
        setSupportActionBar(toolbarEdit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Editamount = (EditText) findViewById(R.id.amountEditIncomeId);
        Editentry = (EditText) findViewById(R.id.EntryINEditId);

        EditSave = (Button) findViewById(R.id.EditIncomeId);
        EditSave.setOnClickListener(this);

        EditDelete = (Button) findViewById(R.id.DeleteIncomeId);
        EditDelete.setOnClickListener(this);

        btnCheck = (Button) findViewById(R.id.CheckEditId);
        btnCheck.setOnClickListener(this);

        datetextIncomeEdit = findViewById(R.id.dateEditIncomeId);
        findViewById(R.id.btnCalenderEditInId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialogEditIncome();
            }
        });
    }

    private void showDatePickerDialogEditIncome() {
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
        datetextIncomeEdit.setText(data);
        DayEdit = dayOfMonth;
        MonthEdit = month + 1;
        YearEdit = year;
    }

    @Override
    public void onClick(View v) {

        String spinnerEditIncome = spinnerEdit.getSelectedItem().toString();
        String amountEditIncome = Editamount.getText().toString();
        String entryEditIncome = Editentry.getText().toString();
        String dateEditIncome = datetextIncomeEdit.getText().toString();
        int dayEditIncome = DayEdit;
        int monthEditIncome = MonthEdit;
        int yearEditIncome = YearEdit;

        if (v.getId() == R.id.EditIncomeId) {
            Boolean isUpdated = myDatabaseHelperEdit.updateIncome(entryEditIncome, spinnerEditIncome, amountEditIncome, dateEditIncome, dayEditIncome, monthEditIncome, yearEditIncome);

            if (isUpdated == true) {
                Toast.makeText(getApplicationContext(), "data is updated", Toast.LENGTH_LONG).show();
                spinnerEdit.setSelection(0);
                Editamount.setText("");
                Editentry.setText("IN-");
                datetextIncomeEdit.setText("");
            } else {
                Toast.makeText(getApplicationContext(), "data is not updated", Toast.LENGTH_LONG).show();
            }
        }

        if (v.getId() == R.id.DeleteIncomeId) {
            int value = myDatabaseHelperEdit.deleteIncome(entryEditIncome);
            if (value < 0) {
                Toast.makeText(getApplicationContext(), "data is not deleted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "data is successfully deleted", Toast.LENGTH_LONG).show();
            }
        }


        if (v.getId() == R.id.CheckEditId) {


           /* Cursor cursorin = myDatabaseHelperEdit.Edit(entryEditIncome);

            if (cursorin.getCount() == 0) {
                Editentry.setText("");
            }
            else
            {
                while (cursorin.moveToNext()){
                    if(cursorin.getString(0) == null)
                    {
                        Editentry.setText("");
                    }else{
                        datetextIncomeEdit.setText(cursorin.getString(1));
                        Editamount.setText(cursorin.getString(4)+" Tk.");
                    }



                }
            }
            */

            //Cursor cursor8 = myDatabaseHelperEdit.Edit(entryEditIncome);

            //if (cursor8.getCount() == 0) {
            datetextIncomeEdit.setText("Still Working");
            Editamount.setText("Still Workking");
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
