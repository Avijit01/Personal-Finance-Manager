package com.example.finance;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button1,button2,button3;

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

}
