package sk.upjs.ics.android.papieriky1;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class PapierikyContentProvider extends ContentProvider {
    private PapierikyDatabaseOpenHelper dbhelper;

    public PapierikyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        long id = ContentUris.parseId(uri);
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        int pocetRiakov = db.delete(Papieriky.Papierik.TABLE_NAME, Papieriky.Papierik._ID + " = " + id, null);
        getContext().getContentResolver().notifyChange(Papieriky.Papierik.CONTENT_URI,null);
        return pocetRiakov;


    }

    @Override
    public String getType(Uri uri) {
        // metoda sa uz nepouziva
      return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        long id = db.insert(Papieriky.Papierik.TABLE_NAME, null, values);
        // upozornit posluchacov
        //2xshift+ctrl+medzera pre sirsie vyhladavanie a pomoc
        getContext().getContentResolver().notifyChange(Papieriky.Papierik.CONTENT_URI,null);
        return ContentUris.withAppendedId(Papieriky.Papierik.CONTENT_URI,id);
    }

    @Override
    public boolean onCreate() {
        //inicializacia db,Context content providera cez getContext pretoze this nemozme mat nakolko content provder nie je context
        this.dbhelper = new PapierikyDatabaseOpenHelper(getContext());
        // true ak sa provider naloadoval(otvoril)
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // db volanie database sa bude opakovat ale netaham do onCreate pretoze tato metoda by mala zbehnut co najrychlejsie,
        // preto db si vytvorim vzdy ked potrebujem
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        //query vrati cursor
        Cursor cursor = db.query(Papieriky.Papierik.TABLE_NAME, null, null, null, null, null, null);// vsetky null okrem nazvu tab. ak chcem vytiahnut vsetky
        cursor.setNotificationUri(getContext().getContentResolver(), Papieriky.Papierik.CONTENT_URI);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
