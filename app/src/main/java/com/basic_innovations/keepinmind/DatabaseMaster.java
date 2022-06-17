package com.basic_innovations.keepinmind;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DatabaseMaster extends SQLiteOpenHelper {
    static String dbName = "KeepIn.sqlite";
    String dbPath = "";
    SQLiteDatabase mainDB;

    public DatabaseMaster(Context context) {
        super(context, dbName, null, 1);
        dbPath = "/data/data/"+ context.getOpPackageName() + "/databases"; // "dbName" add it while creating path
    }

    // to keep multiple database calls in order...
    public static synchronized DatabaseMaster getDB(Context context){
          return new DatabaseMaster(context);
    }

    //to check database exist in the dbpath or not..!!
    public boolean Check(){
        SQLiteDatabase database;
        try {
            database = SQLiteDatabase.openDatabase(dbPath + "/" + dbName, null, SQLiteDatabase.OPEN_READWRITE);
        }catch (Exception e){
            database = null;
        }

        if(database  != null){
            return true;
        }else {
            return false;
        }
    }

    public void createDB(Context context){
        this.getReadableDatabase();
        this.close();
        try {
            InputStream fin = context.getAssets().open(dbName);
            String outputPath = dbPath + "/" + dbName;
            FileOutputStream fos = new FileOutputStream(outputPath);
            byte[] bytes = new byte[1024];
            int lenght;
            while ((lenght = fin.read(bytes)) > 0){
                fos.write(bytes, 0, lenght);
            }
            fin.close();
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openDB(){
        mainDB = SQLiteDatabase.openDatabase(dbPath + "/" + dbName,null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
