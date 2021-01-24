package com.thegamechanger.Installindianapp.LocalDataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.thegamechanger.Installindianapp.DataModal.AppDetail;
import com.thegamechanger.Installindianapp.DataModal.AppType;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    SQLiteDatabase database;
    String TAG = "DBHelper";

    public DBHelper(Context context) {
        super(context, DBConfig.DB_NAME, null, DBConfig.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String app_list = "CREATE TABLE "+DBConfig.TABLE_APP_DETAIL+"("+DBConfig.KEY_TABLE_APP_DETAIL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                DBConfig.KEY_NAME+ " TEXT, "+
                DBConfig.KEY_PACKAGE_NAME+ " TEXT, "+
                DBConfig.KEY_ICON+ " TEXT, "+
                DBConfig.KEY_TYPE_NAME+ " TEXT, "+
                DBConfig.KEY_TYPE_ID+ " TEXT, "+
                DBConfig.KEY_IS_ACTIVE+ " BOOLEAN " + ")";

        db.execSQL(app_list);
        Log.e(TAG,"app_list:"+app_list);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ DBConfig.TABLE_APP_DETAIL);
        onCreate(db);
    }

    public void openReadable(){
        if (database!=null&&database.isOpen()){}
        else {
            database = this.getReadableDatabase();
        }
    }

    public void openWritable(){
        if (database!=null&&database.isOpen()){}
        else {
            database = this.getWritableDatabase();
        }
    }

    public void closeDatabase(){
        database.close();
    }


    //--------------Start app_list

    public long AppDetailCount(){
        openReadable();
        long count = DatabaseUtils.queryNumEntries(database, DBConfig.TABLE_APP_DETAIL);
        closeDatabase();
        return count;
    }

    public void SetAppDetailIsActive(boolean isActive){
        openWritable();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBConfig.KEY_IS_ACTIVE,isActive);
        database.update(DBConfig.TABLE_APP_DETAIL,contentValues,"",new String[]{});
        closeDatabase();
    }

    public AppDetail IsAppDetailExist(AppDetail object){
        int table_id = 0;
        openReadable();
        Cursor cursor = database.query(
                DBConfig.TABLE_APP_DETAIL,
                new String[]{DBConfig.KEY_TABLE_APP_DETAIL_ID},
                DBConfig.KEY_PACKAGE_NAME + "=?",
                new String[]{object.getPackage_name()},
                null,
                null,
                null
        );

        if (null != cursor && cursor.moveToFirst())
            table_id = cursor.getInt(cursor.getColumnIndex(DBConfig.KEY_TABLE_APP_DETAIL_ID));

        cursor.close();

        object.setTable_id(table_id);
        closeDatabase();

        return object;
    }

    public void InsertUpdateAppDetailList(List<AppDetail> objects){
        SetAppDetailIsActive(false);
        for (AppDetail object : objects){
            InsertUpdateAppDetail(IsAppDetailExist(object));
        }
    }

    public void InsertUpdateAppDetail(AppDetail object){
        openWritable();
        ContentValues values = new ContentValues();
        values.put(DBConfig.KEY_NAME,object.getName());
        values.put(DBConfig.KEY_PACKAGE_NAME,object.getPackage_name());
        values.put(DBConfig.KEY_ICON,object.getIcon());
        values.put(DBConfig.KEY_TYPE_NAME,object.getType_name());
        values.put(DBConfig.KEY_TYPE_ID,object.getType_id());
        values.put(DBConfig.KEY_IS_ACTIVE,true);

        int table_id = object.getTable_id();
        if (table_id != 0){
            database.update(DBConfig.TABLE_APP_DETAIL,values,DBConfig.KEY_TABLE_APP_DETAIL_ID + "=?",new String[]{"" + table_id});
        }else {
            database.insert(DBConfig.TABLE_APP_DETAIL,null,values);
        }

        closeDatabase();
    }

    public List<AppDetail> GetAppDetail(String type_id){
        List<AppDetail> Objects = new ArrayList<>();
        openReadable();
        Cursor cursor;
        if (type_id.equalsIgnoreCase("0")){
            cursor = database.query(
                    DBConfig.TABLE_APP_DETAIL,
                    null,
                    DBConfig.KEY_IS_ACTIVE + "=?",
                    new String[]{"1"},
                    null,
                    null,
                    null
            );
        }else {
            cursor = database.query(
                    DBConfig.TABLE_APP_DETAIL,
                    null,
                    DBConfig.KEY_IS_ACTIVE + "=? AND "+DBConfig.KEY_TYPE_ID+"=?",
                    new String[]{"1",type_id},
                    null,
                    null,
                    null
            );
        }

        if (null != cursor && cursor.moveToFirst()){
            do {
                AppDetail object = new AppDetail();
                object.setTable_id(cursor.getInt(cursor.getColumnIndex(DBConfig.KEY_TABLE_APP_DETAIL_ID)));
                object.setName(cursor.getString(cursor.getColumnIndex(DBConfig.KEY_NAME)));
                object.setPackage_name(cursor.getString(cursor.getColumnIndex(DBConfig.KEY_PACKAGE_NAME)));
                object.setIcon(cursor.getString(cursor.getColumnIndex(DBConfig.KEY_ICON)));
                object.setType_name(cursor.getString(cursor.getColumnIndex(DBConfig.KEY_TYPE_NAME)));
                object.setType_id(cursor.getString(cursor.getColumnIndex(DBConfig.KEY_TYPE_ID)));
                Objects.add(object);
            }while (cursor.moveToNext());
        }

        cursor.close();
        closeDatabase();
        return Objects;
    }

    public List<AppType> GetAppType(){
        List<AppType> Objects = new ArrayList<>();
        openReadable();
        Cursor cursor = database.query(
                DBConfig.TABLE_APP_DETAIL,
                new String[]{DBConfig.KEY_TYPE_ID,DBConfig.KEY_TYPE_NAME},
                DBConfig.KEY_IS_ACTIVE + "=?",
                new String[]{"1"},
                DBConfig.KEY_TYPE_ID,
                null,
                null
        );

        if (null != cursor && cursor.moveToFirst()){
            do {
                AppType object = new AppType();
                object.setType_name(cursor.getString(cursor.getColumnIndex(DBConfig.KEY_TYPE_NAME)));
                object.setType_id(cursor.getString(cursor.getColumnIndex(DBConfig.KEY_TYPE_ID)));
                Objects.add(object);
            }while (cursor.moveToNext());
        }

        cursor.close();
        closeDatabase();
        return Objects;
    }

    //--------------End app_list

}