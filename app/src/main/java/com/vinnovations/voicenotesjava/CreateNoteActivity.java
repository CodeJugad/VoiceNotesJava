package com.vinnovations.voicenotesjava;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CreateNoteActivity extends AppCompatActivity {

//     Declare a member variable for the SpeechRecognizer
    private SpeechRecognizer speechRecognizer;

    // Declare a member variable for the Button or ImageView

    TextView textView;
    int REQUEST_CODE_SPEECH_INPUT = 123;
    String spokenStringText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);


        textView = findViewById(R.id.textView);


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
//                if (spokenStringText != null){
//                    textView.setText(spokenStringText);
//                }

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
            spokenStringText = String.valueOf(spokenText);
            // Do something with the spoken text
            processSpokenText(spokenText);
        }
    }

    private void processSpokenText(String spokenText) {
        // Handle the spoken text here
        // Add your desired actions or logic
//        spokenStringText = spokenText;
        if (spokenText != null){
            textView.setText(spokenText);
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

}