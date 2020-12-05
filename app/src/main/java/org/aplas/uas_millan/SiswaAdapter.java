package org.aplas.uas_millan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class SiswaAdapter extends ArrayAdapter<Siswa> {
    Context mCtx;
    int listLayoutRes;
    List<Siswa> siswaList;
    SQLiteDatabase mDatabase;

    public SiswaAdapter(Context mCtx, int listLayoutRes, List<Siswa>siswaList, SQLiteDatabase mDatabase){
        super(mCtx, listLayoutRes, siswaList);

        this.mCtx=mCtx;
        this.listLayoutRes=listLayoutRes;
        this.siswaList=siswaList;
        this.mDatabase=mDatabase;
    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(listLayoutRes, null);

        //getting siswa ke posisi spesifik
        final Siswa siswa = siswaList.get(position);

        //getting views
        TextView textViewNama= view.findViewById(R.id.textViewNama);
        TextView textViewNomer= view.findViewById(R.id.textViewNomer);
        TextView textViewKelas= view.findViewById(R.id.textViewKelas);
        TextView textViewHasilNilaiAkhir = view.findViewById(R.id.textViewNilaiAkhir);
        TextView textViewJoiningDate = view.findViewById(R.id.textViewJoiningDate);

        //menambahkan data ke viewsnya
        textViewNama.setText((siswa.getNama()));
        textViewNomer.setText((siswa.getNomer()));
        textViewKelas.setText((siswa.getKelas()));
        textViewHasilNilaiAkhir.setText((siswa.getNilaiakhir()));
        textViewJoiningDate.setText((siswa.getJoiningdate()));

        //penggunaan tombol update dan hapus
        Button buttonDelete = view.findViewById(R.id.bHapus);
        Button buttonUpdate = view.findViewById(R.id.bEdit);
        Button buttonLihat = view.findViewById(R.id.bLihat);

        //adding a clicklistener to button
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSiswa(siswa);
            }
        });

        buttonLihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lihatSiswa(siswa);
            }
        });

        //the delete operation
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                builder.setTitle("Yakin ingin menghapus data ini?");
                builder.setPositiveButton("Yakin", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String sql = "DELETE FROM siswa WHERE id = ?";
                        mDatabase.execSQL(sql, new Integer[]{siswa.getId()});
                        reloadSiswaFromDatabase();
                    }
                });
                builder.setNegativeButton("Batalkan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return  view;
    }

    private void lihatSiswa(Siswa siswa) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.detail_siswa, null);
        builder.setView(view);

        TextView textNama = view.findViewById(R.id.TextNama);
        TextView textNomer = view.findViewById(R.id.TextNomer);
        TextView textKelas = view.findViewById(R.id.TextKelas);
        TextView textSemester = view.findViewById(R.id.TextSemester);
        TextView textNilaiKehadiran = view.findViewById(R.id.TextNilaiHadir);
        TextView textNilaiTugas = view.findViewById(R.id.TextNilaiTugas);
        TextView textNilaiUts = view.findViewById(R.id.TextNilaiUTS);
        TextView textNilaiUas = view.findViewById(R.id.TextNilaiUAS);
        TextView textNilaiAkhir = view.findViewById(R.id.TextNilaiAkhir);

        textNama.setText(siswa.getNama());
        textKelas.setText(siswa.getKelas());
        textNomer.setText(siswa.getNomer());
        textSemester.setText(siswa.getSemester());
        textNilaiKehadiran.setText(siswa.nilaikehadiran);
        textNilaiTugas.setText(siswa.getNilaitugas());
        textNilaiUts.setText(siswa.getNilaiuts());
        textNilaiUas.setText(siswa.getNilaiuas());
        textNilaiAkhir.setText(siswa.getNilaiakhir());

        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateSiswa(final Siswa siswa){
        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.update_siswa, null);
        builder.setView(view);

        final EditText editTextNama = view.findViewById(R.id.editTextNama);
        final EditText editTextNomer = view.findViewById(R.id.editTextNomer);
        final EditText editTextKelas = view.findViewById(R.id.editTextKelas);
        final Spinner spinnerSemester = view.findViewById(R.id.spinnerSemester);
        final EditText editTextNilaiKehadiran = view.findViewById(R.id.editTextNilaiHadir);
        final EditText editTextNilaiTugas = view.findViewById(R.id.editTextNilaiTugas);
        final EditText editTextNilaiUTS = view.findViewById(R.id.editTextNilaiUTS);
        final EditText editTextNilaiUAS = view.findViewById(R.id.editTextNilaiUAS);
        final TextView textViewHasilNilaiAkhir = view.findViewById(R.id.textViewHasilNilaiAkhir);
        Button bEdit = view.findViewById(R.id.bEdit);

        editTextNama.setText(siswa.getNama());
        editTextNomer.setText(siswa.getNomer());
        editTextKelas.setText(siswa.getKelas());
        editTextNilaiKehadiran.setText(siswa.getNilaikehadiran());
        editTextNilaiTugas.setText(siswa.getNilaitugas());
        editTextNilaiUTS.setText(siswa.getNilaiuts());
        editTextNilaiUAS.setText(siswa.getNilaiuas());
        textViewHasilNilaiAkhir.setText(siswa.getNilaiakhir());


        final AlertDialog dialog = builder.create();
        dialog.show();

        view.findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                String nama = editTextNama.getText().toString().trim();
                String kelas = editTextKelas.getText().toString().trim();
                String nomer = editTextNomer.getText().toString().trim();
                String nilaikehadiran = editTextNilaiKehadiran.getText().toString().trim();
                String nilaitugas = editTextNilaiTugas.getText().toString().trim();
                String nilaiuts = editTextNilaiUTS.getText().toString().trim();
                String nilaiuas = editTextNilaiUAS.getText().toString().trim();
                String semester = spinnerSemester.getSelectedItem().toString();
                String nilaiAkhir = Siswa.hitungNilaiAkhir(nilaikehadiran, nilaitugas, nilaiuts, nilaiuas);

                if (nama.isEmpty()) {
                    editTextNama.setError("Nama tidak boleh kosong!");
                    editTextNama.requestFocus();
                    return;
                }

                if (nomer.isEmpty()) {
                    editTextNomer.setError("NISN tidak boleh kosong!");
                    editTextNomer.requestFocus();
                    return;
                }
                if (kelas.isEmpty()) {
                    editTextKelas.setError("Kelas tidak boleh kosong!");
                    editTextKelas.requestFocus();
                    return;
                }
                if (nilaikehadiran.isEmpty()) {
                    editTextNilaiKehadiran.setError("Nilai Kehadiran tidak boleh kosong!");
                    editTextNilaiKehadiran.requestFocus();
                    return;
                }
                if (nilaitugas.isEmpty()) {
                    editTextNilaiTugas.setError("Nilai Kehadiran tidak boleh kosong!");
                    editTextNilaiTugas.requestFocus();
                    return;
                }
                if (nilaiuts.isEmpty()) {
                    editTextNilaiUTS.setError("Nilai Kehadiran tidak boleh kosong!");
                    editTextNilaiUTS.requestFocus();
                    return;
                }
                if (nilaiuas.isEmpty()) {
                    editTextNilaiUAS.setError("Nilai Kehadiran tidak boleh kosong!");
                    editTextNilaiUAS.requestFocus();
                    return;
                }
                String sql = "UPDATE siswa \n" +
                        "SET nama = ?, \n" +
                        "nomer = ?, \n" +
                        "kelas = ?, \n" +
                        "semester = ?, \n" +
                        "nilaikehadiran = ?, \n" +
                        "nilaitugas = ?, \n" +
                        "nilaiuts = ?, \n" +
                        "nilaiuas = ? \n," +
                        "nilaiakhir = ? \n" +
                        "WHERE id = ?;\n";

                mDatabase.execSQL(sql, new String[]{nama, nomer, kelas, semester, nilaikehadiran, nilaitugas, nilaiuts, nilaiuas, nilaiAkhir, String.valueOf(siswa.getId())});
                Toast.makeText(mCtx, "Data Siswa Berhasil Diperbarui !", Toast.LENGTH_SHORT).show();
                reloadSiswaFromDatabase();

                dialog.dismiss();
            }
        });
    }

    private void reloadSiswaFromDatabase() {
        Cursor cursorSiswa = mDatabase.rawQuery("SELECT * FROM siswa", null);
        if (cursorSiswa.moveToFirst()) {
            siswaList.clear();
            do {
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
        cursorSiswa.close();
        notifyDataSetChanged();
    }
}
