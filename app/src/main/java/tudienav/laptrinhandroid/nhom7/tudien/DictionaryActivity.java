package tudienav.laptrinhandroid.nhom7.tudien;

import android.content.Intent;
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

public class DictionaryActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private int MY_DATA_CHECK_CODE = 0;
    private TextView wordMeaning;
    private TextView txtpro;
    private TextView txtviet;
    private TextToSpeech tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int dictionaryId = bundle.getInt("DICTIONARY_ID");
        int id = dictionaryId;

        final TextView word = (TextView)findViewById(R.id.word);
        TextView txtpro = (TextView)findViewById(R.id.txtpronounce);
        TextView txtviet = (TextView)findViewById(R.id.txtvie);
        wordMeaning = (TextView)findViewById(R.id.dictionary);
        Button textToSpeech = (Button)findViewById(R.id.button);

        DbBackend dbBackend = new DbBackend(DictionaryActivity.this);
        QuizObject allQuizQuestions = dbBackend.getQuizById(id);

        word.setText(allQuizQuestions.getEng());
        wordMeaning.setText(allQuizQuestions.getKind());
        txtpro.setText(allQuizQuestions.getPronounce());
        txtviet.setText(allQuizQuestions.getVie());

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
}
