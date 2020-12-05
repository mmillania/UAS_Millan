package org.aplas.uas_millan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String DATABASE_NAME = "pelitabangsa";
    TextView textViewSiswa, textViewNilaiAkhir, textViewHasilNilaiAkhir;
    EditText editTextNama, editTextNomer, editTextKelas, editTextNilaiHadir, editTextNilaiTugas, editTextNilaiUTS, editTextNilaiUAS;
    Spinner spinnerSemester;
    Button bHitung, bSimpan;

     SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewSiswa = (TextView) findViewById(R.id.textViewSiswa);
        textViewNilaiAkhir = (TextView) findViewById(R.id.textViewNilaiAkhir);
        textViewHasilNilaiAkhir = (TextView) findViewById(R.id.textViewHasilNilaiAkhir);


        editTextNama = (EditText) findViewById(R.id.editTextNama);
        editTextNomer = (EditText) findViewById(R.id.editTextNomer);
        editTextKelas = (EditText) findViewById(R.id.editTextKelas);
        editTextNilaiHadir = (EditText) findViewById(R.id.editTextNilaiHadir);
        editTextNilaiTugas = (EditText) findViewById(R.id.editTextNilaiTugas);
        editTextNilaiUTS = (EditText) findViewById(R.id.editTextNilaiUTS);
        editTextNilaiUAS = (EditText) findViewById(R.id.editTextNilaiUAS);
        spinnerSemester = (Spinner) findViewById(R.id.spinnerSemester);

        bSimpan = (Button) findViewById(R.id.bSimpan);
        bHitung = (Button) findViewById(R.id.bHitung);

        textViewSiswa.setOnClickListener(this);
        bHitung.setOnClickListener(this);
        bSimpan.setOnClickListener(this);

        //membuat database
        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        createSiswaTable();
    }
    //untuk memvalidasi nama,nomer,kelas,nilai kehadiran,nilai tugas,nilai uts,nilai uas
    private boolean inputsAreCorrect(String nama, String nomer, String kelas,String nilaikehadiran, String nilaitugas, String nilaiuts, String nilaiuas){
        if (nama.equals("") || nomer.equals("") || kelas.equals("") || nilaikehadiran.equals("") || nilaitugas.equals("") || nilaiuts.equals("") || nilaiuas.equals("")){
            editTextNama.setError("Nama Wajib diisi!");
            editTextNomer.setError("NISN Wajib diisi!");
            editTextKelas.setError("Kelas Wajib diisi!");
            editTextNilaiHadir.setError("Nilai Kehadiran Wajib diisi!");
            editTextNilaiTugas.setError("Nilai Tugas Wajib diisi!");
            editTextNilaiUTS.setError("Nilai UTS Wajib diisi!");
            editTextNilaiUAS.setError("Nilai UAS Wajib diisi!");
            return false;
        }
        return true;
    }

    private void addSiswa(){
    String nama = editTextNama.getText().toString().trim();
    String kelas = editTextKelas.getText().toString().trim();
    String nomer = editTextNomer.getText().toString().trim();
    String semester = spinnerSemester.getSelectedItem().toString();
    String nilaikehadiran =editTextNilaiHadir.getText().toString().trim();
    String nilaitugas =editTextNilaiTugas.getText().toString().trim();
    String nilaiuts =editTextNilaiUTS.getText().toString().trim();
    String nilaiuas =editTextNilaiUAS.getText().toString().trim();
    String nilaiakhir = textViewHasilNilaiAkhir.getText().toString().trim();
    //getting the current time for joining date
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String joiningDate = sdf.format(cal.getTime());

        //validasi inputan
        if(inputsAreCorrect(nama,nomer,kelas,nilaikehadiran,nilaitugas,nilaiuts,nilaiuas)){
            String insertSQL = "INSERT INTO siswa \n" +
                    "(nama, nomer, kelas, semester, nilaikehadiran, nilaitugas, nilaiuts, nilaiuas,nilaiakhir, joiningdate)\n" +
                    "VALUES \n" +
                    "(?,?,?,?,?,?,?,?,?,?);";

            mDatabase.execSQL(insertSQL, new String[]{nama, nomer, kelas, semester, nilaikehadiran,nilaitugas,nilaiuts,nilaiuas ,nilaiakhir,joiningDate});

            Toast.makeText(this, "Data Siswa berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
        }

    }

    private void hitungNilai(){
        String nilaikehadiran =editTextNilaiHadir.getText().toString().trim();
        String nilaitugas =editTextNilaiTugas.getText().toString().trim();
        String nilaiuts =editTextNilaiUTS.getText().toString().trim();
        String nilaiuas =editTextNilaiUAS.getText().toString().trim();
        if (nilaikehadiran.equals("") || nilaitugas.equals("") || nilaiuts.equals("") || nilaiuas.equals("")) {
            editTextNilaiTugas.setError("Nilai Tugas Wajib diisi!");
            editTextNilaiUTS.setError("Nilai UTS Wajib diisi!");
            editTextNilaiUAS.setError("Nilai UAS Wajib diisi!");

        }else{
            double total = (0.1 * Integer.parseInt(nilaikehadiran)) + (0.2 *  Integer.parseInt(nilaitugas)) + (0.3 *  Integer.parseInt(nilaiuts)) + (0.4 *  Integer.parseInt(nilaiuas));
            textViewHasilNilaiAkhir.setText(String.valueOf(total));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bSimpan:
                addSiswa();
            break;

            case R.id.textViewSiswa:
            startActivity(new Intent(this, SiswaActivity.class));
            break;

            case R.id.bHitung:

                hitungNilai();
        }
    }

    //membuat table siswa
    private void createSiswaTable(){
        mDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS siswa (\n" +
                "    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "    nama varchar(200) NOT NULL,\n" +
                "    nomer varchar(200) NOT NULL,\n" +
                "    kelas varchar(200) NOT NULL,\n" +
                "    semester varchar(200) NOT NULL,\n" +
                "    nilaikehadiran varchar(200) NOT NULL,\n" +
                "    nilaitugas varchar(200) NOT NULL,\n" +
                "    nilaiuts varchar(200) NOT NULL,\n" +
                "    nilaiuas varchar(200) NOT NULL,\n" +
                        "    nilaiakhir varchar(200) NOT NULL,\n" +

                "    joiningdate datetime NOT NULL\n" +
                ");"
        );
    }
}