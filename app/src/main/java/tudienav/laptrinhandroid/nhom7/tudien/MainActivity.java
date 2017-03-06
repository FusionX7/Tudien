package tudienav.laptrinhandroid.nhom7.tudien;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import tudienav.laptrinhandroid.nhom7.tudien.DictionaryContract.DictionaryEntry;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private EditText filterText;
    private WordCursorAdapter wordCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        filterText = (EditText)findViewById(R.id.editText);
        ListView itemList = (ListView)findViewById(R.id.listView);
        wordCursorAdapter = new WordCursorAdapter(this,null);
        itemList.setAdapter(wordCursorAdapter);
        getLoaderManager().initLoader(0,null,this);



        //final DbBackend dbBackend = new DbBackend(MainActivity.this);
        //String[] terms = dbBackend.dictionaryWords();

        /*itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String txt = ((TextView)view).getText().toString();
                int engid=dbBackend.wid(txt);

                // make Toast when click
                Toast.makeText(getApplicationContext(), "Position " + engid, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, DictionaryActivity.class);
                intent.putExtra("DICTIONARY_ID", engid);
                startActivity(intent);
            }
        });*/

        /*filterText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.this.listAdapter.getFilter().filter(s);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                DictionaryEntry._ID,
                DictionaryEntry.COLUMN_EV_EN,
                DictionaryEntry.COLUMN_EV_VI,
        };
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
