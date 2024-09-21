package com.example.gymprogresstracker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DatabaseManager {
    Context context;
    public static final String DATABASE_NAME = "WorkoutProgress.db";
    public static final int DATABASE_VERSION = 1;
    DatabaseHelper databaseHelper;
    SQLiteDatabase database;
    /*
    TABLE SCHEMA
    D<i>{
        ExerciseID INT
    };

    Ex<i>{
        Date TEXT,
        SET_NO INT,
        WEIGHT FLOAT,
        REPS INT
    };
    WEIGHT{
        DATE TEXT
        MORNING FLOAT,
        NIGHT FLOAT
    }
     */
    public class DatabaseHelper extends SQLiteOpenHelper{

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }


    public DatabaseManager(Context context){
        this.context = context;
        init();
    }

    public void init(){
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        //addTables();
        //showTables();
        //CLEAR();
        //printTable();
        getExeSetTables();
    }
    public void getExTables(){

    }
    private void addTables(){
        for(int i = 0;i < 7;i++){
            String CREATE_TABLE_QRY = "CREATE TABLE IF NOT EXISTS D"+i+" (WORKOUT_ID INT);";
            database.execSQL(CREATE_TABLE_QRY);
        }
    }
    public void addRecord(int day, int exercise_id){
        String QUERY = "INSERT INTO D" + day + " VALUES("+exercise_id+")";
        database.execSQL(QUERY);
    }
    public void addRecord(int day, List<Integer> ids){
        for (Integer exercise_id:ids) {
            addRecord(day, exercise_id);
            addExerciseTable(exercise_id);
        }
        //getRecord(day);
    }
    public List<String> showTables(){
        Cursor c = database.rawQuery("SELECT name FROM sqlite_master WHERE type='table';", null);
        c.moveToFirst();
        //Log.e("e", "=======================TABLES");
        List<String> names = new ArrayList<>();
        while (c.moveToNext()){
            //Log.e(this.getClass().getName(), c.getString(0));
            if(c.getString(0).startsWith("EXC")){names.add(c.getString(0));}
        }
        //Log.e("e", "=======================TABLES-END");
        c.close();
        return names;
    }
    public List<Integer> getRecord(int id){
        List<Integer> res = new ArrayList<>();
        Cursor c = database.rawQuery("SELECT * FROM D"+id+";", null);
        if(c.getCount()==0){return res;}
        c.moveToFirst();
        do{
            res.add(c.getInt(0));
        }while (c.moveToNext());
        Log.e(this.getClass().getName(), "D" + id + "--" + res.toString());
        c.close();
        return res;
    }

    public void clearTable(int id){
        String QRY = "DELETE FROM D"+id;
        database.execSQL(QRY);
    }
    public void CLEAR(){
        for(int i = 0;i < 7;i++){
            String CREATE_TABLE_QRY = "DELETE FROM D"+i;
            database.execSQL(CREATE_TABLE_QRY);
        }
    }
    public void printTable(){
        for(int i = 0;i < 7;i++){
            //Log(" EEEEEE  + " + i);
            getRecord(i);
        }
    }
    public void removeRecord(int tableID, int contentID){
        String QRY = "DELETE FROM D" + tableID + " WHERE WORKOUT_ID = " + contentID;
        //Log.e("QUERYYYYYYYYYYYY" , QRY);
        database.execSQL(QRY);
    }
    public void remove_duplicates(int table_id){
        String QRY = "SELECT * FROM D"+table_id;
        Cursor c = database.rawQuery(QRY,null);
        c.moveToFirst();
        Set<Integer> ids = new HashSet<>();
        do{
            ids.add(c.getInt(0));
        }while (c.moveToNext());
        clearTable(table_id);
        //Log(new ArrayList<>(ids).toString());
        addRecord(table_id, new ArrayList<>(ids));
        c.close();
    }
    public void addExerciseTable(int id){
        String QRY = "CREATE TABLE IF NOT EXISTS EXC"+String.valueOf(id)+" (EX_DATE TEXT, SET_NO INT, WEIGHT FLOAT, REPS INT);";
        database.execSQL(QRY);
        Log(QRY);
    }
    public void addSetInExercise(int id, String date, int set_id, float weight, int reps){
        String QRY = "INSERT INTO EXC"+id + " VALUES('"+date+"',"+set_id+","+weight+","+reps+")";
        database.execSQL(QRY);
        Log(QRY);
    }
    public int getLastSetID(int ex_id, String date){
        int res = 0;
        String QRY = "SELECT SET_NO FROM EXC"+ex_id + " WHERE EX_DATE = '" + date+"' ORDER BY SET_NO DESC;";
        Cursor cursor = database.rawQuery(QRY,null);
        cursor.moveToFirst();
        if(cursor.getCount() == 0){return 0;}
        res = cursor.getInt(0);
        cursor.close();
        return res;
    }
    public void getTable(int id){
        String QRY = "SELECT * FROM EXC"+id+";";
        Cursor cursor = database.rawQuery(QRY,null);
        cursor.moveToFirst();
        do{
            String res = "";
            for(int i = 0;i< cursor.getColumnCount();i++){
                res += cursor.getString(i) + " | ";

            }
            Log.e("ERRRRRRR", res);
        }while( (cursor.moveToNext()));
        cursor.close();
    }
    public List<List<String>> getSetList(int id){
        printSetTable(id);
        List<List<String>> res = new ArrayList<>();
        String QRY = "SELECT * FROM EXC"+id+";";
        //Log.e("EEEEEEEE", QRY);
        Cursor cursor = database.rawQuery(QRY,null);
        if(cursor.getCount() == 0){return res;}
        cursor.moveToFirst();
        do{
            List<String> temp = (new ArrayList<>());
            for(int i = 0;i< cursor.getColumnCount();i++){
                temp.add(cursor.getString(i));
            }
            //Log.e("BRUHHHHHHHHH", temp.toString());
            res.add(temp);
        }while( (cursor.moveToNext()));
        cursor.close();
        return res;
    }
    public void printSetTable(int id){
        //Log.e("BRUHHHHHHHHH", String.valueOf(id));
        List<List<String>> res = new ArrayList<>();
        String QRY = "SELECT * FROM EXC"+id+";";
        Cursor cursor = database.rawQuery(QRY,null);
        cursor.moveToFirst();
        if(cursor.getCount() == 0){
            return;
        }
        do{
            List<String> temp = (new ArrayList<>());
            for(int i = 0;i< cursor.getColumnCount();i++){
                temp.add(cursor.getString(i));
            }
            //Log.e("BRUHHHHHHHHH", temp.toString());
            res.add(temp);
        }while( (cursor.moveToNext()));

        cursor.close();
    }
    public void getExeSetTables(){
        Cursor c = database.rawQuery("SELECT name FROM sqlite_master WHERE type='table';", null);
        c.moveToFirst();
        List<String> tab_names = new ArrayList<>();
        //Log.e("e", "=======================TABLES");
        do{
            if(c.getString(0).startsWith("EXC")){tab_names.add(c.getString(0));}
        }while (c.moveToNext());
        //Log.e("e", "=======================TABLES-END");
        Log(tab_names.toString());
        c.close();
    }
    public void clearExTable(String tabName){
        String QRY = "DELETE FROM "+tabName;
        database.execSQL(QRY);
    }
    public void removeExerciseSet(int exId, String date, int setID){
        String QRY = "DELETE FROM EXC"+exId+" WHERE EX_DATE = '" + date +"' AND SET_NO = " + setID;

        database.execSQL(QRY);
    }
    public void makeWeightTable(){
        String QRY = "CREATE TABLE IF NOT EXISTS WEIGHT(REC_DATE TEXT, MORNING FLOAT, NIGHT FLOAT);";
        database.execSQL(QRY);
    }
    public void addWeightRecord(String date, float weight, int timeOfDay){
        List<List<String>> record = getWeightRecord(date);
        String wght = ((timeOfDay==0)?weight:0) + ", " + ((timeOfDay==1)?weight:0);
        if(record.isEmpty()){
            String QRY = "INSERT INTO WEIGHT VALUES('"+date+"'," + wght + ");";
            database.execSQL(QRY);
        }
        else{
            String QRY = "UPDATE WEIGHT SET " + ((timeOfDay==0)?"MORNING":"NIGHT") + " = " + weight + " WHERE REC_DATE = '" + date + "';";
            database.execSQL(QRY);
        }
    }
    public List<List<String>> getWeightRecord(String date){
        List<List<String>> res = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM  WEIGHT WHERE REC_DATE = '" + date + "';", null);
        cursor.moveToFirst();
        if(cursor.getCount() == 0){
            return res;
        }
        do{
            List<String> temp = new ArrayList<>();
            for(int i = 0;i< cursor.getColumnCount();i++){
                temp.add(cursor.getString(i));
            }
            res.add(temp);
        }while(cursor.moveToNext());
        cursor.close();
        for(List<String> temp:res){
            Log(temp.toString());
        }
        return res;
    }
    public List<List<String>> getWeightTable(){
        List<List<String>> res = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM  WEIGHT;", null);
        cursor.moveToFirst();
        if(cursor.getCount() == 0){
            return res;
        }
        do{
            List<String> temp = new ArrayList<>();
            for(int i = 0;i< cursor.getColumnCount();i++){
                temp.add(cursor.getString(i));
            }
            res.add(temp);
        }while(cursor.moveToNext());
        cursor.close();
        for(List<String> temp:res){
            Log(temp.toString());
        }
        return res;
    }
    public void renameDatabase(String Old, String New){
        String QRY = "ALTER TABLE " + Old + " RENAME TO " + New + ";";
        database.execSQL(QRY);
        Log.d("RENAMED", "RENAMED "  + Old + " TO " + New);
    }


    // NEW STUFF HERE

    public List<List<String>> getRecords(String query, String[] args){
        List<List<String>> res = new ArrayList<>();
        Cursor cursor = database.rawQuery(query,args);

        cursor.moveToFirst();
        if(cursor.getCount() == 0){return res;}

        do {
            List<String> record = new ArrayList<>();
            int cols = cursor.getColumnCount();
            for(int i = 0; i < cols; i++){
                record.add(cursor.getString(i));
            }
            res.add(record);
        }
        while (cursor.moveToNext());
        cursor.close();
        return res;
    }


    public void Log(String text){
        Log.e(this.getClass().getName(), text);
    }
}
