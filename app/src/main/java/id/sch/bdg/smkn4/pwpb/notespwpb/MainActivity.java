package id.sch.bdg.smkn4.pwpb.notespwpb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerviewAdapter.OnUserClickListener{
    RecyclerView recyclerView;
    FloatingActionButton btnInput;
    RecyclerView.LayoutManager layoutManager;
    List<Note> listNoteInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Notes");
        recyclerView = findViewById(R.id.rvContainer);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        btnInput = findViewById(R.id.btnAdd);
        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CreateActivity.class);
                startActivity(i);
            }
        });
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        DatabaseHelper db = new DatabaseHelper(MainActivity.this);
        listNoteInfo = db.selectUserData();
        RecyclerviewAdapter adapter = new RecyclerviewAdapter(MainActivity.this, listNoteInfo, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onUserClick(Note currentNote, String action) {
        if(action.equals("Edit")){
            Intent i = new Intent(MainActivity.this, UpdateActivity.class);
            i.putExtra(UpdateActivity.CURRENT_NOTE, currentNote);
            startActivity(i);
        }
        if(action.equals("Delete")){
            DatabaseHelper db = new DatabaseHelper(MainActivity.this);
            db.delete(currentNote.getJudul());
            setupRecyclerView();
        }
    }
}
