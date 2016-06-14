package barabaraen.barblipp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class DBHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "Barblipp.sql";
    private String devid;
    SimpleDateFormat dateFormat;
    Random r;
    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        devid = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        r = new Random(System.currentTimeMillis());


    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_KAMERER_TABLE = "CREATE TABLE " + "Kamererer" + "("
                + "id" + " INTEGER PRIMARY KEY," + "name" + " TEXT,"
                + "sektion" + " INT," + "pengar " + "INT," + "Grupp " + "INT" +")";
        String CREATE_GRUPPER_TABLE = "CREATE TABLE Grupper (id INTEGER PRIMARY KEY, name TEXT)";
        String CREATE_SEKTIONER_TABLE = "CREATE TABLE sektioner (id INTEGER PRIMARY KEY, name TEXT)";
        String CREATE_KRYSS_TABLE = "CREATE TABLE Kryss (tag TEXT PRIMARY KEY, kamerer INT,amount FLOAT, ts DATETIME,device TEXT,krysstyp INT,Longitud FLOAT,Latitud FLOAT)";
        db.execSQL(CREATE_KAMERER_TABLE);
        db.execSQL(CREATE_GRUPPER_TABLE);
        db.execSQL(CREATE_KRYSS_TABLE);
        db.execSQL(CREATE_SEKTIONER_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + "Kamererer");
        db.execSQL("DROP TABLE IF EXISTS " + "Grupper");
        db.execSQL("DROP TABLE IF EXISTS " + "sektioner");
        db.execSQL("DROP TABLE IF EXISTS " + "Kryss");
        // Create tables again
        onCreate(db);
    }
    public void addKamerer(int id, String name,int sektion,int pengar,int grupp) {

        ContentValues values  = new ContentValues();

        SQLiteDatabase db = this.getWritableDatabase();


        db.insert("Kamererer", null, values);
        db.close();
    }
    public int addKamerer(String s) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("kamerere", "antal: " + db.rawQuery("SELECT * from Kamererer",null).getCount());
        ContentValues values  = new ContentValues();
        int count = 0;
        try {
            JSONArray jarr = new JSONArray(s);
            JSONObject jobj = new JSONObject();
            for (int i = 0; i < jarr.length(); i++) {
                jobj = jarr.getJSONObject(i);
                values.put("id",Integer.parseInt(jobj.get("id").toString()));
                values.put("name",jobj.get("name").toString());
                values.put("sektion",Integer.parseInt(jobj.get("sektion").toString()));
                values.put("pengar",Integer.parseInt(jobj.get("pengar").toString()));
                values.put("Grupp",Integer.parseInt(jobj.get("Grupp").toString()));

                Log.i("km", jobj.get("name").toString());

                if(db.insertWithOnConflict("Kamererer",null,values,SQLiteDatabase.CONFLICT_IGNORE) == 0)
                count++;
            }
        }
        catch (Exception e){
                Log.d("error",e.toString());
            }
        Log.i("kamerere", "antal: " + db.rawQuery("SELECT * from Grupper",null).getCount());
        db.close();
        return count;
    }
    public int addGrupper(String s) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("Grupper", "antal: " + db.rawQuery("SELECT * from Grupper",null).getCount());
        ContentValues values  = new ContentValues();
        int count = 0;
        try {
            JSONArray jarr = new JSONArray(s);
            JSONObject jobj = new JSONObject();
            for (int i = 0; i < jarr.length(); i++) {
                jobj = jarr.getJSONObject(i);
                values.put("id",Integer.parseInt(jobj.get("Id").toString()));
                values.put("name",jobj.get("name").toString());
                Log.i("km", jobj.get("name").toString());

                if(db.insertWithOnConflict("Grupper",null,values,SQLiteDatabase.CONFLICT_IGNORE)== 0)
                    count++;
            }
        }
        catch (Exception e){
            Log.d("error",e.toString());
        }
        Log.i("grupper", "antal: " + db.rawQuery("SELECT * from Grupper",null).getCount());
        db.close();
        return count;
    }
    public int addSektioner(String s) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("Sektioner", "antal: " + db.rawQuery("SELECT * from sektioner",null).getCount());
        ContentValues values  = new ContentValues();
        int count = 0;
        try {
            JSONArray jarr = new JSONArray(s);
            JSONObject jobj = new JSONObject();
            for (int i = 0; i < jarr.length(); i++) {
                jobj = jarr.getJSONObject(i);
                values.put("id",Integer.parseInt(jobj.get("id").toString()));
                values.put("name",jobj.get("name").toString());
                Log.i("km", jobj.get("name").toString());

                if(db.insertWithOnConflict("sektioner",null,values,SQLiteDatabase.CONFLICT_IGNORE)== 0)
                    count++;
            }
        }
        catch (Exception e){
            Log.d("error",e.toString());
        }
        Log.i("sektioner", "antal: " + db.rawQuery("SELECT * from sektioner",null).getCount());
        db.close();
        return count;
    }

    public void addKryss(int kamerer,float amount, int krysstyp) {

        Date date = new Date();

        ContentValues values = new ContentValues();

        values.put("tag", devid + date.getTime() + Integer.toHexString(r.nextInt()));
        values.put("kamerer", kamerer);
        values.put("amount", amount);
        values.put("krysstyp",krysstyp);
        values.put("ts",dateFormat.format(date));
        values.put("device",devid);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("kryss",null,values);
        db.close();
    }
    public void getKryss(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        String query = "Select * FROM " +"kryss";




        cursor = db.rawQuery(query, null);



        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            for(int x = 0; x < cursor.getCount(); x++) {

                if (cursor.getColumnCount() > 0) {
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        try {
                            Log.i("AK", cursor.getColumnName(i) + " : " + cursor.getString(i));
                        } catch (Exception e) {
                            Log.i("error", e.toString());
                        }
                    }
                } else {
                    Log.i("AK", "Inga kryss i databasen");
                }
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
    }
    public void findKamerer(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        String query = "Select * FROM " +"Kamererer";




        cursor = db.rawQuery(query, null);



        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            if(cursor.getColumnCount() > 0) {
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    try {
                        Log.i("AK", cursor.getString(i));
                    }
                    catch (Exception e){
                        Log.i("error", e.toString());
                    }
                }

            }
            else{
                Log.i("AK", "Inga kamerere i databasen");
            }
            cursor.close();
        }
        db.close();

    }
    public ArrayList<String> GetKamerer() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        ArrayList<String> kmlist = new ArrayList<String>();
        String query = "Select name FROM " +"Kamererer";
        cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                try {
                    kmlist.add(i,cursor.getString(0));
                }
                catch (Exception e){
                    Log.i("error", e.toString());
                }
                cursor.moveToNext();
            }

        }
        else{
            Log.i("AK", "Inga kamerere i databasen");
        }
        cursor.close();

        db.close();
        return kmlist;

    }
    public int getKamererID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        ArrayList<String> kmlist = new ArrayList<String>();
        String query = "Select id FROM " + "Kamererer WHERE name="+ '"' + name + '"';
        cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            return Integer.parseInt(cursor.getString(0));
            }
        return 0;
    }

}



