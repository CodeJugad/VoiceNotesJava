package com.vinnovations.voicenotesjava;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> notesTopicList = new ArrayList<>();
    List<String> notesDescList = new ArrayList<>();
    MyDBHandler db;
    private MainActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         mActivity = this;
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

        db = new MyDBHandler(MainActivity.this);
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
                int notesId = db.getId(selectedItem, notesDescList.get(position));
                NotesDescription notesDesc = new NotesDescription();
                Bundle args = new Bundle();
                args.putString("title", selectedItem);
                args.putString("desc", notesDescList.get(position));
                args.putString("position", String.valueOf(notesId));
                notesDesc.setArguments(args);
                notesDesc.show(getSupportFragmentManager(), "NotesDescription");

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_item1) {
            // Handle menu item 1 click
            showDialogBox();
            return true;
        }
//        } else if (id == R.id.menu_item2) {
//            // Handle menu item 2 click
//            return true;
//        }
        // Add more if-else blocks for additional menu items

        return super.onOptionsItemSelected(item);
    }

    private void showDialogBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All Notes")
                .setMessage("You will not be able to access notes in future!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle OK button click
                        db.deleteAll(); // this is not working
                        // Reload the activity
                        Intent intent = getIntent();
                        mActivity.finish();
                        mActivity.startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle Cancel button click
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Example usage within another method
    public void onReloadButtonClick(View view) {
        reloadActivity();
    }
    public void reloadActivity() {
        recreate();
    }



}