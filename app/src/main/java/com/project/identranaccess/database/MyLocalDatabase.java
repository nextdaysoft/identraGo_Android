package com.project.identranaccess.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.project.identranaccess.model.FavData;
import com.project.identranaccess.model.ProfileData;
import com.project.identranaccess.model.VisitorData;

import java.util.ArrayList;

public class MyLocalDatabase extends SQLiteOpenHelper {

  private static final String DB_NAME = "db_identran";

  private static final int DB_VERSION = 1;
  private static final String TABLE_NAME = "newvisitentry";
  private static final String USER_TABLE_NAME = "usertable";
  private static final String PROFILE_TABLE_NAME = "profilenametable";
  private static final String ID_COL = "id";
  private static final String ID_Time = "time";
  private static final String NAME_COL = "name";
  private static final String CODE = "code";
  private static final String LASTNAME_COL = "lastname";
  private static final String DATEOFVISIT_COL = "date";
  private static final String VISITREASON_COL = "reason";
  private static final String COMMENT_COL = "comment";
  private static final String PROFILE_IMAGE = "image";
  private static final String PROFILE_NAME = "name";
  private static final String PROFILE_ADMIN = "administration";
  private static final String PROFILE_Contact = "contact";
  private static final String PROFILE_POS = "position";

  private static final String user_PROFILE_POS_name = "name";
  private static final String user_PROFILE_POS_last = "lastname";
  private static final String user_PROFILE_POS_email = "email";
  private static final String user_PROFILE_POS_password = "password";
  private static final String FAV_TABLE_NAME = "favnametable";
  private static final String FAV_NAME_COL = "favname";
  private static final String FAV_LASTNAME_COL = "favlastname";
  private static final String FAV_DATEOFVISIT_COL = "favdate";
  private static final String FAV_VISITREASON_COL = "favreason";
  private static final String FAV_COMMENT_COL = "favcomment";
  private static final String FAV_CODE = "favcode";
    private static final String FAVID_Time = "time";


