package tudienav.laptrinhandroid.nhom7.tudien;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import tudienav.laptrinhandroid.nhom7.tudien.DictionaryContract.DictionaryEntry;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private EditText filterText;
    private WordCursorAdapter wordCursorAdapter;
    Cursor mCursor;
    boolean learned;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        filterText = (EditText)findViewById(R.id.editText);
        ListView itemList = (ListView)findViewById(R.id.listView);
        learned =false;
        wordCursorAdapter = new WordCursorAdapter(this,null);
        itemList.setAdapter(wordCursorAdapter);
        getLoaderManager().initLoader(0,null,this);

        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,DictionaryActivity.class);
                Uri currentUri = ContentUris.withAppendedId(DictionaryEntry.CONTENT_URI,id);
                intent.setData(currentUri);
                startActivity(intent);
            }
        });

        filterText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getLoaderManager().restartLoader(0,null,MainActivity.this);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            if(learned==false)
            learned=true;
            else learned = false;
            getLoaderManager().restartLoader(0,null,MainActivity.this);
            return learned;
        }

        return super.onOptionsItemSelected(item);
    }
    /*private CursorLoader search(String keywords){
        String[] args = {keywords};
        String[] projection = {
                DictionaryEntry._ID,
                DictionaryEntry.COLUMN_EV_EN,
                DictionaryEntry.COLUMN_EV_VI,
        };
        return new CursorLoader(this,DictionaryEntry.CONTENT_URI,projection,DictionaryEntry.COLUMN_EV_EN+"=?",args,null);
    }*/

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String keywords;
        keywords = filterText.getText().toString();
        String[] projection = {
                DictionaryEntry._ID,
                DictionaryEntry.COLUMN_EV_EN,
                DictionaryEntry.COLUMN_EV_VI,
                DictionaryEntry.COLUMN_EV_USERCHECK
        };
        if (keywords.length()>0 && learned ){
            String[] selectArgs={"1",keywords+"%"};
            return new CursorLoader(this,DictionaryEntry.CONTENT_URI,projection,DictionaryEntry.COLUMN_EV_USERCHECK + "=?" + "AND " + DictionaryEntry.COLUMN_EV_EN+" LIKE ?",selectArgs,DictionaryEntry.COLUMN_EV_EN+" ASC");
        }
        if (keywords.length()>0){
            String[] selectArgs={keywords+"%"};
            return new CursorLoader(this,DictionaryEntry.CONTENT_URI,projection,DictionaryEntry.COLUMN_EV_EN+" LIKE ?",selectArgs,DictionaryEntry.COLUMN_EV_EN+" ASC");
        }
        if(learned){
            String[] selectArgs={"1"};
            return new CursorLoader(this,DictionaryEntry.CONTENT_URI,projection,DictionaryEntry.COLUMN_EV_USERCHECK+"=?",selectArgs,null);
        }

        else
        return new CursorLoader(this,DictionaryEntry.CONTENT_URI,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        wordCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        wordCursorAdapter.swapCursor(null);
    }
}
