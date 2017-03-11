package tudienav.laptrinhandroid.nhom7.tudien;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import tudienav.laptrinhandroid.nhom7.tudien.DictionaryContract.DictionaryEntry;

public class DictionaryActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, TextToSpeech.OnInitListener {
    private int MY_DATA_CHECK_CODE = 1;
    private TextView wordMeaning;
    private TextView txtpro;
    private TextView txtviet;
    private TextView word;
    private TextToSpeech tts;
    private Uri currentUri;
    int currentUserCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dictionary);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        Intent intent = getIntent();
        currentUri = intent.getData();

        word = (TextView)findViewById(R.id.word);
        txtpro = (TextView)findViewById(R.id.txtpronounce);
        txtviet = (TextView)findViewById(R.id.txtvie);
        wordMeaning = (TextView)findViewById(R.id.dictionary);
        Button textToSpeech = (Button)findViewById(R.id.button);

        getLoaderManager().initLoader(1, null, this);



        textToSpeech.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String text = word.getText().toString();
                if (text!=null && text.length()>0) {
                    tts.speak(text, TextToSpeech.QUEUE_ADD, null);
                }
            }
        });

        Intent checkIntent = new Intent();
        if (MY_DATA_CHECK_CODE==0){
            checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
            startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
        }


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                MY_DATA_CHECK_CODE = 1;
                // success, create the TTS instance
                tts = new TextToSpeech(this, this);
            }
            else {
                // missing data, install it
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        }

    }

    public void onInit(int status) {
        /*if (status == TextToSpeech.SUCCESS) {
            Toast.makeText(DictionaryActivity.this,
                    "Text-To-Speech engine is initialized", Toast.LENGTH_SHORT).show();
        }*/
        if (status == TextToSpeech.ERROR) {
            Toast.makeText(DictionaryActivity.this,
                    "Error occurred while initializing Text-To-Speech engine", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }
    private void checkChanger(){
        ContentValues check = new ContentValues();
        int i;

        if(currentUserCheck==0)
            i=1;
        else
            i=0;
        check.put(DictionaryEntry.COLUMN_EV_USERCHECK,i);
        int result = getContentResolver().update(currentUri, check,null,null);
        Log.d("i status","i="+i+"check="+currentUserCheck);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {// User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Exit activity
                checkChanger();
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:

                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // If the pet hasn't changed, continue with navigating up to parent activity
                // which is the {@link CatalogActivity}.

                    NavUtils.navigateUpFromSameTask(DictionaryActivity.this);
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
                DictionaryEntry.COLUMN_EV_CLASSIFIER,
                DictionaryEntry.COLUMN_EV_PRONUNCIATION,
                DictionaryEntry.COLUMN_EV_USERCHECK};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                currentUri,         // Query the content URI for the current pet
                null,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursor.moveToFirst();
        word.setText(cursor.getString(cursor.getColumnIndex(DictionaryEntry.COLUMN_EV_EN)));
        wordMeaning.setText(cursor.getString(cursor.getColumnIndex(DictionaryEntry.COLUMN_EV_CLASSIFIER)));
        txtpro.setText(cursor.getString(cursor.getColumnIndex(DictionaryEntry.COLUMN_EV_PRONUNCIATION)));
        txtviet.setText(cursor.getString(cursor.getColumnIndex(DictionaryEntry.COLUMN_EV_VI)));
        currentUserCheck=cursor.getInt(cursor.getColumnIndex(DictionaryEntry.COLUMN_EV_USERCHECK));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        word.setText("");
        wordMeaning.setText("");
        txtpro.setText("");
        txtviet.setText("");
    }
}