    public MyLocalDatabase(@Nullable Context context) {
    super(context, DB_NAME, null, DB_VERSION);
  }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
/*
  @Override
  public void onCreate(SQLiteDatabase db) {
    String query = "CREATE TABLE " + TABLE_NAME + " ("
            + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME_COL + " TEXT,"
            + LASTNAME_COL + " TEXT,"
            + DATEOFVISIT_COL + " TEXT,"
            + VISITREASON_COL + " TEXT,"
            + COMMENT_COL + " TEXT,"
            + CODE + " TEXT,"
            + ID_Time + " TEXT)";

    String favquery = "CREATE TABLE " + FAV_TABLE_NAME + " ("
            + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FAV_NAME_COL + " TEXT,"
            + FAV_LASTNAME_COL + " TEXT,"
            + FAV_VISITREASON_COL + " TEXT,"
            + FAV_DATEOFVISIT_COL + " TEXT,"
            + FAV_COMMENT_COL + " TEXT,"
            + FAV_CODE + " TEXT,"
            + FAVID_Time + " TEXT)";


*//*    String queryProfile = "CREATE TABLE " + USER_TABLE_NAME + " ("
            + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + user_PROFILE_POS_name + " TEXT,"
            + user_PROFILE_POS_last + " TEXT,"
            + user_PROFILE_POS_email + " TEXT,"
            + user_PROFILE_POS_password + " TEXT)";*//*


 String profile_query = "CREATE TABLE " + PROFILE_TABLE_NAME + " ("
            + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PROFILE_IMAGE + " BLOB NOT NULL,"
            + PROFILE_NAME + " TEXT,"
            + PROFILE_ADMIN + " TEXT,"
            + PROFILE_Contact + " TEXT,"
            + PROFILE_POS + " TEXT)";

    db.execSQL(query);
    db.execSQL(profile_query);
   // db.execSQL(queryProfile);
    db.execSQL(favquery);
  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

  }

    public void addNewVisitData(String name, String lastname, String dateofvisit, String reasonofvisit, String comment, String code, String ts) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL, name);
        values.put(LASTNAME_COL, lastname);
        values.put(DATEOFVISIT_COL, dateofvisit);
        values.put(VISITREASON_COL, reasonofvisit);
        values.put(COMMENT_COL, comment);
        values.put(CODE, code);
        values.put(ID_Time, ts);
        db.insert(TABLE_NAME, null, values);
        Log.e("user_data", "addNewVisitData: "+ values );

        db.close();
    }


    public ArrayList<VisitorData> visitorData() {

    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursorVisit = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    ArrayList<VisitorData> visitdataArrayList = new ArrayList<>();

    if (cursorVisit.moveToFirst()) {
      do {
        visitdataArrayList.add(new VisitorData(cursorVisit.getString(1),
                cursorVisit.getString(2),
                cursorVisit.getString(3),
                cursorVisit.getString(4),
                cursorVisit.getString(5),
                cursorVisit.getString(6),
                cursorVisit.getString(7)));
     //   for(int i =0;i<cursorVisit.moveToFirst();)
          Log.e("user_data", "addNewVisitData: "+ cursorVisit.getString(7) );

      } while (cursorVisit.moveToNext());
      // moving our cursor to next.
    }
        cursorVisit.close();
        return visitdataArrayList;
  }

    public void profile_Data(byte[] byteArray, String name, String administration, String pos, String mob) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PROFILE_IMAGE,byteArray);
        values.put(NAME_COL, name);
        values.put(PROFILE_ADMIN, administration);
        values.put(PROFILE_Contact, mob);
        values.put(PROFILE_POS, pos);
        db.insert(PROFILE_TABLE_NAME, null, values);
        Log.e("profile_data", "addNewVisitData: "+values );
        db.close();
    }


    *//*public ArrayList<ProfileData> profileData() {

    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursorVisitdata = db.rawQuery("SELECT * FROM " + PROFILE_TABLE_NAME, null);
    ArrayList<ProfileData> profiledataArrayList = new ArrayList<>();

    if (cursorVisitdata.moveToFirst()) {
      do {
        profiledataArrayList.add(new ProfileData(cursorVisitdata.getBlob(1),
                cursorVisitdata.getString(2),
                cursorVisitdata.getString(3),
                cursorVisitdata.getString(4)));
      } while (cursorVisitdata.moveToNext());

    }
    cursorVisitdata.close();
    return profiledataArrayList;
  }
*//*
*//*  public void addUserListData(String name, String lastname, String email, String password) {

    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(user_PROFILE_POS_name,name);
    values.put(user_PROFILE_POS_last, lastname);
    values.put(user_PROFILE_POS_email, email);
    values.put(user_PROFILE_POS_password, password);
    db.insert(USER_TABLE_NAME, null, values);
    Log.e("profile_data", "addNewVisitData: "+values );
    db.close();
  }

  public  ArrayList<UserListModel> UserModelData() {

    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE_NAME, null);
    ArrayList<UserListModel> dataArrayList = new ArrayList<>();

    if (cursor.moveToFirst()) {
      do {
        dataArrayList.add(new UserListModel(cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4)));
      } while (cursor.moveToNext());

    }
    cursor.close();
    return dataArrayList;
  }*//*

  public ArrayList<FavData> fav_listData() {

    SQLiteDatabase db = this.getReadableDatabase();

    Cursor cursorfav = db.rawQuery("SELECT * FROM " + FAV_TABLE_NAME, null);

    ArrayList<FavData> favdataArrayList = new ArrayList<>();

    if (cursorfav.moveToFirst()) {
      do {
        favdataArrayList.add(new FavData(cursorfav.getString(1),
                cursorfav.getString(2),
                cursorfav.getString(3),
                cursorfav.getString(4),
                cursorfav.getString(5),
                cursorfav.getString(6),
                cursorfav.getString(7)));
      } while (cursorfav.moveToNext());
      // moving our cursor to next.
    }
    cursorfav.close();
    return favdataArrayList;
  }
  public void addfavListData(String name, String lastName, String reasonofvisit, String modalReasonofvisit, String code, String modalCode, String ts) {
    SQLiteDatabase db = this.getWritableDatabase();
*//*    Cursor cursor = db.rawQuery("SELECT "+name+ " FROM "+FAV_TABLE_NAME+" WHERE "+
            NAME_COL + " = '"+TABLE_NAME +"'",null);
    Log.e("data", "cursor is matched: "+name);
    Log.e("data", "cursor is matched: "+nameValue );

    //  Cursor cursor = db.rawQuery("SELECT * FROM FAV_TABLE_NAME WHERE user="", null);
    if(cursor.moveToFirst()){
      Log.e("data", "cursor is matched: " );

   // for (int i = 0; i <= visitorData().size(); i++) {
    Log.e("data", "cursor is matched: "+visitorData().get(0).getName());
    Log.e("data", "cursor is matched: "+name );


      } else {*//*
        ContentValues values = new ContentValues();
        values.put(FAV_NAME_COL, name);
        values.put(FAV_LASTNAME_COL, lastName);
        values.put(FAV_VISITREASON_COL, reasonofvisit);
        values.put(FAV_DATEOFVISIT_COL, modalReasonofvisit);
        values.put(FAV_COMMENT_COL, code);
        values.put(FAV_CODE, modalCode);
        values.put(FAVID_Time, ts);
        db.insert(FAV_TABLE_NAME, null, values);
        db.close();
        Log.e("fav_user", "addNewVisitData: " + values);
      }

    public void remove(String code) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FAV_TABLE_NAME, "favcode=?", new String[]{code});
        db.close();
  }*/


}




