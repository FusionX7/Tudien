package tudienav.laptrinhandroid.nhom7.tudien;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Hphuo on 03/04/2017.
 */

public final class DictionaryContract {
    private DictionaryContract(){}
    public static final class DictionaryEntry{
        public final static String TABLE_NAME = "dictionary";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_EV_EN = "en";
        public final static String COLUMN_EV_PRONUNCIATION = "pronunciation";
        public final static String COLUMN_EV_VI= "vi";
        public final static String COLUMN_EV_CLASSIFIER = "classifier";
        public final static String COLUMN_EV_USERCHECK = "usercheck";

        public final static int USER_UNCHECKED = 0;
        public final static int USER_CHECKED = 1;

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_DICTIONARY);
        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of pets.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DICTIONARY;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DICTIONARY;

        public static boolean isValidUserCheck(int check) {
            if (check == USER_UNCHECKED || check == USER_CHECKED) {
                return true;
            }
            return false;
        }
    }
    public static final String CONTENT_AUTHORITY = "tudienav.laptrinhandroid.nhom7.tudien";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_DICTIONARY = "dictionary";
}
