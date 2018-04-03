package com.ajgarcia.book_student;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "BookData.db";
    public static final String TABLE_STUDENT = "student_table";
    public static final String COL_1 = "STD_ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "COURSE";
    public static final String COL_4 = "SEX";
    public static final String COL_5 = "SEM";
    public static final String TABLE_BOOK = "book_table";
    public static final String COL_6 = "BOOK_ID";
    public static final String COL_7 = "NAME";
    public static final String COL_8 = "AUTHOR";
    public static final String COL_9 = "PUBLISHED_DATE";
    public static final String TABLE_BORROWED = "borrow_table";
    public static final String COL_10 = "SSTD";
    public static final String COL_11 = "STD_NAME";
    public static final String COL_12 = "SBK";
    public static final String COL_13 = "BK_NAME";
    public static final String COL_14 = "BOR_DATE";
    public static final String COL_15 = "RET_DATE";
    public static final String COL_16 = "REMARKS";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_STUDENT + " (STD_ID INTEGER PRIMARY KEY NOT NULL,NAME STRING NOT NULL,COURSE STRING NOT NULL,SEX STRING NOT NULL,SEM STRING NOT NULL)");
        db.execSQL("create table " + TABLE_BOOK + " (BOOK_ID INTEGER PRIMARY KEY NOT NULL,NAME STRING NOT NULL,AUTHOR STRING NOT NULL,PUBLISHED_DATE INTEGER NOT NULL)");
        db.execSQL("create table " + TABLE_BORROWED + " (SSTD INTEGER NOT NULL,STD_NAME STRING NOT NULL,SBK INTEGER NOT NULL,BK_NAME STRING NOT NULL,BOR_DATE STRING NOT NULL,RET_DATE STRING NOT NULL,REMARKS STRING NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BORROWED);
        onCreate(db);
    }
////---------------------------------------------------------------------------------------------------------------------------------------
public boolean insertBorrowed(String stid, String stdname, String bkid, String bkname, String bordate, String retdate, String remarks) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put(COL_10,stid);
    contentValues.put(COL_11,stdname);
    contentValues.put(COL_12,bkid);
    contentValues.put(COL_13,bkname);
    contentValues.put(COL_14,bordate);
    contentValues.put(COL_15,retdate);
    contentValues.put(COL_16,remarks);
    long result = db.insert(TABLE_BORROWED,null ,contentValues);
    if(result == -1)
        return false;
    else
        return true;
}


    public boolean insertStudent(String std_id, String name, String course, String sex, String sem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, std_id);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, course);
        contentValues.put(COL_4, sex);
        contentValues.put(COL_5, sem);

        long result = db.insert(TABLE_STUDENT, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertBook(String book_id, String name, String author, String pub_date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_6, book_id);
        contentValues.put(COL_7, name);
        contentValues.put(COL_8, author);
        contentValues.put(COL_9, pub_date);
        long result = db.insert(TABLE_BOOK, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

///-------------------------------------------------------------------------------------------------------------------------------------------
    public Cursor viewStudent() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_STUDENT, null);
        return res;
    }

    public Cursor viewBook() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_BOOK, null);
        return res;
    }

    public Cursor viewBorrowed() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_BORROWED, null);
        return res;
    }

    public Cursor viewSTname(String studeID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor dec = db.rawQuery("select NAME from "+TABLE_STUDENT+" where STD_ID = "+studeID, null);
        return dec;
    }

    public Cursor viewBKname(String bkID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor dec2 = db.rawQuery("select NAME from "+TABLE_BOOK+" where BOOK_ID = "+bkID, null);
        return dec2;
    }




    //-----------------------------------------------------------------------------------------------------------------------------------
    public boolean updateStudent(String std_id, String name, String course, String sex, String sem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, std_id);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, course);
        contentValues.put(COL_4, sex);
        contentValues.put(COL_5, sem);

        db.update(TABLE_STUDENT, contentValues, "std_id = ?", new String[]{std_id});
        return true;
    }

    public boolean updateBook(String book_id, String name, String author, String pub_date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_6, book_id);
        contentValues.put(COL_7, name);
        contentValues.put(COL_8, author);
        contentValues.put(COL_9, pub_date);
        db.update(TABLE_BOOK, contentValues, "book_id = ?", new String[]{book_id});
        return true;
    }
//----------------------------------------------------------------------------------------------------------------------------------------
    public Integer deleteStudent(String std_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_STUDENT, "std_id = ?", new String[]{std_id});
    }

    public Integer deleteBook(String book_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_BOOK, "book_id = ?", new String[]{book_id});
    }

    public List<String> student_id(){
        List<String> userlist=new ArrayList<>();
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor stud_cursor=db.rawQuery("select STD_ID from student_table",null);
        if(stud_cursor.moveToFirst())
        {
            do{
                userlist.add(stud_cursor.getString(0));
            }while (stud_cursor.moveToNext());
        }
        return userlist;
    }
    public List<String> book_id(){
        List<String> userlist=new ArrayList<>();
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor book_cursor=db.rawQuery("select BOOK_ID from book_table",null);
        if(book_cursor.moveToFirst())
        {
            do{
                userlist.add(book_cursor.getString(0));
            }while (book_cursor.moveToNext());
        }
        return userlist;
    }


}


