package com.example.proiectandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String databaseName = "notite.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "notite.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("create Table users(id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, password TEXT)");
            db.execSQL("create Table notes(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content LONGTEXT)");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists users");
        db.execSQL("drop Table if exists notes");
        onCreate(db);
    }

    public Boolean insertNoteData(String title, String content) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("title", title);
        contentValues.put("content", content);

        long result = MyDatabase.insert("notes", null, contentValues);

        return result != -1;
    }

    public ArrayList<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        try (Cursor cursor = db.rawQuery("SELECT * from notes", null) )
        {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int titleIndex = cursor.getColumnIndex("title");
                    int contentIndex = cursor.getColumnIndex("content");

                    // Check if column index is valid
                    if (titleIndex != -1 && contentIndex != -1) {
                        String title = cursor.getString(titleIndex);
                        String content = cursor.getString(contentIndex);
                        // Create Note object and add to the list
                        Note note = new Note(title, content);
                        notes.add(note);
                    } else {
                        Log.e("DatabaseHelper", "Invalid column index");
                    }
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        return notes;
    }

    public Boolean insertData(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = MyDatabase.insert("users", null, contentValues);

        return result != -1;
    }

    public Boolean checkEmail(String email) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * from users where email = ?", new String[]{email});

        return cursor.getCount() > 0;
    }

    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * from users where email = ? and password = ?", new String[]{email, password});

        return cursor.getCount() > 0;
    }

    public int getUserId(String email, String password) {
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        int userId = -1; // Default value if user ID is not found

        Cursor cursor = MyDatabase.rawQuery("SELECT id FROM users WHERE email = ? AND password = ?", new String[]{email, password});

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("id");
            if (columnIndex != -1) {
                userId = cursor.getInt(columnIndex);
            }
        }

        cursor.close();
        return userId;
    }
}
