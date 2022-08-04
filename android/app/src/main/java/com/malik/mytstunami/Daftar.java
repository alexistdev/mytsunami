package com.malik.mytstunami;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Daftar extends AppCompatActivity {
//    private EditText mEmail,mPassword,mNama;
//    private Button mDaftar;
//    private ProgressDialog pDialog;
    private TextView mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        init();
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Daftar.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void init() {
        mLogin = findViewById(R.id.btn_login);
//        mNama = findViewById(R.id.txt_nama);
//        mDaftar = findViewById(R.id.btn_daftar);
//        mEmail = findViewById(R.id.txt_email);
//        mPassword = findViewById(R.id.txt_password);
//        pDialog = new ProgressDialog(this);
//        pDialog.setCancelable(false);
//        pDialog.setMessage("Loading.....");
    }
}