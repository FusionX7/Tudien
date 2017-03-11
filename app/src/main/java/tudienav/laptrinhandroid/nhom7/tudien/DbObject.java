package tudienav.laptrinhandroid.nhom7.tudien;

/**
 * Created by Fusion on 13/04/2016.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DbObject {

    private static DictionaryDatabase dbHelper;
    private SQLiteDatabase db;

    public DbObject() {}

    public SQLiteDatabase getDbConnection(Context context){
        dbHelper = new DictionaryDatabase(context);
        this.db = dbHelper.getWritableDatabase();
        return this.db;
    }
    public SQLiteDatabase getWritableDbConnection(Context context){
        dbHelper = new DictionaryDatabase(context);
        this.db = dbHelper.getWritableDatabase();
        return this.db;
    }


    public void closeDbConnection(){
        if(this.db != null){
            this.db.close();
        }
    }
}
