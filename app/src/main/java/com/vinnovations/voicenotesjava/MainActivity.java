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
    List<String> notesDescList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.e("create", "create");
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listview);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateNoteActivity.class);
                startActivity(intent);
                finish();
            }
        });

        MyDBHandler db = new MyDBHandler(MainActivity.this);
        List<NotesEntity> allNotes = db.getAllNotes();
        for(NotesEntity notesEntity: allNotes) {
            notesTopicList.add(notesEntity.getTopicName());
            notesDescList.add(notesEntity.getTopicDesc());
        }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesTopicList);
            listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click event here
                String selectedItem = (String) parent.getItemAtPosition(position);
                NotesDescription notesDesc = new NotesDescription();
                Bundle args = new Bundle();
                args.putString("title", selectedItem);
                args.putString("desc", notesDescList.get(position));
                args.putString("position", String.valueOf(position+1));
                notesDesc.setArguments(args);
                notesDesc.show(getSupportFragmentManager(), "NotesDescription");

            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.e("start", "start");
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.e("resume", "resume");
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.e("pause", "pause");
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.e("stop", "stop");
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Log.e("restart", "restart");
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.e("destroy", "destroy");
//    }


}