package id.sch.bdg.smkn4.pwpb.notespwpb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateActivity extends AppCompatActivity {
    public static String CURRENT_NOTE = "extra_note";
    EditText edtTitle, edtDescription;
    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        this.setTitle("Edit Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Note note = getIntent().getParcelableExtra(CURRENT_NOTE);
        edtTitle = findViewById(R.id.edtJudul);
        edtDescription = findViewById(R.id.edtDeskripsi);
        edtTitle.setText(note.getJudul());
        edtDescription.setText(note.getDeskripsi());
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(edtTitle.getText().toString().trim().equals("") || edtDescription.getText().toString().trim().equals(""))) {
                    DatabaseHelper db = new DatabaseHelper(UpdateActivity.this);
                    Note currentNote = new Note();
                    currentNote.setJudul(edtTitle.getText().toString());
                    currentNote.setDeskripsi(edtDescription.getText().toString());
                    SimpleDateFormat tanggal = new SimpleDateFormat ("dd/MM/yyyy' 'hh:mm:ss");
                    String tglNow = tanggal.format(new Date());
                    currentNote.setTanggal(tglNow);
                    db.update(currentNote);
                    Intent i = new Intent(UpdateActivity.this, MainActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(UpdateActivity.this,"Data tidak boleh kosong", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
