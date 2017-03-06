package tudienav.laptrinhandroid.nhom7.tudien;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import tudienav.laptrinhandroid.nhom7.tudien.DictionaryContract.DictionaryEntry;

public class DictionaryActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, TextToSpeech.OnInitListener {
    private int MY_DATA_CHECK_CODE = 0;
    private TextView wordMeaning;
    private TextView txtpro;
    private TextView txtviet;
    private TextView word;
    private TextToSpeech tts;
    private Uri currentUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                    Toast.makeText(DictionaryActivity.this, "Saying: " + text, Toast.LENGTH_LONG).show();
                    tts.speak(text, TextToSpeech.QUEUE_ADD, null);
                }
            }
        });

        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
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
        if (status == TextToSpeech.SUCCESS) {
            Toast.makeText(DictionaryActivity.this,
                    "Text-To-Speech engine is initialized", Toast.LENGTH_SHORT).show();
        }
        else if (status == TextToSpeech.ERROR) {
            Toast.makeText(DictionaryActivity.this,
                    "Error occurred while initializing Text-To-Speech engine", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                DictionaryContract.DictionaryEntry._ID,
                DictionaryContract.DictionaryEntry.COLUMN_EV_EN,
                DictionaryContract.DictionaryEntry.COLUMN_EV_VI,
                DictionaryContract.DictionaryEntry.COLUMN_EV_CLASSIFIER,
                DictionaryContract.DictionaryEntry.COLUMN_EV_PRONUNCIATION};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                currentUri,         // Query the content URI for the current pet
                projection,             // Columns to include in the resulting Cursor
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
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        word.setText("");
        wordMeaning.setText("");
        txtpro.setText("");
        txtviet.setText("");
    }
}
