package com.devlion.catchcall;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbManager extends SQLiteOpenHelper {

    public final static String DB_NAME = "MY.DB";
    public final static int VERSION = 3;
    public final static String TABLE_NAME = "PHONE_BOOK";


    private static DbManager dbManager;
    private SQLiteDatabase database;

//    protected SQLiteDatabase sampleDB;

    // 싱글톤 적용
    public static DbManager getInstance(Context context){
        if(dbManager != null){
            dbManager = new DbManager(context, DB_NAME, null ,VERSION);
        }

        return dbManager;
    }

    protected DbManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

        getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        create(TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public SQLiteDatabase open(){
        return database;
    }


    public void create(String tableName){
        database.execSQL("CREATE TABLE IF NOT EXISTS " + tableName
                + " (name VARCHAR(20), phone VARCHAR(20) );");

        database.close();

    }

    public void delete(String tableName){
        database.execSQL("DELETE FROM " + tableName  );

        database.close();
    }



    public long insert(ContentValues contentValues){

        if (database == null){
            Log.d("CALL_TEST", "null!");
            return 0;
        }
        return database.insert(TABLE_NAME, null, contentValues);
    }




    public List<Map<String, String>> select() {

        List<Map<String, String>> retMapList = new ArrayList<Map<String, String>>();


        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));

            Map<String, String> retMap = new HashMap<String, String>();

            retMap.put("name", name);
            retMap.put("name", phone);

            retMapList.add(retMap);
        }


        return retMapList;
    }

}
