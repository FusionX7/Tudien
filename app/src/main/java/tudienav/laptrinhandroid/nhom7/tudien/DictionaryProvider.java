package tudienav.laptrinhandroid.nhom7.tudien;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import tudienav.laptrinhandroid.nhom7.tudien.DictionaryContract.DictionaryEntry;

/**
 * Created by Hphuo on 03/04/2017.
 */

public class DictionaryProvider extends ContentProvider {
    /** Tag for the log messages */
    public static final String LOG_TAG = DictionaryProvider.class.getSimpleName();


    /** URI matcher code for the content URI for the pets table */
    private static final int WORDS = 100;

    /** URI matcher code for the content URI for a single pet in the pets table */
    private static final int WORD_ID = 101;
    private DbObject mDbHelper;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        // TODO: Add 2 content URIs to URI matcher
        sUriMatcher.addURI(DictionaryContract.CONTENT_AUTHORITY, DictionaryContract.PATH_DICTIONARY, WORDS);
        sUriMatcher.addURI(DictionaryContract.CONTENT_AUTHORITY, DictionaryContract.PATH_DICTIONARY + "/#", WORD_ID);
    }
    @Override
    public boolean onCreate() {
        mDbHelper = new DbObject(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase db = mDbHelper.getDbConnection();
        int match = sUriMatcher.match(uri);
        switch (match){
            case WORDS:
                return db.query(DictionaryEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);

        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
