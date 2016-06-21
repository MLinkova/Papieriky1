package sk.upjs.ics.android.papieriky1;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.AsyncQueryHandler;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
private GridView papierikyGridView;
    private SimpleCursorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        papierikyGridView= (GridView) findViewById(R.id.PapierikyGridView);
        papierikyGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AsyncQueryHandler handler= new AsyncQueryHandler(getContentResolver()) {
                    @Override
                    protected void onDeleteComplete(int token, Object cookie, int result) {

                    }
                };
                Uri uri= ContentUris.withAppendedId(Papieriky.Papierik.CONTENT_URI,id);
                handler.startDelete(0,null,uri,null,null);//alt+P pre zistenie parametrov
                return true;
            }
        });

        String []from ={ Papieriky.Papierik.POZNAMKA};//skadial beriem data
        int [] to={R.id.papierikGridViewItem};//kde datam data
        adapter= new SimpleCursorAdapter(this,R.layout.papierik,null,from,
               to ,0);
        papierikyGridView.setAdapter(adapter);
        getLoaderManager().initLoader(0, Bundle.EMPTY, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader= new CursorLoader(this);
        loader.setUri(Papieriky.Papierik.CONTENT_URI);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor kurzor) {
        adapter.swapCursor(kurzor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menuPridat){
            final EditText papierikEditText = new EditText(this);
            DialogInterface.OnClickListener listener= new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //tu by som mal ulozit do databazy
                    String poznamka=papierikEditText.getText().toString();

                    ulozDoContentProvidera(poznamka);
                }
            };


            new AlertDialog.Builder(this)
                    .setTitle("Pridat zlty papierik").
                    setMessage("Napiste poznamku")
                    .setPositiveButton("OK", listener)
                    .setView(papierikEditText)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void ulozDoContentProvidera(String poznamka) {
        ContentValues values = new ContentValues();
        values.put(Papieriky.Papierik.POZNAMKA,poznamka);
        AsyncQueryHandler handler=new AsyncQueryHandler(getContentResolver()) {
            @Override
            protected void onInsertComplete(int token, Object cookie, Uri uri) {
                //dorobit toast
                super.onInsertComplete(token, cookie, uri);
            }
        };
        handler.startInsert(0,null,Papieriky.Papierik.CONTENT_URI,values);
    }
}
