package com.example.expenselock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(Context context) {
        super(context, "expense_lock", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table expense (id INTEGER PRIMARY KEY AUTOINCREMENT,amount DOUBLE,type TEXT,reason TEXT,addnote TEXT,time TEXT)");
        db.execSQL("create table income (id INTEGER PRIMARY KEY AUTOINCREMENT, amount DOUBLE,type TEXT,reason TEXT,addnote TEXT,time TEXT)");
        db.execSQL("create table savings (id INTEGER PRIMARY KEY AUTOINCREMENT,amount DOUBLE,time TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS expense");
        db.execSQL("DROP TABLE IF EXISTS income");
        db.execSQL("DROP TABLE IF EXISTS savings");
        onCreate(db);

    }

    public void addExpense (double amount,String type,String reason,String addnote){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues conval = new ContentValues();
        conval.put("amount",amount);
        conval.put("reason",reason);
        conval.put("type",type);
        conval.put("addnote",addnote);
        conval.put("time",System.currentTimeMillis());
        db.insert("expense",null,conval);
    }

    public void addIncome (double amount,String type,String reason,String addnote){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues conval = new ContentValues();
        conval.put("amount",amount);
        conval.put("type",type);
        conval.put("reason",reason);
        conval.put("addnote",addnote);
        conval.put("time",System.currentTimeMillis());
        db.insert("income",null,conval);
    }

    public void addsavings (double amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues conval = new ContentValues();
        conval.put("amount",amount);
        conval.put("time",System.currentTimeMillis());
        db.insert("savings",null,conval);
    }


    public Cursor getAllExpense(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from expense order by id desc",null);
        return cursor;
    }

    public Cursor getAllIncome(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from income order by id desc",null);
        return cursor;
    }

    public double getTotalExpense(){
        double totalexpense = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from expense",null);
        if (cursor!=null & cursor.getCount()>0){
            while (cursor.moveToNext()){
                double amount = cursor.getDouble(1);
                totalexpense = totalexpense+amount;
            }
        }
        return totalexpense;
    }


    public double getTotalIncome() {
        double totalincome = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from income", null);
        if (cursor != null & cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                double amount = cursor.getDouble(1);
                totalincome = totalincome + amount;
            }
        }

        return totalincome;
    }

    public double getTotalsavings() {
        double totalincome = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from income", null);
        if (cursor != null & cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                double amount = cursor.getDouble(1);
                totalincome = totalincome + amount;
            }
        }

        return totalincome;
    }
}
