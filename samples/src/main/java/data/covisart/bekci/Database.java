package data.covisart.bekci;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper
{
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Tur_Data";//database adı

    private static final String TABLE_NAME = "tur_data";
    private static String WORK_ID = "id";
    private static String WORK_Name = "adi";
    private static String WORK_Tarih = "tarih";
    private static String WORK_Saat = "saat";
    private static String WORK_Konum = "konum";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {  // Databesi oluşturuyoruz.Bu methodu biz çağırmıyoruz. Databese de obje oluşturduğumuzda otamatik çağırılıyor.
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + WORK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + WORK_Name + " TEXT,"
                + WORK_Tarih + " TEXT,"
                + WORK_Saat + " TEXT,"
                + WORK_Konum + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    public void WORKSil(int id){ //id si belli olan row u silmek için

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, WORK_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    public void WORK_Ekle(String WORK_name, String WORK_tarih,String WORK_saat,String WORK_konum)
    {
        //WORKEkle methodu ise adı üstünde Databese veri eklemek için
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WORK_Name, WORK_name);
        values.put(WORK_Tarih, WORK_tarih);
        values.put(WORK_Saat, WORK_saat);
        values.put(WORK_Konum, WORK_konum);

        db.insert(TABLE_NAME, null, values);
        db.close(); //Database Bağlantısını kapattık*/
    }

    public HashMap<String, String> WORKDetay(int id){
        HashMap<String,String> WORK = new HashMap<String,String>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME+ " WHERE id="+id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            WORK.put(WORK_Name, cursor.getString(1));
            WORK.put(WORK_Tarih, cursor.getString(2));
            WORK.put(WORK_Saat, cursor.getString(3));
            WORK.put(WORK_Konum, cursor.getString(4));
        }
        cursor.close();
        db.close();
        // return WORK
        return WORK;
    }

    public  ArrayList<HashMap<String, String>> WORK_LIST(){

        //Bu methodda ise tablodaki tüm değerleri alıyoruz
        //ArrayList adı üstünde Array lerin listelendiği bir Array.Burda hashmapleri listeleyeceğiz
        //Herbir satırı değer ve value ile hashmap a atıyoruz. Her bir satır 1 tane hashmap arrayı demek.
        //olusturdugumuz tüm hashmapleri ArrayList e atıp geri dönüyoruz(return).

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> WORKlist = new ArrayList<HashMap<String, String>>();
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                for(int i=0; i<cursor.getColumnCount();i++)
                {
                    map.put(cursor.getColumnName(i), cursor.getString(i));
                }

                WORKlist.add(map);
            } while (cursor.moveToNext());
        }
        db.close();
        // return WORK liste
        return WORKlist;
    }

    public void WORKDuzenle(String WORK_name, String WORK_tarih,String WORK_saat,String WORK_konum,int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        //Bu methodda ise var olan veriyi güncelliyoruz(update)
        ContentValues values = new ContentValues();
        values.put(WORK_Name, WORK_name);
        values.put(WORK_Tarih, WORK_tarih);
        values.put(WORK_Saat, WORK_saat);
        values.put(WORK_Konum, WORK_konum);

        // updating row
        db.update(TABLE_NAME, values, WORK_ID + " = ?",
                new String[] { String.valueOf(id) });
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

    public void resetTables(){
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
