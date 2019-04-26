package com.covisart.bekci;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import data.covisart.bekci.Database;

public class MainActivity extends AppCompatActivity {

    public Database db;
    Button report, read, save;
    TextView mailText;
    //public Kayit mail;
    //ArrayList<HashMap<String, String>> workList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new Database(this);
        report = findViewById(R.id.report);
        read = findViewById(R.id.read);
    }

    public void openReader(View v) {
        Intent intent = new Intent(getApplicationContext(), DecoderActivity.class);
        startActivity(intent);
    }

    public void openReport(View v) {
        Intent intent = new Intent(getApplicationContext(), ReportData.class);
        startActivity(intent);
    }

    public void saveMail(View v) {
        String content = mailText.getText().toString();
        //mail.WORK_Ekle(content);
        Toast.makeText(this, "Yönetici mail adresi başarı ile kayıt edildi", Toast.LENGTH_LONG).show();
    }

    public void deleteMail(View v) {
        //mail.resetTables();
        Toast.makeText(this, "Yönetici mail adresi başarı ile silindi", Toast.LENGTH_LONG).show();
    }
}
