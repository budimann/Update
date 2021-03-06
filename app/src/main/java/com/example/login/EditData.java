package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class EditData extends AppCompatActivity {
    private TextView txt_id;
    private EditText txt_nama;
    private EditText txt_pesanan;
    private EditText txt_no_hp;
    private EditText txt_alamat;
    private Button btn_edit;
    private Button btn_hapus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        //layout dulu
        //nangkep
        //id pasang di textview
        //lain2 pasang di edittext
        //button kirim implemnasti API update.php
        //
        Bundle extras = getIntent().getExtras();
        final String id = extras.getString("id");
        final String nama = extras.getString("nama");
        final String pesanan = extras.getString("pesanan");
        final String no_hp = extras.getString("no-hp");
        final String alamat = extras.getString("alamat");

        txt_id = findViewById(R.id.id);
        txt_nama = findViewById(R.id.nama);
        txt_pesanan = findViewById(R.id.pesanan);
        txt_no_hp = findViewById(R.id.no_hp);
        txt_alamat = findViewById(R.id.alamat);
        btn_edit = findViewById(R.id.btn_edit);
        btn_hapus = findViewById(R.id.btn_hapus);
        btn_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                AndroidNetworking.post(BaseURL.url + "delete.php")
                                        .addBodyParameter("id", txt_id.getText().toString())
                                        .setPriority(Priority.LOW)
                                        .build()
                                        .getAsJSONObject(new JSONObjectRequestListener() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                Log.e("", response.toString());
                                                try {
                                                    JSONObject hasil = response.getJSONObject("hasil");
                                                    boolean sukses = hasil.getBoolean("respon");
                                                    if (sukses) {
                                                        Intent returnIntent = new Intent();
                                                        returnIntent.putExtra("refresh", "refresh");
                                                        setResult(23, returnIntent);
                                                        finish();
                                                        Toast.makeText(EditData.this, "Delete Suskses", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(EditData.this, "Delete gagal", Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                    System.out.println("ttttt" + e.getMessage());
                                                    Toast.makeText(EditData.this, "Delete gagal", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onError(ANError anError) {

                                            }
                                        });
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(EditData.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });


        txt_id.setText(id);
        txt_nama.setText(nama);
        txt_pesanan.setText(pesanan);
        txt_no_hp.setText(no_hp);
        txt_alamat.setText(alamat);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidNetworking.post(BaseURL.url + "update.php")
                        .addBodyParameter("id", txt_id.getText().toString())
                        .addBodyParameter("nama", txt_nama.getText().toString())
                        .addBodyParameter("pesanan", txt_pesanan.getText().toString())
                        .addBodyParameter("No_hp", txt_no_hp.getText().toString())
                        .addBodyParameter("alamat", txt_alamat.getText().toString())
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.e("", response.toString());
                                try {
                                    JSONObject hasil = response.getJSONObject("hasil");
                                    boolean sukses = hasil.getBoolean("respon");
                                    if (sukses) {
                                        Intent returnIntent = new Intent();
                                        returnIntent.putExtra("refresh", "refresh");
                                        setResult(23, returnIntent);
                                        finish();
                                        Toast.makeText(EditData.this, "Edit Suskses", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(EditData.this, "Edit gagal", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    System.out.println("ttttt" + e.getMessage());
                                    Toast.makeText(EditData.this, "Edit gagal", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(EditData.this, anError.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

    }
}
