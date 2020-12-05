package org.aplas.uas_millan;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SiswaActivity extends AppCompatActivity {
    List<Siswa> siswaList;
    SQLiteDatabase mDatabase;
    ListView listViewSiswa;
    SiswaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siswa);

        listViewSiswa = (ListView) findViewById(R.id.listViewSiswa);
        siswaList = new ArrayList<>();

        //membuka database
        mDatabase = openOrCreateDatabase(MainActivity.DATABASE_NAME, MODE_PRIVATE, null);

        //memunculkan siswa di list
        showSiswaFromDatabase();
    }

    private void showSiswaFromDatabase(){
        //we used rawQuery(sql, selectionargs) for fetching all the employees
        Cursor cursorSiswa = mDatabase.rawQuery("SELECT * FROM siswa", null);

        //if the cursor has some data
        if (cursorSiswa.moveToFirst()) {
            //looping through all the records
            do {
                //pushing each record in the employee list
                siswaList.add(new Siswa(
                        cursorSiswa.getInt(0),
                        cursorSiswa.getString(1),
                        cursorSiswa.getString(2),
                        cursorSiswa.getString(3),
                        cursorSiswa.getString(4),
                        cursorSiswa.getString(5),
                        cursorSiswa.getString(6),
                        cursorSiswa.getString(7),
                        cursorSiswa.getString(8),
                        cursorSiswa.getString(9),
                        cursorSiswa.getString(10)
                ));
            } while (cursorSiswa.moveToNext());
        }
        //closing the cursor
        cursorSiswa.close();

        //creating the adapter object
        adapter= new SiswaAdapter(this, R.layout.list_layout_siswa, siswaList, mDatabase);

        //adding the adapter to listView
        listViewSiswa.setAdapter(adapter);
    }
}