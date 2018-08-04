package pl.swpw.info.wydatki;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by rzak on 07.04.2018.
 */

public class BazaWydatki extends SQLiteOpenHelper{

    public static final int DB_VERSION = 1;

    public static final String DB_NAME = "wydatki2.db";

    public static final String TB_NAME_WPISY = "wpisy";
    public static final String TB_NAME_ZAKUPY = "zakupy";

    //Tabela WPISY
    public static final String CL_NAME_ID_WPISY = "_id";
    public static final String ID_OPTIONS_WPISY = " INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String CL_NAME_DATA_WPISY = "data";
    public static final String DATA_OPTIONS_WPISY = " TEXT NOT NULL";
    public static final String CL_NAME_ZAKUP_WPISY = "zakup";
    public static final String ZAKUP_OPTIONS_WPISY = " TEXT NOT NULL";
    public static final String CL_NAME_KWOTA_WPISY = "kwota";
    public static final String KWOTA_OPTIONS_WPISY = " REAL NOT NULL";

    //Tabela ZAKUPY
    public static final String CL_NAME_ID_ZAKUPY = "_id";
    public static final String ID_OPTIONS_ZAKUPY = " INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String CL_NAME_ZAKUP_ZAKUPY = "zakup";
    public static final String ZAKUP_OPTIONS_ZAKUPY= " TEXT NOT NULL";



    private static final String DB_CREATE_TB_WPISY = "CREATE TABLE " + TB_NAME_WPISY + "( " +
            CL_NAME_ID_WPISY + ID_OPTIONS_WPISY + ", " + CL_NAME_DATA_WPISY + DATA_OPTIONS_WPISY + ", " +
            CL_NAME_ZAKUP_WPISY + ZAKUP_OPTIONS_WPISY + ", " + CL_NAME_KWOTA_WPISY + KWOTA_OPTIONS_WPISY + " );";
    private static final String DB_DROP_TB_WPISY = "DROP TABLE IF EXISTS " + TB_NAME_WPISY ;

    private static final String DB_CREATE_TB_ZAKUPY = "CREATE TABLE " + TB_NAME_ZAKUPY + "( " +
            CL_NAME_ID_ZAKUPY + ID_OPTIONS_ZAKUPY + ", " + CL_NAME_ZAKUP_ZAKUPY + ZAKUP_OPTIONS_ZAKUPY + " );";
    private static final String DB_DROP_TB_ZAKUPY = "DROP TABLE IF EXISTS " + TB_NAME_ZAKUPY ;


    public BazaWydatki(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DB_CREATE_TB_WPISY);
        sqLiteDatabase.execSQL(DB_CREATE_TB_ZAKUPY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(DB_DROP_TB_WPISY);
        sqLiteDatabase.execSQL(DB_DROP_TB_ZAKUPY);
        onCreate(sqLiteDatabase);
    }

    public  void wstawWydatek(String dataZ, String zakupZ, float kwotaZ){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues nowyWydatek = new ContentValues();
        nowyWydatek.put(CL_NAME_DATA_WPISY, dataZ);
        nowyWydatek.put(CL_NAME_ZAKUP_WPISY, zakupZ);
        nowyWydatek.put(CL_NAME_KWOTA_WPISY, kwotaZ);
        db.insertOrThrow(TB_NAME_WPISY, null, nowyWydatek);
        db.close();
        Log.d("Komentarz", "Wstawiono rekord do bazy");
    }

    public  void wstawZakup(String zakupZ){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues nowyZakup = new ContentValues();
        nowyZakup.put(CL_NAME_ZAKUP_ZAKUPY, zakupZ);
        db.insertOrThrow(TB_NAME_ZAKUPY, null, nowyZakup);
        db.close();
        Log.d("Komentarz", "Wstawiono rekord do bazy");
    }

    public ArrayList<Wydatek> selectListaWydatki (String warunek){
        ArrayList<Wydatek> wydatki = new ArrayList<Wydatek>();
        String [] kolumny ={CL_NAME_ID_WPISY, CL_NAME_DATA_WPISY, CL_NAME_ZAKUP_WPISY, CL_NAME_KWOTA_WPISY};
        SQLiteDatabase db = getReadableDatabase();
        String where = CL_NAME_DATA_WPISY + ">='" + warunek +"'";
        String sort = CL_NAME_DATA_WPISY + " DESC, " + CL_NAME_ID_WPISY + " DESC";
        Cursor kursor = db.query(TB_NAME_WPISY, kolumny, where, null, null, null,sort);
        if(kursor!=null && kursor.getCount()>0){
            kursor.moveToFirst();
            do{
                Wydatek wydatek = new Wydatek(kursor.getLong(0),
                        kursor.getString(1), kursor.getString(2),
                        kursor.getFloat(3));
                wydatki.add(wydatek);
            } while (kursor.moveToNext());
        }
        kursor.close();
        db.close();
        return wydatki;
    }

    public ArrayList<Zakup> selectListaZakupy (){
        ArrayList<Zakup> zakupy = new ArrayList<Zakup>();
        String [] kolumny ={CL_NAME_ID_ZAKUPY, CL_NAME_ZAKUP_ZAKUPY};
        SQLiteDatabase db = getReadableDatabase();

        Cursor kursor = db.query(TB_NAME_ZAKUPY, kolumny, null, null, null,null,null);
        if(kursor!=null && kursor.getCount()>0){
            kursor.moveToFirst();
            do{
                Zakup zakup = new Zakup(kursor.getLong(0),
                        kursor.getString(1));
                zakupy.add(zakup);
            } while (kursor.moveToNext());
        }
        kursor.close();
        db.close();
        return zakupy;
    }

    public float sumaWydatkow (String warunek){
        SQLiteDatabase db = getReadableDatabase();
        float wynik;
        Cursor cursor=db.rawQuery("SELECT SUM(" + CL_NAME_KWOTA_WPISY + ") FROM " + TB_NAME_WPISY + " WHERE " + CL_NAME_DATA_WPISY + " >= '" + warunek + "'", null);
        if (cursor.moveToFirst()) {
            wynik=cursor.getFloat(0);
        } else {
            wynik=0;
        }
        cursor.close();
        db.close();
        return wynik;
    }


    public void skasujWydatek(long idRekordu){
        SQLiteDatabase db =getWritableDatabase();
        String where = CL_NAME_ID_WPISY + "=" + idRekordu;
        db.delete(TB_NAME_WPISY,where,null);
        db.close();
        Log.d("komentarz", "Usunięto wpis z bazy");
    }

    public void skasujZakup(long idRekordu){
        SQLiteDatabase db =getWritableDatabase();
        String where = CL_NAME_ID_ZAKUPY + "=" + idRekordu;
        db.delete(TB_NAME_ZAKUPY,where,null);
        db.close();
        Log.d("komentarz", "Usunięto wpis z bazy");
    }

}
