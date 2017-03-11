package tudienav.laptrinhandroid.nhom7.tudien;

import android.content.Context;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
/**
 * Created by Fusion on 13/04/2016.
 */
public class DictionaryDatabase  extends SQLiteAssetHelper {

        private static final String DATABASE_NAMES = "quiz";
        private static final int DATABASE_VERSION = 2;

        public DictionaryDatabase(Context context) {
            super(context, DATABASE_NAMES, null, DATABASE_VERSION);
            // TODO Auto-generated constructor stub
            setForcedUpgrade(DATABASE_VERSION);
    }
}
