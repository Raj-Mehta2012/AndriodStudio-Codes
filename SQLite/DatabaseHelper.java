package com.example.datastorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="database_name";
    private static final String TB_NAME="table_name";

    DatabaseHelper(Context context)
    {
        super(context,DB_NAME,null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        //Creating Table
        String createTable= "create table "+ TB_NAME + "(id INTEGER PRIMARY KEY, txt TEXT)" ;
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //delete (drop) older table if exists
        db.execSQL("DROP TABLE IF EXISTS "+ TB_NAME);
        onCreate(db);
    }

    public boolean addText(String text){
        //writable db
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        //get values
        ContentValues contentValues=new ContentValues();
        contentValues.put("txt",text);

        //add val to db
        sqLiteDatabase.insert(TB_NAME,null,contentValues);
        return true;
    }

    public ArrayList getAllText(){

        //readable db
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();

        ArrayList<String> arrayList=new ArrayList<String>();

        /*Cursors are what contain the result set of a query made against a database in Android.
        The Cursor class has an API that allows an app to read the columns that were returned from
        the query as well as iterate over the rows of the result set*/

        //Create Cursor
        Cursor cursor=sqLiteDatabase.rawQuery("select * from " +TB_NAME,null);

        cursor.moveToFirst();
        /*moveToFirst() does two things:
        it allows you to test whether the query returned an empty set (by testing the return value)
        and it moves the cursor to the first result (when the set is not empty)*/

        while (!cursor.isAfterLast()){
            arrayList.add(cursor.getString(cursor.getColumnIndex("txt")));
            cursor.moveToNext();
        }

        return arrayList;
    }
}

