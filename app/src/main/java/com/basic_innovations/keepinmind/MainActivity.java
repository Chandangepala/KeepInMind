package com.basic_innovations.keepinmind;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<NoteData> arrNoteData = new ArrayList<>();
    LinearLayout linLayAdd;
    FloatingActionButton btnAdd;
    EditText edtNewtitle, edtNewNote;
    Button addNewBtn;
    Animation appearBox;
    SharedPreferences preferences;
    int flag = 0;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("addNewNote", MODE_PRIVATE);
        i = preferences.getInt("Counter",0);

        initMain(); // to initiliase all the layout views

        //dummy notes
        addNote("Title 1", "Note 1");
        addNote("Title 2", "Note 2");
        addNote("Title 3", "Note 3");
        addNote("Title 4", "Note 4");

        for(int j = 0; j < preferences.getInt("Counter", 1); j++){
            saveNote(j);}

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final RecyclerviewNotesAdapter recyclerviewNotesAdapter = new RecyclerviewNotesAdapter(this, arrNoteData);
        recyclerView.setAdapter(recyclerviewNotesAdapter);
        recyclerView.startAnimation(appearBox);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //For appear and dissappear the layout
                if (flag % 2 == 0) {
                    linLayAdd.setVisibility(View.VISIBLE);
                    linLayAdd.startAnimation(appearBox);
                    flag++;
                } else {
                    linLayAdd.setVisibility(View.GONE);
                    flag++;
                }
            }
        });
        addNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewNote(i);
                i++;
                recyclerviewNotesAdapter.notifyDataSetChanged();
                linLayAdd.setVisibility(View.GONE);
                flag = 0;
            }
        });
    }

    //to initialize all layout views
    public void initMain(){
        linLayAdd = findViewById(R.id.linlay_add);
        btnAdd = findViewById(R.id.btn_add);
        edtNewtitle = findViewById(R.id.add_title);
        edtNewNote = findViewById(R.id.add_note);
        addNewBtn = findViewById(R.id.addNew_btn);
        appearBox = AnimationUtils.loadAnimation(this, R.anim.appear_box);
        recyclerView = findViewById(R.id.recycler_view);
    }

    //to add a note...in arrNoteData
    public void addNote(String title, String note) {
        NoteData noteData = new NoteData();
        noteData.title = title;
        noteData.Note = note;
        arrNoteData.add(noteData);
    }

    //to add a new note dynamically in the arrNoteData
    public void addNewNote(int i) {
        NoteData noteData = new NoteData();
        noteData.title = edtNewtitle.getText().toString();
        noteData.Note = edtNewNote.getText().toString();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("TitleNew "+i, edtNewtitle.getText().toString());
        editor.putString("NoteNew "+i, edtNewNote.getText().toString());
        editor.apply();
        noteData.title = preferences.getString("TitleNew "+i, "New Title");
        noteData.Note = preferences.getString("NoteNew "+i, "New Note");
        arrNoteData.add(noteData);

        edtNewtitle.setText("");
        edtNewNote.setText("");
    }

    //to save the note using shared pref
    public void saveNote(int i){
        NoteData noteData = new NoteData();
        noteData.title = preferences.getString("TitleNew "+i, "New Title");
        noteData.Note = preferences.getString("NoteNew "+i, "New Note");
        arrNoteData.add(noteData);
    }

    //method to be called at back button press
    @Override
    public void onBackPressed() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Counter", i);
        editor.apply();
        super.onBackPressed();
    }
}
