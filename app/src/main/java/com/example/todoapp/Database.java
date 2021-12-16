package com.example.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "todo";
    private static final String TABLE_TASK= "tasks";

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION= "description";
    private static final String KEY_CATEGORY = "category";

    public Database(@Nullable Context context) {
       super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

            String CREATE_ACCOUNTS_TABLE = "CREATE TABLE " + TABLE_TASK + "("
                       + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
                       + KEY_DESCRIPTION + " TEXT,"
                       + KEY_CATEGORY + " TEXT" + ");";
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

    void deleteAllTasks() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_TASK;
        database.execSQL(query);
    }
}
