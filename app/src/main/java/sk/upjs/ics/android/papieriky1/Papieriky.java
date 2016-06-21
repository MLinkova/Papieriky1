package sk.upjs.ics.android.papieriky1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Michaela on 31.03.2016.
 */
public interface Papieriky {
public interface Papierik extends BaseColumns{
    public static  final Uri CONTENT_URI=Uri.parse("content://sk.upjs.ics.android.papieriky1.PapierikyContentProvider/papierik");
    public static final String TABLE_NAME="Papieriky";
    public static final String POZNAMKA="poznamka";
}
}
