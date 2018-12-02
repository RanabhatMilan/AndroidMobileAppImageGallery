package com.example.db.imagegallery;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
/* import android.graphics.Bitmap;
import android.graphics.BitmapFactory; */


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by user on 9/29/2018.
 */

class DatabaseHelper extends SQLiteOpenHelper {

    public  static final String DATABASE_NAME = "ImageGallery.db";
    public static final String TABLE_NAME = "images_table";
   // public  static  final String col1 = "id";
    public static final String col2 = "image";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
   }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+" (id integer primary key autoincrement,image blob not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_NAME);
        onCreate(db);
    }

    public boolean storeImage(String x){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            FileInputStream fs = new FileInputStream(x);
            byte[] imgbyte = new byte[fs.available()];
            fs.read(imgbyte);
            ContentValues cv = new ContentValues();
            cv.put(col2,imgbyte);
            db.insert(TABLE_NAME,null,cv);
            fs.close();
            return true;
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }



 /*   public Bitmap viewAllImages() {
        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap bt = null;
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        if (res.moveToNext()) {
            byte[] imag = res.getBlob(1);
            bt = BitmapFactory.decodeByteArray(imag, 0, imag.length);
        }
        return bt;
    } */

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select id from "+TABLE_NAME,null);
        return cursor;
    }

    public ArrayList<Person> getLi(){
        Person person = null;
        ArrayList<Person> personlist = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE_NAME,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            person =  new Person(cursor.getBlob(1));
            personlist.add(person);
            cursor.moveToNext();
        }
        return personlist;
    }

    public void deleteImage(int id){
        SQLiteDatabase db = this.getWritableDatabase();
       // return db.delete(TABLE_NAME,"id = ?",new String[]{id.});
        String sql = "delete from "+TABLE_NAME+" where id = ?";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1,(double)id);
        statement.execute();
        db.close();
    }

}
