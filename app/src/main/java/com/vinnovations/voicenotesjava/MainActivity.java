package com.vinnovations.voicenotesjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> notesTopicList = new ArrayList<>();
//    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listview);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle FAB click event here
                // Add your desired action or navigation logic
                Intent intent = new Intent(MainActivity.this, CreateNoteActivity.class);
                startActivity(intent);
                finish();
            }
        });

        MyDBHandler db = new MyDBHandler(MainActivity.this);
        List<NotesEntity> allNotes = db.getAllNotes();
        for(NotesEntity notesEntity: allNotes) {
            notesTopicList.add(notesEntity.getTopicName());
//            count++;
//            Log.v("dbNotes", "\nId: " + notesEntity.getId() + "\n" +
//                    "topic: " + notesEntity.getTopicName() + "\n" +
//                    "desc: " + notesEntity.getTopicDesc() + "\n");
//            Toast.makeText(MainActivity.this, "hello " + notesEntity.getId() , Toast.LENGTH_SHORT ).show();
        }


//        if(count == allNotes.size()){
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesTopicList);
            listView.setAdapter(adapter);
//        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click event here
                String selectedItem = (String) parent.getItemAtPosition(position);
                // Perform actions based on the selected item
            }
        });
    }
}