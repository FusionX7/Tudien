package tudienav.laptrinhandroid.nhom7.tudien;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

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

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getDbConnection();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case WORDS:
                // For the PETS code, query the pets table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the pets table.
                // TODO: Perform database query on pets table
                cursor = database.query(DictionaryEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case WORD_ID:
                // For the PET_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.pets/pets/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = DictionaryEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // This will perform a query on the pets table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(DictionaryEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case WORDS:
                return insertPet(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Insert a pet into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertPet(Uri uri, ContentValues values) {
        // Check that the name is not null
        String name = values.getAsString(DictionaryEntry.COLUMN_EV_EN);
        if (name == null) {
            throw new IllegalArgumentException("Pet requires a name");
        }

        // TODO: Finish sanity checking the rest of the attributes in ContentValues

        // Get writeable database
        SQLiteDatabase database = mDbHelper.getDbConnection();

        // Insert the new pet with the given values
        long id = database.insert(DictionaryEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri,null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }
    private int updatePet(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        /*if (values.size() == 0) {
            return 0;
        }
        if (values.containsKey(DictionaryEntry.COLUMN_EV_EN)){
            String name = values.getAsString(DictionaryEntry.COLUMN_EV_EN);
            if (name == null) {
                throw new IllegalArgumentException("Pet requires a name");
            }
        }
        // If the {@link PetEntry#COLUMN_PET_GENDER} key is present,
        // check that the gender value is valid.
        if (values.containsKey(DictionaryEntry.COLUMN_PET_GENDER)) {
            Integer gender = values.getAsInteger(DictionaryEntry.COLUMN_PET_GENDER);
            if (gender == null || !PDictionaryEntry.isValidGender(gender)) {
                throw new IllegalArgumentException("Pet requires valid gender");
            }
        }

        // If the {@link PetEntry#COLUMN_PET_WEIGHT} key is present,
        // check that the weight value is valid.
        if (values.containsKey(DictionaryEntry.COLUMN_PET_WEIGHT)) {
            // Check that the weight is greater than or equal to 0 kg
            Integer weight = values.getAsInteger(DictionaryEntry.COLUMN_PET_WEIGHT);
            if (weight != null && weight < 0) {
                throw new IllegalArgumentException("Pet requires valid weight");
            }

            getContext().getContentResolver().notifyChange(uri,null);
        }

        SQLiteDatabase database = mDbHelper.getDbConnection();
        // TODO: Update the selected pets in the pets database table with the given ContentValues
        int id = database.update(DictionaryEntry.TABLE_NAME,values,selection,selectionArgs);
        // TODO: Return the number of rows that were affected*/
        return 0;
    }
    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case WORDS:
                return updatePet(uri, contentValues, selection, selectionArgs);
            case WORD_ID:
                // For the PET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = DictionaryEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updatePet(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getDbConnection();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case WORDS:
                getContext().getContentResolver().notifyChange(uri,null);
                // Delete all rows that match the selection and selection args
                return database.delete(DictionaryEntry.TABLE_NAME, selection, selectionArgs);
            case WORD_ID:
                // Delete a single row given by the ID in the URI
                selection = DictionaryEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                getContext().getContentResolver().notifyChange(uri,null);
                return database.delete(DictionaryEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case WORDS:
                return DictionaryEntry.CONTENT_LIST_TYPE;
            case WORD_ID:
                return  DictionaryEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri + " with match " + match);
        }
    }
}
