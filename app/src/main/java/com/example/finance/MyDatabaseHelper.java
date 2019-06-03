package com.example.finance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Finance.db";
    private static final String TABLE_NAME = "Income";
    private static final String ENTRY = "Entry";                    //0
    private static final String DATE = "Date";                      //1
    private static final String CATEGORY = "Category_Income";       //2
    private static final String CATEGORY_EX = "Category_Expense";   //3
    private static final String AMOUNT = "Amount";                  //4
    private static final String AMOUNT_EX = "Amount_Expense";       //5
    private static final String DAY = "Day";                        //6
    private static final String MONTH = "Month";                    //7
    private static final String YEAR = "Year";                      //8
    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+"("+ENTRY+" VARCHAR(30) Primary Key, "+DATE+" VARCHAR(30), "+CATEGORY+" VARCHAR(30), "+CATEGORY_EX+" VARCHAR(30), "+AMOUNT+" decimal(22,2), "+AMOUNT_EX+" decimal(22,2), "+DAY+" int(5), "+MONTH+" int(5), "+YEAR+" int(5)); ";
    private static final int VERSION_NUMBER = 19;
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;

    private Context context;

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            Toast.makeText(context,"onCreate is called",Toast.LENGTH_LONG).show();
            db.execSQL(CREATE_TABLE);

        }catch (Exception e)
        {
            Toast.makeText(context,"Exception : "+e,Toast.LENGTH_LONG).show();
        }



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            Toast.makeText(context,"onUpgrade is called",Toast.LENGTH_LONG).show();
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }catch (Exception e)
        {
            Toast.makeText(context,"Exception : "+e,Toast.LENGTH_LONG).show();
        }

    }

    public long insertData(String category, String amount, String date, int day, int month, int year, String entry)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY,category);
        contentValues.put(AMOUNT,amount);
        contentValues.put(DATE,date);
        contentValues.put(DAY,day);
        contentValues.put(MONTH,month);
        contentValues.put(YEAR,year);
        contentValues.put(ENTRY,entry);
        long rowId = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        return rowId;
    }

    public long insertDataEx(String categoryEx, String amountEx, String date, int day, int month, int year, String entry)
    {
        SQLiteDatabase sqLiteDatabase2 = this.getWritableDatabase();
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(CATEGORY_EX,categoryEx);
        contentValues2.put(AMOUNT_EX,amountEx);
        contentValues2.put(DATE,date);
        contentValues2.put(DAY,day);
        contentValues2.put(MONTH,month);
        contentValues2.put(YEAR,year);
        contentValues2.put(ENTRY,entry);
        long rowId2 = sqLiteDatabase2.insert(TABLE_NAME,null,contentValues2);
        return rowId2;
    }

    public Cursor showAllData()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor1 = sqLiteDatabase.rawQuery("SELECT "+DATE+","+CATEGORY+","+AMOUNT+","+ENTRY+" FROM "+TABLE_NAME+" WHERE ("+CATEGORY+" != '') ORDER BY "+YEAR+" DESC, "+MONTH+" DESC, "+DAY+" DESC",null);
        return cursor1;
    }

    public Cursor showAllDataEx()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursorExAllData = sqLiteDatabase.rawQuery("SELECT "+DATE+","+CATEGORY_EX+","+AMOUNT_EX+","+ENTRY+" FROM "+TABLE_NAME+" WHERE ("+CATEGORY_EX+" != '') ORDER BY "+YEAR+" DESC, "+MONTH+" DESC, "+DAY+" DESC",null);
        return cursorExAllData;
    }

    public Cursor TotalIncome()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor2 = sqLiteDatabase.rawQuery("SELECT SUM("+AMOUNT+") FROM "+TABLE_NAME,null);
        return cursor2;
    }

    public Cursor TotalExpense()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursortotalEx = sqLiteDatabase.rawQuery("SELECT SUM("+AMOUNT_EX+") FROM "+TABLE_NAME,null);
        return cursortotalEx;
    }

    public Cursor TotalSavings()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursortotalSavings = sqLiteDatabase.rawQuery("SELECT (SUM("+AMOUNT+") - SUM("+AMOUNT_EX+")) FROM "+TABLE_NAME,null);
        return cursortotalSavings;
    }


    public Cursor showDayData(int day, int month, int year)
    {
        int day1 = day;
        int month1 = month;
        int year1 = year;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursorday = sqLiteDatabase.rawQuery("SELECT "+DATE+","+CATEGORY+","+AMOUNT+","+ENTRY+" FROM "+TABLE_NAME+" WHERE ("+YEAR+" = "+year1+" AND "+MONTH+" = "+month1+" AND "+DAY+" = "+day1+" AND "+CATEGORY+" != '')",null);
        return cursorday;
    }

    public Cursor showDayDataEx(int day, int month, int year)
    {
        int day1 = day;
        int month1 = month;
        int year1 = year;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursordayEx = sqLiteDatabase.rawQuery("SELECT "+DATE+","+CATEGORY_EX+","+AMOUNT_EX+","+ENTRY+" FROM "+TABLE_NAME+" WHERE ("+YEAR+" = "+year1+" AND "+MONTH+" = "+month1+" AND "+DAY+" = "+day1+" AND "+CATEGORY_EX+" != '')",null);
        return cursordayEx;
    }


    public Cursor TotalIncomeday(int day, int month, int year)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor2 = sqLiteDatabase.rawQuery("SELECT SUM("+AMOUNT+") FROM "+TABLE_NAME+" WHERE "+YEAR+" = "+year+" AND "+MONTH+" = "+month+" AND "+DAY+" = "+day+"",null);
        return cursor2;
    }


    public Cursor TotalExpenseday(int day, int month, int year)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursortotalday = sqLiteDatabase.rawQuery("SELECT SUM("+AMOUNT_EX+") FROM "+TABLE_NAME+" WHERE "+YEAR+" = "+year+" AND "+MONTH+" = "+month+" AND "+DAY+" = "+day+"",null);
        return cursortotalday;
    }


    public Cursor showMonthData(int month, int year)
    {
        int month1 = month;
        int year1 = year;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursormonth = sqLiteDatabase.rawQuery("SELECT "+DATE+","+CATEGORY+","+AMOUNT+","+ENTRY+" FROM "+TABLE_NAME+" WHERE ("+YEAR+" = "+year1+" AND "+MONTH+" = "+month1+" AND "+CATEGORY+" != '') ORDER BY "+DAY+" DESC",null);
        return cursormonth;
    }

    public Cursor showMonthDataEx(int month, int year)
    {
        int month1 = month;
        int year1 = year;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursorMonthEx = sqLiteDatabase.rawQuery("SELECT "+DATE+","+CATEGORY_EX+","+AMOUNT_EX+","+ENTRY+" FROM "+TABLE_NAME+" WHERE ("+YEAR+" = "+year1+" AND "+MONTH+" = "+month1+" AND "+CATEGORY_EX+" != '') ORDER BY "+DAY+" DESC",null);
        return cursorMonthEx;
    }


    public Cursor TotalIncomeMonth(int month, int year)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor3 = sqLiteDatabase.rawQuery("SELECT SUM("+AMOUNT+") FROM "+TABLE_NAME+" WHERE "+YEAR+" = "+year+" AND "+MONTH+" = "+month+"",null);
        return cursor3;
    }

    public Cursor TotalExpenseMonth(int month, int year)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursorTotalMonthEx = sqLiteDatabase.rawQuery("SELECT SUM("+AMOUNT_EX+") FROM "+TABLE_NAME+" WHERE "+YEAR+" = "+year+" AND "+MONTH+" = "+month+"",null);
        return cursorTotalMonthEx;
    }

    public Cursor TotalSavingsMonth(int month, int year)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursorTotalMonthSave = sqLiteDatabase.rawQuery("SELECT (SUM("+AMOUNT+") - SUM("+AMOUNT_EX+")) FROM "+TABLE_NAME+" WHERE "+YEAR+" = "+year+" AND "+MONTH+" = "+month+"",null);
        return cursorTotalMonthSave;
    }


    public Cursor showYearData(int year)
    {
        int year3 = year;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursorYear = sqLiteDatabase.rawQuery("SELECT "+DATE+","+CATEGORY+","+AMOUNT+","+ENTRY+" FROM "+TABLE_NAME+" WHERE ("+YEAR+" = "+year3+" AND "+CATEGORY+" != '') ORDER BY "+YEAR+" DESC, "+MONTH+" DESC, "+DAY+" DESC",null);
        return cursorYear;
    }



    public Cursor showYearDataEx(int year)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursorYearEx = sqLiteDatabase.rawQuery("SELECT "+DATE+","+CATEGORY_EX+","+AMOUNT_EX+","+ENTRY+" FROM "+TABLE_NAME+" WHERE ("+YEAR+" = "+year+" AND "+CATEGORY_EX+" != '') ORDER BY "+YEAR+" DESC, "+MONTH+" DESC, "+DAY+" DESC",null);
        return cursorYearEx;
    }

    public Cursor TotalIncomeYear(int year)
    {
        int year4 = year;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor4 = sqLiteDatabase.rawQuery("SELECT SUM("+AMOUNT+") FROM "+TABLE_NAME+" WHERE "+YEAR+" = "+year4+"",null);
        return cursor4;
    }

    public Cursor TotalExpenseYear(int year)
    {
        int yearEx = year;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursorTotalYearEx = sqLiteDatabase.rawQuery("SELECT SUM("+AMOUNT_EX+") FROM "+TABLE_NAME+" WHERE "+YEAR+" = "+yearEx+"",null);
        return cursorTotalYearEx;
    }

    public Cursor TotalSavingsYear(int year)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursorTotalYearSave = sqLiteDatabase.rawQuery("SELECT (SUM("+AMOUNT+") - SUM("+AMOUNT_EX+")) FROM "+TABLE_NAME+" WHERE "+YEAR+" = "+year+"",null);
        return cursorTotalYearSave;
    }

    public Boolean updateIncome(String entry, String spinner, String amount, String date,int day, int month, int year)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ENTRY,entry);
        contentValues.put(CATEGORY,spinner);
        contentValues.put(AMOUNT,amount);
        contentValues.put(DATE,date);
        contentValues.put(DAY,day);
        contentValues.put(MONTH,month);
        contentValues.put(YEAR,year);
        sqLiteDatabase.update(TABLE_NAME,contentValues,ENTRY+" = ?",new String[] {entry});
        return true;
    }

    public int deleteIncome(String entry)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int value = sqLiteDatabase.delete(TABLE_NAME,ENTRY+" = ?",new String[] {entry});
        return value;
    }

    //EditIncome check
    public Cursor Edit(String entry)
    {
        String entry1 = entry;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursorEntry = sqLiteDatabase.rawQuery("SELECT "+DATE+","+CATEGORY+","+AMOUNT+" FROM "+TABLE_NAME+" WHERE "+ENTRY+" = "+entry1+"",null);
        return cursorEntry;
    }


}
