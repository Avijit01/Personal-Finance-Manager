package com.example.finance;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button1,button2,button3,button4,button5,buttonPie;

    MyDatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDatabaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = myDatabaseHelper.getWritableDatabase();

        button1 = (Button) findViewById(R.id.buttonIncome);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openincome();
            }
        });

        button2 = (Button) findViewById(R.id.buttonExpense);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openexpense();
            }
        });

        button3 = (Button) findViewById(R.id.buttonSavings);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opensavings();
            }
        });

        button4 = (Button) findViewById(R.id.buttonComparisonId);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opencomparison();
            }
        });

        button5 = (Button) findViewById(R.id.buttonAboutId);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openabout();
            }
        });

        buttonPie = (Button) findViewById(R.id.buttonPieChartId);
        buttonPie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPieChart();
            }
        });
    }
    public void openincome(){
        Intent intent = new Intent(this,income.class);
        startActivity(intent);
    }

    public void openexpense(){
        Intent intent = new Intent(this,Expense.class);
        startActivity(intent);
    }

    public void opensavings(){
        Intent intent = new Intent(this,Savings.class);
        startActivity(intent);
    }

    public void opencomparison(){
        Intent intent = new Intent(this,Comparison.class);
        startActivity(intent);
    }

    public void openabout(){
        Intent intent = new Intent(this,About.class);
        startActivity(intent);
    }

    public void openPieChart(){
        Intent intent = new Intent(this,PieChart.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to Exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        //Exit
    }



}
