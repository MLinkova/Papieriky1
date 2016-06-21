package sk.upjs.ics.android.papieriky1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Michaela on 31.03.2016.
 */
public class PapierikyDatabaseOpenHelper extends SQLiteOpenHelper {
    public PapierikyDatabaseOpenHelper(Context context) {
        super(context, "papieriky",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="Create table %s(" +
                "%s integer primary key autoincrement," +
                "%s text" +
                ")";
        db.execSQL(String.format(sql,
                Papieriky.Papierik.TABLE_NAME,
                Papieriky.Papierik._ID,
                Papieriky.Papierik.POZNAMKA));
        vlozUkazkoveData(db,"Kup mlieko");
        vlozUkazkoveData(db,"Bud ci nebud");
    }

    private void vlozUkazkoveData(SQLiteDatabase db,String popis) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Papieriky.Papierik.POZNAMKA,popis);

       db.insert(Papieriky.Papierik.TABLE_NAME,null,contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
