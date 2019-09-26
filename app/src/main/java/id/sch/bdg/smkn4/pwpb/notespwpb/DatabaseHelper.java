package id.sch.bdg.smkn4.pwpb.notespwpb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "NoteDB";
    private static final String TABLE_NAME = "tbl_db";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "desc";
    private static final String KEY_DATE = "date";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "Create Table " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_TITLE + " TEXT, " + KEY_DESCRIPTION + " TEXT, " + KEY_DATE + " TEXT)";
        db.execSQL(createUserTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = ("drop table if exists " + TABLE_NAME);
        db.execSQL(sql);
        onCreate(db);
    }

    public void insert(Note note){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, note.getJudul());
        values.put(KEY_DESCRIPTION, note.getDeskripsi());
        values.put(KEY_DATE, note.getTanggal());
        db.insert(TABLE_NAME,null,values);
    }

    public List<Note> selectUserData(){
        ArrayList<Note> userList = new ArrayList<Note>();
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {KEY_ID,KEY_TITLE,KEY_DESCRIPTION,KEY_DATE};
        Cursor c = db.query(TABLE_NAME,columns,null,null,null,null,null);
        while (c.moveToNext()){
            int id = c.getInt(0);
            String title = c.getString(1);
            String desc = c.getString(2);
            String date = c.getString(3);
            Note note = new Note();
            note.setId(id);
            note.setJudul(title);
            note.setDeskripsi(desc);
            note.setTanggal(date);
            userList.add(note);
        }
        return userList;
    }

    public void delete(String judul){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = KEY_TITLE + "='" + judul + "'";
        db.delete(TABLE_NAME, whereClause,null);
    }

    public void update(Note note){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, note.getJudul());
        values.put(KEY_DESCRIPTION, note.getDeskripsi());
        values.put(KEY_DATE, note.getTanggal());
        String whereClause = KEY_ID + "=" + note.getId() + "";
        db.update(TABLE_NAME, values, whereClause,null);
    }
}
