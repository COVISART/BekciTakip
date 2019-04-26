package data.covisart.bekci;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class Kayit extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Mail";//database adı

    private static final String TABLE_NAME = "yonetici_mail";
    private static String WORK_ID = "id";
    private static String WORK_Mail = "mail";

    public Kayit(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {  // Databesi oluşturuyoruz.Bu methodu biz çağırmıyoruz. Databese de obje oluşturduğumuzda otamatik çağırılıyor.
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + WORK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + WORK_Mail + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }


    public void WORK_Ekle(String WORK_mail) {
        //WORKEkle methodu ise adı üstünde Databese veri eklemek için
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WORK_Mail, WORK_mail);


        db.insert(TABLE_NAME, null, values);
        db.close(); //Database Bağlantısını kapattık*/
    }

    public ArrayList<HashMap<String, String>> WORK_LIST() {

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> WORKlist = new ArrayList<HashMap<String, String>>();
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    map.put(cursor.getColumnName(i), cursor.getString(i));
                }

                WORKlist.add(map);
            } while (cursor.moveToNext());
        }
        db.close();
        // return WORK liste
        return WORKlist;
    }

    public void WORKDuzenle(String WORK_mail, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        //Bu methodda ise var olan veriyi güncelliyoruz(update)
        ContentValues values = new ContentValues();
        values.put(WORK_Mail, WORK_mail);

        // updating row
        db.update(TABLE_NAME, values, WORK_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public int getRowCount() {
        // Bu method bu uygulamada kullanılmıyor ama her zaman lazım olabilir.Tablodaki row sayısını geri döner.
        //Login uygulamasında kullanacağız
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        // return row count
        return rowCount;
    }

    public void resetTables() {
        //Bunuda uygulamada kullanmıyoruz. Tüm verileri siler. tabloyu resetler.
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_NAME, null, null);
        Log.println(Log.ERROR, "resetTables: ", "db.delete");
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

}
