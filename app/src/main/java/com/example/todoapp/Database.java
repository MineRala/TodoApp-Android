package com.example.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.ColorSpace;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "todo";
    private static final String TABLE_TASK= "tasks";

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION= "description";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_DONE = "done";


    Database(@Nullable Context context) {
       super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

            String CREATE_ACCOUNTS_TABLE = "CREATE TABLE " + TABLE_TASK + "("
                       + KEY_ID + " INTEGER PRIMARY KEY,"
                       + KEY_TITLE + " TEXT,"
                       + KEY_DESCRIPTION + " TEXT,"
                       + KEY_CATEGORY + " TEXT,"
                       + KEY_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP, "
                       + KEY_DONE + " INTEGER" + ");";
           db.execSQL(CREATE_ACCOUNTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(db);
    }

    void addTasks(String title, String description, String category) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, title);
        contentValues.put(KEY_DESCRIPTION, description);
        contentValues.put(KEY_CATEGORY, category);
        contentValues.put(KEY_TIMESTAMP, "");
        contentValues.put(KEY_DONE, 0);


        long resultValue = database.insert(TABLE_TASK, null, contentValues);

        if (resultValue == -1) {
            Toast.makeText(context,"Data not added!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context,"Data added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_TASK;
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = null;
        if (database!= null) {
            cursor = database.rawQuery(query, null);
        }
        return cursor;
    }

    void deleteAllData() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_TASK;
        database.execSQL(query);
    }

    public void deleteOneRow(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_TASK, "id=?", new String[]{id});
        if(result == -1){
            Toast.makeText(context, "Failed To Delete!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    void updateTasks(String title, String description, String category, String id) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, title);
        contentValues.put(KEY_DESCRIPTION, description);
        contentValues.put(KEY_CATEGORY, category);

        long result = database.update(TABLE_TASK,contentValues,"id=?", new String[]{id});
        if (result == -1) {
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context,"Done",Toast.LENGTH_SHORT).show();
        }
    }


    void taskDone(String id, int isDone) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_DONE, isDone);
        database.update(TABLE_TASK,contentValues,"id=?", new String[]{id});
        database.close();
    }

    ArrayList<Model> getIsNotDoneTasks() {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] rows = {KEY_ID,KEY_TITLE,KEY_DESCRIPTION,KEY_CATEGORY,KEY_TIMESTAMP,KEY_DONE};
        Cursor cursor = database.query(TABLE_TASK,rows,null,null,null,null,null);
        ArrayList<Model> tempList = new ArrayList<>();

        while (cursor.moveToNext()){
            tempList.add(new Model(cursor.getString(0),cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5)));
        }
        cursor.close();
        database.close();

        ArrayList<Model> returnTaskList = new ArrayList<>();

        for (int i = 0; i < tempList.size(); i++) {
            if(tempList.get(i).getIsDone() == 0) {
                returnTaskList.add(tempList.get(i));
            }
        }

        return returnTaskList;
    }

    ArrayList<Model> getIsDoneTasks() {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] rows = {KEY_ID,KEY_TITLE,KEY_DESCRIPTION,KEY_CATEGORY,KEY_TIMESTAMP,KEY_DONE};
        Cursor cursor = database.query(TABLE_TASK,rows,null,null,null,null,null);
        ArrayList<Model> tempList = new ArrayList<>();

        while (cursor.moveToNext()){
            tempList.add(new Model(cursor.getString(0),cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5)));
        }
        cursor.close();
        database.close();

        ArrayList<Model> returnTaskList = new ArrayList<>();

        for (int i = 0; i < tempList.size(); i++) {
            if(tempList.get(i).getIsDone() == 1) {
                returnTaskList.add(tempList.get(i));
            }
        }

        return returnTaskList;
    }
}