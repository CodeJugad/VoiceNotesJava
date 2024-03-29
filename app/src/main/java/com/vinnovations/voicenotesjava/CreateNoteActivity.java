package com.vinnovations.voicenotesjava;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CreateNoteActivity extends AppCompatActivity {

//     Declare a member variable for the SpeechRecognizer
    private SpeechRecognizer speechRecognizer;

    // Declare a member variable for the Button or ImageView


    EditText edt_topic, edt_desc;
    int REQUEST_CODE_SPEECH_INPUT = 123;
    String count = "0";
    String notes_title, notes_desc;
    String position;
    Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);


        edt_desc = findViewById(R.id.edt_desc);
        edt_topic = findViewById(R.id.edt_topic);
        btn_save = findViewById(R.id.btn_save);
//        notes_title = String.valueOf(edt_topic.getText()); // never try to get the value from edittext in oncreate it will always give null
//        notes_title = edt_topic.getText().toString();

        // -------------------- getting text from other apps
//        Intent intent = getIntent();
//        if(intent != null){
//            String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
//            processSpokenText(sharedText);
//        }
        // --------------
        Intent intent = getIntent();
        if (intent != null && intent.getAction() != null && intent.getAction().equals(Intent.ACTION_SEND)) {
            if (intent.getType() != null && intent.getType().equals("text/plain")) {
                String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
                if (sharedText != null) {
                    // Process the shared URL string as needed
                    processSpokenText(sharedText);
                }
            }
        }


        // ------------------------ when data is coming from main activity or fragment to update or delete
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String title = bundle.getString("title");
            String desc = bundle.getString("desc");
            position = bundle.getString("position");
            count = bundle.getString("count");
            if (count.equals("1")){
                btn_save.setText("Update");
            }else if(count.equals("2")){
                btn_save.setText("Delete");
            }
            processSpokenText(desc);
            edt_topic.setText(title);

        }

        // Initialize the SpeechRecognizer
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        // Initialize the Button or ImageView
//        Button voiceInputButton = findViewById(R.id.btn);
//        voiceInputButton.setOnClickListener(new View.OnClickListener() {
        FloatingActionButton speak = findViewById(R.id.speech_mic);
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceInput();
            }
        });

        // saving in db

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyDBHandler db = new MyDBHandler(CreateNoteActivity.this);
                notes_title = String.valueOf(edt_topic.getText());
                notes_desc = String.valueOf(edt_desc.getText());
//                Log.v("myMessage", "hello" + notes_title);
                if(notes_title.isEmpty()){
                    Toast.makeText(CreateNoteActivity.this, "Please Enter Title", Toast.LENGTH_SHORT).show();
                }else{
                    NotesEntity notesEn;
                    if(count.equals("1")){
                        notesEn = new NotesEntity(Integer.parseInt(position),notes_title,notes_desc);
                        db.updateNotes(notesEn);
                    }else if(count.equals("2")){
                        notesEn = new NotesEntity(Integer.parseInt(position),notes_title,notes_desc);
                        db.deleteNotesEntity(notesEn);
                    } else{
                        notesEn = new NotesEntity(notes_title,notes_desc);
                        db.addNotes(notesEn);
                    }
                        Intent intent = new Intent(CreateNoteActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }


            }
        });
    }

    private void startVoiceInput() {
        // Create an intent to launch the speech recognition activity
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        // Start the activity for voice input
        startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null) {
            // Retrieve the speech-to-text results
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            // Get the first result (most likely the user's intended speech)
            String spokenText = results.get(0);
            // Do something with the spoken text
            processSpokenText(spokenText);
        }
    }

    private void processSpokenText(String spokenText) {
        // Handle the spoken text here
        // Add your desired actions or logic
        if (spokenText != null){
            String s1 = String.valueOf(edt_desc.getText());
//            if(s1 == null){   // strings can be null or empty learn this concept
            if(s1.isEmpty()){
                edt_desc.setText(spokenText);
            }else{
//                String s1 = String.valueOf(edt_desc.getText());
//                String s2 = s1.concat(spokenText);
                String s2 = s1 + " " + spokenText;
                edt_desc.setText(s2);
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Release resources when the activity is destroyed
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(CreateNoteActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}