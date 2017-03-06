package tudienav.laptrinhandroid.nhom7.tudien;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import tudienav.laptrinhandroid.nhom7.tudien.DictionaryContract.DictionaryEntry;

/**
 * Created by Hphuo on 03/04/2017.
 */

public class WordCursorAdapter extends CursorAdapter {

    public WordCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvName = (TextView)view.findViewById(R.id.en_word);
        TextView tvSummary = (TextView)view.findViewById(R.id.vi_word);

        String getName = cursor.getString(cursor.getColumnIndex(DictionaryEntry.COLUMN_EV_EN));
        String getSummary = cursor.getString(cursor.getColumnIndex(DictionaryEntry.COLUMN_EV_VI));
        tvName.setText(getName);
        tvSummary.setText(getSummary);
    }
}
