package com.example.aquaadventure;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(Context context) {
        super(context, "Mydb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table Subject(Sub_code Text primary key,Sub_name Text,No_of_credits Text,Max_marks Text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Subject");

    }

    public Boolean InsertU(String code, String name , String credits,String Max_marks){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put("Sub_code",code);
        contentValues.put("Sub_name",name);
        contentValues.put("No_of_credits",credits);
        contentValues.put("Max_marks",Max_marks);

        long result=db.insert("Subject",null,contentValues);
        if(result==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor Display(){
        SQLiteDatabase db=getWritableDatabase();
        Cursor cur= db.rawQuery("Select * from Subject",null);

        return cur;
    }

    public Boolean deleteU(String code){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cur= db.rawQuery("Select * from Subject where Sub_code=?",new String[]{code});


        if(cur.getCount()>0){
            long result=db.delete("Subject","Sub_code=?",new String[]{code});
            if(result==-1){
                return false;
            }
            else{
                return true;
            }
        }
        else{
            return false;
        }


    }


}