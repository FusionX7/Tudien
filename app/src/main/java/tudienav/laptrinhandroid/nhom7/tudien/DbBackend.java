package tudienav.laptrinhandroid.nhom7.tudien;

/**
 * Created by Fusion on 13/04/2016.
 */
import android.content.Context;

import android.database.Cursor;

import java.util.ArrayList;

public class DbBackend extends DbObject{

    public DbBackend(Context context) {
        super(context);
    }

    public String[] dictionaryWords(){
        String query = "Select * from dictionary";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        ArrayList<String> wordTerms = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                String word = cursor.getString(cursor.getColumnIndexOrThrow("eng"));
                wordTerms.add(word);
            }while(cursor.moveToNext());
        }
        cursor.close();
        String[] dictionaryWords = new String[wordTerms.size()];
        dictionaryWords = wordTerms.toArray(dictionaryWords);
        return dictionaryWords;
    }
    public int wid(String txt) {
        String query = "select _id from dictionary where eng = " +"'"+ txt+"'";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);

        int engid = 0;
        if (cursor.moveToFirst()) {
            do {
                engid = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return engid;
    }
    public WordObject getQuizById(int quizId){

        WordObject wordObject = null;
        String query = "select * from dictionary where _id = " + quizId;
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String eng = cursor.getString(cursor.getColumnIndexOrThrow("eng"));
                String kind = cursor.getString(cursor.getColumnIndexOrThrow("kind"));
                String pronounce = cursor.getString(cursor.getColumnIndexOrThrow("pronounce"));
                String vie = cursor.getString(cursor.getColumnIndexOrThrow("vi"));
                wordObject = new WordObject(eng, kind, pronounce, vie);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return wordObject;
    }
}
