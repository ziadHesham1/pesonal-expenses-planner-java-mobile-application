package com.example.mma7faztyv2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class ma7fazaDatabase extends SQLiteOpenHelper {
    public static final String add_table      = "addtable";
    public static final String sub_table      = "subtable";
    public static final String record_comment = "record_comment";
    public static final String record_number = "record_number";
    public static final String record_title = "record_title";
    public static final String record_id       = "record_id";
    public static final String record_budgetid = "record_budgetid";

    public static final String max          = "max";
    public static final String remain = "remain";
    public static final String spent      = "spent";
    public static final String calc_table = "calctable";
    public static final String calcid     = "calcid";
    public static final String av_num = "av_num";


    public static final int database_version = 5;
    public static final String database_name = "recyclerDB.db";
    public static final String budget_table  = "recyclerTable";
    public static final String TAG           = "ma7fazaDatabase";

    public static final String budget_id     = "recyclerid";
    public static final String budget_date    = "recyclerdate";
    public static final String budget_time  = "recyclertime";
    public static final String budget_title = "recyclertitle";
    SQLiteDatabase readDataBase =  this.getReadableDatabase();
    SQLiteDatabase writeDatabase = this.getWritableDatabase();
    SQLiteDatabase db;
    Context contextHere;
    public static final String todaySt="today",yesterdaySt="yesterday",thismonthSt="this month",thisyearSt="this year",allSt="all budgets";
    public ma7fazaDatabase(Context context) {
        super(context, "ma7fazaDB.db", null, database_version);
        this.contextHere=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //add table
        db.execSQL(String.format("create table %s (%s INTEGER PRIMARY KEY,%s INTEGER,%s text NOT NULL , %s text , %s double );",
                add_table, record_id, record_budgetid, record_title, record_comment, record_number));
        //sub table
        db.execSQL(String.format("create table %s (%s INTEGER PRIMARY KEY,%s INTEGER,%s text NOT NULL , %s text , %s double );",
                sub_table, record_id, record_budgetid, record_title, record_comment, record_number));
        //calctable
        db.execSQL(String.format("create table %s (%s text unique not null primary key, %s double , %s double ,%s double , %s double );"
                , calc_table, record_title,                             max,         spent,      remain,     av_num));
        //recycler table
        String createDb = String.format("CREATE TABLE %s (%s TEXT unique not null primary key, %s TEXT , %s TEXT )",
                budget_table, budget_title, budget_date, budget_time);
        db.execSQL(createDb);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //insert--------------------------------------------------------------------------
    public boolean insertRecordAdd( recordsData data) {
        SQLiteDatabase database=this.getWritableDatabase();
        double num=data.getNumber();
        String com=data.getComment();
        ContentValues values=new ContentValues();
        String theAddTitle=String.format("add:%s",data.getTitle());
        int budgetid=(countRecordByTitle(theAddTitle, add_table));
        values.put(record_id,countTableRows(add_table)+1);
        values.put(record_budgetid,budgetid);
        values.put(record_title,theAddTitle);
        values.put(record_number,num);
        values.put(record_comment,com);
        long insert=database.insert(add_table,null,values);
        if (insert==-1)
        {
            Log.i(TAG, String.format("insert nothing insserted in %s : ",add_table));return false;
        }
        else
        {
            Log.i(TAG, String.format("insert item inseted in %s table the number of cloms are %s ",add_table,countTableRows(add_table)));
            return true;
        }
    }
    public boolean insertRecordSub( recordsData data) {
        SQLiteDatabase database=this.getWritableDatabase();
        double num=data.getNumber();
        String com=data.getComment();
        ContentValues values=new ContentValues();
        String theSubTitle=String.format("sub:%s",data.getTitle());
        int budgetid=(countRecordByTitle(theSubTitle, ma7fazaDatabase.sub_table));
        values.put(record_id,countTableRows(ma7fazaDatabase.sub_table)+1);
        values.put(record_budgetid,budgetid);
        values.put(record_title,theSubTitle);
        values.put(record_number,num);
        values.put(record_comment,com);
        long insert=database.insert(sub_table,null,values);
        if (insert==-1)
        {
            Log.i(TAG, String.format("insert nothing insserted in %s : ",sub_table));return false;
        }
        else
        {
            Log.i(TAG, String.format("insert item inseted in %s table the number of cloms are %s ",sub_table,countTableRows(sub_table)));
            return true;
        }
    }
    public boolean addCalc(String t, calcData cd) {
        ContentValues values=new ContentValues();
        values.put(record_title,t);
        values.put(max,cd.getMax());
        values.put(remain,cd.getRemain());
        values.put(spent,cd.getSpent());
        values.put(av_num,cd.getAv_no());

        long insert= writeDatabase.insert(calc_table, null, values);

        if (insert==-1)
        {
            return false;
        }
        else
            return true;

    }
    public boolean addbudget(budgetData budget) {
        int checkno = checkTitle(budget.getTitle());
        Log.i(TAG, String.format("addbudget: checkno :%s", checkno));
        String  newTitle;
        if (checkno>0)
            newTitle = String.format("%s_%s",budget.getTitle(),checkno);
        else newTitle=budget.getTitle();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(budget_title, newTitle);
        v.put(budget_date, budget.getDate());
        v.put(budget_time, budget.getTime());

        // inserting recordsData into db
        long result = db.insert(budget_table, null, v);
        addCalc(newTitle, budget.getThecalcData());
        insertRecordAdd(new recordsData(budget.getTitle(),budget.getMax(),"current budget"));
        return result != -1;
    }

    //get--------------------------------------------------------------------------
    public ArrayList getRecordWithTitle(String t, String table) {
        Log.i(TAG, String.format("getRecordWithTitle: table : %s  ,, title  : %s",t,table ));
        String aTitle = getRecordTitle(t, table);
        String cursortitle,cursorcomment=" ";
        int cursortheid;
        Double cursornumber;
        if (aTitle!=null)
        {
            Log.i(TAG, String.format("getRecordWithTitle: atitle :%s", aTitle));
            ArrayList<recordsData> listData;
            Cursor data1= readDataBase.rawQuery(String.format("SELECT *  FROM %s where %s = ?",
                    table,record_title), new String[]{aTitle});

//        Cursor data1=database.rawQuery("SELECT * FROM table",null);
            listData=  new ArrayList<>();

            if (data1.moveToFirst())
            {
                do
                {
                    cursornumber=data1.getDouble(data1.getColumnIndex(record_number));
                    cursorcomment=data1.getString(data1.getColumnIndex(record_comment));
                    cursortitle=data1.getString(data1.getColumnIndex(record_title));
                    cursortheid=data1.getInt(data1.getColumnIndex(record_budgetid));
                    Log.i(TAG, String.format("getRecordWithTitle: the cursor retrieved :%s",
                            cursortheid, cursortitle, cursornumber, cursorcomment));
                    listData.add(new recordsData(cursortitle,cursornumber,
                              cursorcomment));
                }
                while (data1.moveToNext());
                data1.close();
            }
            else Log.i(TAG, "getRecordWithTitle: corsur is empty");
            recordsData.printrecordDataArray(listData);
            return listData;
        }
        else
        {
            Log.i(TAG, String.format("getDataWithTitle: cant get the data>>table inserted :%s",table ));
            return null;
        }
    }
    public ArrayList get_id(String table) {
        ArrayList<Integer> listData;
        Cursor data1= readDataBase.rawQuery(String.format("SELECT * FROM %s", table),null);

//        Cursor data1=database.rawQuery("SELECT * FROM table",null);
        listData=  new ArrayList<>();
        while (data1.moveToNext())
        {
            listData.add(data1.getColumnIndex(record_id));
        }
        return listData;
    }
    public calcData getcalc(String t) {
        Log.i(TAG, "getcalc: t="+t);
        Cursor data1= readDataBase.rawQuery(String.format("SELECT * FROM %s where %s = ?", calc_table,record_title), new String[]{t});
        if (data1!=null)
        {
            calcData calcData;
            if (data1.moveToFirst())
            {
                calcData= new calcData(
                        data1.getDouble(data1.getColumnIndex(max)),
                        data1.getDouble(data1.getColumnIndex(spent)),
                        data1.getDouble(data1.getColumnIndex(remain)),
                        data1.getDouble(data1.getColumnIndex(av_num)));
                return calcData;
            }

            else
                return new calcData(0,0,0,0);
        }
        else {
            Log.i(TAG, "getcalc: cant find this id !!! returning the defualt value");
            return new calcData(0,0,0,0);}
    }
    public budgetData getbudget(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] query = new String[] {budget_id, budget_title, budget_date, budget_time};
        Cursor cursor=  db.query(budget_table,query, budget_id +"=?",new String[]{String.valueOf(id)},
                null,null,null,null);
        budgetData rd;
        if(cursor.moveToFirst())
        {
            cursor.moveToFirst();
            rd = new budgetData
                    (
                            Long.parseLong(cursor.getString(cursor.getColumnIndex(budget_id))),
                            cursor.getString(cursor.getColumnIndex(budget_title)),
                            new dateTime(cursor.getString(cursor.getColumnIndex(budget_date)),
                                    cursor.getString(cursor.getColumnIndex(budget_time))),
                            getcalc(cursor.getString(cursor.getColumnIndex(budget_title)))
                    );
            return rd;
        }

        else {
            return null;
        }
    }
    public ArrayList<budgetData> getAllbudgets() {
        ArrayList<budgetData> allbudgets = new ArrayList<>();
        String query = "SELECT * FROM " + budget_table;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                budgetData budget = new budgetData(
                        countTableRows(budget_table),
                        cursor.getString(cursor.getColumnIndex(budget_title)),
                        new dateTime(cursor.getString(cursor.getColumnIndex(budget_date)),
                                cursor.getString(cursor.getColumnIndex(budget_time))),
                        getcalc(cursor.getString(cursor.getColumnIndex(budget_title)))
                );
                allbudgets.add(budget);
            }while (cursor.moveToNext());
        }
        Collections.reverse(allbudgets);
        return allbudgets;
    }
    public ArrayList<budgetData> getAllbudgetsWithDate(String dateTime) {

        ArrayList<budgetData> allbudgets = new ArrayList<>();
        Cursor cursor= readDataBase.rawQuery(String.format("SELECT *  FROM %s where %s like ?"
                , budget_table, budget_date)
                , new String[]{"%"+dateTime+"%"});
        int counter=0;
        if(cursor.moveToFirst()){
            do{
                budgetData budget = new budgetData(
                        counter,
                        cursor.getString(cursor.getColumnIndex(budget_title)),
                        new dateTime(cursor.getString(cursor.getColumnIndex(budget_date)),
                                cursor.getString(cursor.getColumnIndex(budget_time))),
                        getcalc(cursor.getString(cursor.getColumnIndex(budget_title)))
                );
                allbudgets.add(budget);
                counter++;
            }while (cursor.moveToNext());
        }
        Collections.reverse(allbudgets);
        budgetData.recyclerArrayPrinter(TAG,dateTime,allbudgets);
        return allbudgets;
    }
    public ArrayList<budgetData> getThisDateArray(String period) {
        Log.i(TAG, "getThisDateArray: inside");
        //all
        if (period==allSt) {
            Log.i(TAG, String.format("getThisDateArray: period :%s", period));
            return getAllbudgets();
        }
        //today
        else if (period==todaySt) {
            Log.i(TAG, String.format("getThisDateArray: period :%s", period));

            return getAllbudgetsWithDate(dateTime.getCurrentDateTime().getDate());
        }
        //yesterday
        else if (period==yesterdaySt) {
            Log.i(TAG, String.format("getThisDateArray: period :%s", period));
            return getAllbudgetsWithDate(dateTime.getyesterdayDateTime().getDate());
        }
        //this month
        else if (period==thismonthSt) {
            Log.i(TAG, String.format("getThisDateArray: period :%s", period));
            return getAllbudgetsWithDate(String.format("/%s/%s",dateTime.getMonth(),dateTime.getYear()));
        }
        //this year
        else if (period==thisyearSt) {
            Log.i(TAG, String.format("getThisDateArray: period :%s", period));
            return getAllbudgetsWithDate(String.format("/%s",dateTime.getYear()));
        }
        else {
            Log.i(TAG, String.format("getThisDateArray: period :%s", period));
            Log.i(TAG, String.format("getThisDateArray: theArray is null"));
            return null;}
    }

    //update--------------------------------------------------------------------------
    public boolean updatecalc(String t, calcData calcData) {
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(max,calcData.getMax());
        values.put(remain,calcData.getRemain());
        values.put(spent,calcData.getSpent());
        values.put(av_num,calcData.getAv_no());
        database.update(calc_table,values,String.format("%s=?",record_title),new String[] {t});
        return true;

    }
    public boolean update_id(String aTitle,String tablename) {
       /* Log.i(TAG, String.format("update_id: before updating title :%s, table:%s", aTitle, tablename));
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        int titleCounter=(countRecordByTitle(aTitle, tablename));

        int idCounter=0;
        if (aTitle != null) {
            Log.i(TAG, String.format("getRecordWithTitle: atitle :%s", aTitle));
            Cursor data1 = readDataBase.rawQuery(String.format("SELECT *  FROM %s where %s = ?",
                    tablename, record_title), new String[]{aTitle});

//        Cursor data1=database.rawQuery("SELECT * FROM table",null);
            if (data1.moveToFirst()) {
                do {
                    Log.i(TAG, String.format("update_id: inside cursor idCounter:%s",idCounter));
                    values.put(record_budgetid,idCounter);
                    Log.i(TAG, String.format("update_id: updated values :%s",
                            database.update(tablename, values, String.format("%s=?",record_title), new String[]{aTitle})));
                    Log.i(TAG, String.format("update_id :%s", idCounter));
                    idCounter++;
                }
                while (data1.moveToNext()&&idCounter<=titleCounter);
                data1.close();
                Log.i(TAG, String.format("update_id: last item is  :%s", idCounter));
                return idCounter>0;
            } else {Log.i(TAG, "getRecordWithTitle: corsur is empty");return false;}
        }
        else {
            Log.i(TAG, String.format("getDataWithTitle: cant get the data>>table inserted :%s", tablename));
            return false;
        }
*/
       ArrayList<recordsData> therecordsDataArray=getRecordWithTitle(aTitle,tablename);
       recordsData.printrecordDataArray(therecordsDataArray);
       deleteTable(tablename);
       boolean checker=false;
        for (recordsData recordsData : therecordsDataArray) {
            if (tablename==add_table)checker=insertRecordAdd(recordsData);
            if (tablename==sub_table)checker=insertRecordSub(recordsData);
        }
        Toast.makeText(contextHere,String.format(" update the ids: %s",checker), Toast.LENGTH_LONG).show();
        return checker;
    }
    //delete--------------------------------------------------------------------------
    public boolean delAllRecords(String deletingTitle, String tableName) {
        String aTitle = getRecordTitle(deletingTitle, tableName);
        if (aTitle != null) {
            return deleteRows(tableName, record_title, aTitle) > 0;
        }
        else {
            Log.i(TAG, String.format("delAllRecords: cant get the data>>table inserted :%s",tableName ));
            return false;
        }
    }
    public int deleteCetrainrecord(int recordDelid, recordsData deletingData, String tableName){

                String arg[]={String.valueOf(recordDelid),deletingData.getTitle()};
                    Log.i(TAG, String.format("deleteCetrainrecord: trying to delete id:%s ,title:%s "
                            ,arg[0],arg[1]));
        Cursor data1= readDataBase.rawQuery(String.format("SELECT *  FROM %s where %s = ? and %s = ?",
                tableName,record_budgetid,record_title),arg);
        if (data1.moveToFirst())
        {
            Log.i(TAG, String.format("deleteCetrainrecord:  from query:trying to delete  :%s",
                    data1.getDouble(data1.getColumnIndex(record_number)),
                    data1.getString(data1.getColumnIndex(record_comment))));
            data1.close();
        }
        else Log.i(TAG, "getRecordWithTitle: corsur is empty");

        int result= writeDatabase.delete(tableName,String.format
                        ("%s=? and %s=?",
                                record_budgetid,record_title),arg);
                Log.i(TAG, String.format("delRecordByID: the items deleted :%s", result));
                if (result>0) update_id(deletingData.getTitle(),tableName);
                return result;
    }
    public void deleteAllBudgets(){
        deleteTable(budget_table);
        deleteTable(calc_table);
        deleteTable(add_table);
        deleteTable(sub_table);
    }
    public void delbudgetbytitle(String t) {
        deleteRows(budget_table,budget_title,t);
        deleteRows(calc_table,record_title,t);
        deleteRows(add_table,record_title,t);
        deleteRows(sub_table,record_title,t);
    }
    public int deleteTable(String deletetable) {
        int result= writeDatabase.delete( deletetable,null,null);
        return result;
    }
    public int deleteRows(String deletetable,String deleteColumn,String deleteTitle) {
            String arg[]={String.valueOf(deleteTitle)};
            int result= writeDatabase.delete(deletetable,String.format("%s=?", deleteColumn),arg);
        return result;

    }
    //count--------------------------------------------------------------------------
    public int countTableRows(String table) {
        Cursor data1= readDataBase.rawQuery(String.format("SELECT * FROM %s",table ),null);
        int c=data1.getCount();
        return c;
    }
    public int countDefaultTitle() {
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor data1 = db.rawQuery(String.format("SELECT * FROM %s where %s like ?", budget_table, budget_title), new String[]{"%new budget%"});
        int c = data1.getCount();
        return c ;
    }
    public int checkTitle(String t) {
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor data1 = db.rawQuery(String.format("SELECT * FROM %s where %s = ?", budget_table, budget_title), new String[]{t});
        int c = data1.getCount();
        return c ;
    }
    public int countRecordByTitle(String t, String table) {
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor data1 = db.rawQuery(String.format("SELECT * FROM %s where %s = ?",table,record_title), new String[]{t});
        int c = data1.getCount();
        return c ;
    }
    ////////////////////////////////////
    private String getRecordTitle(String t, String table) {
        String aTitle;
        if (table == add_table) aTitle = String.format("add:%s", t);
        else if (table == sub_table) aTitle = String.format("sub:%s", t);
        else aTitle = null;
        return aTitle;
    }

}
