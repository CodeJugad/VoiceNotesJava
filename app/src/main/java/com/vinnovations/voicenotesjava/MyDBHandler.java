package com.vinnovations.voicenotesjava;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper {


    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Params.TABLE_NAME + " (" +
                    Params.KEY_ID + " INTEGER PRIMARY KEY," +
                    Params.KEY_TOPIC + " TEXT," +
                    Params.KEY_DESC + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Params.TABLE_NAME;

    public MyDBHandler(Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addNotes(NotesEntity notesEntity){
        // this is one time and make many apps

        // Gets the data repository in write mode
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Params.KEY_TOPIC, notesEntity.getTopicName());
        values.put(Params.KEY_DESC, notesEntity.getTopicDesc());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(Params.TABLE_NAME, null, values);
        db.close();
    }

    public List<NotesEntity> getAllNotes(){
        List<NotesEntity> notesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Generate the query to read from the database
        String select = "SELECT * FROM " + Params.TABLE_NAME;
        Cursor cursor = db.rawQuery(select, null);

        //Loop through now
        if(cursor.moveToFirst()){
            do{
                NotesEntity notesEntity = new NotesEntity();
                notesEntity.setId(Integer.parseInt(cursor.getString(0)));
                notesEntity.setTopicName(cursor.getString(1));
                notesEntity.setTopicDesc(cursor.getString(2));
                notesList.add(notesEntity);
            }while(cursor.moveToNext());
        }
        return notesList;
    }
}
