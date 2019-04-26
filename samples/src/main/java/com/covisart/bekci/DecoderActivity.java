package com.covisart.bekci;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView.OnQRCodeReadListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import data.covisart.bekci.Database;

public class DecoderActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback, OnQRCodeReadListener {

    private static final int MY_PERMISSION_REQUEST_CAMERA = 0;

    private ViewGroup mainLayout;

    private TextView resultTextView;
    private QRCodeReaderView qrCodeReaderView;
    private CheckBox flashlightCheckBox;
    private CheckBox enableDecodingCheckBox;
    private PointsOverlayView pointsOverlayView;
    private BackgroundMail bm;
    private Display display;
    private Point size;
    private Button mailButton;
    public Database db;
    ArrayList<HashMap<String, String>> work_list;
    HashMap<String, String> work_detay;
    private String Name;

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoder);

        db = new Database(this);
        mainLayout = findViewById(R.id.main_layout);
        bm = new BackgroundMail(this);
        bm.setGmailUserName("bekci@covisart.com");
        bm.setGmailPassword("*Nobat21*");
        bm.setMailTo("nobatgeldi@outlook.com");
        bm.setFormSubject("Bekci");
        bm.setFormBody("Bekci Denetimi");
        bm.setSendingMessage("Mesaj Gönderiliyor");
        bm.setSendingMessageSuccess("Mesaj Gönderildi");

        display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            initQRCodeReaderView();
        } else {
            requestCameraPermission();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (qrCodeReaderView != null) {
            qrCodeReaderView.startCamera();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (qrCodeReaderView != null) {
            qrCodeReaderView.stopCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != MY_PERMISSION_REQUEST_CAMERA) {
            return;
        }

        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Snackbar.make(mainLayout, "Camera permission was granted.", Snackbar.LENGTH_SHORT).show();
            initQRCodeReaderView();
        } else {
            Snackbar.make(mainLayout, "Camera permission request was denied.", Snackbar.LENGTH_SHORT)
                    .show();
        }
    }

    // Called when a QR is decoded
    // "text" : the text encoded in QR
    // "points" : points where QR control points are placed
    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        resultTextView.setText(text);
        Name = text;
        mailButton.setVisibility(View.VISIBLE);
        //bm.setFormBody("Bekci Geldi Gitti:" + text);
        pointsOverlayView.setPoints(points);
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Snackbar.make(mainLayout, "Camera access is required to display the camera preview.",
                    Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(DecoderActivity.this, new String[]{
                            Manifest.permission.CAMERA
                    }, MY_PERMISSION_REQUEST_CAMERA);
                }
            }).show();
        } else {
            Snackbar.make(mainLayout, "Permission is not available. Requesting camera permission.",
                    Snackbar.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA
            }, MY_PERMISSION_REQUEST_CAMERA);
        }
    }

    private void initQRCodeReaderView() {
        View content = getLayoutInflater().inflate(R.layout.content_decoder, mainLayout, true);

        qrCodeReaderView = content.findViewById(R.id.qrdecoderview);
        resultTextView = content.findViewById(R.id.result_text_view);
        flashlightCheckBox = content.findViewById(R.id.flashlight_checkbox);
        enableDecodingCheckBox = content.findViewById(R.id.enable_decoding_checkbox);
        pointsOverlayView = content.findViewById(R.id.points_overlay_view);
        mailButton = content.findViewById(R.id.button);

        qrCodeReaderView.setAutofocusInterval(2000L);
        qrCodeReaderView.setOnQRCodeReadListener(this);
        qrCodeReaderView.setBackCamera();

        flashlightCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                qrCodeReaderView.setTorchEnabled(isChecked);
            }
        });
        enableDecodingCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                qrCodeReaderView.setQRDecodingEnabled(isChecked);
            }
        });
        qrCodeReaderView.startCamera();
    }

    public void SendMail(View v) {
        try {
            Date c = Calendar.getInstance().getTime();

            SimpleDateFormat tarih = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat saat = new SimpleDateFormat("kk:mm:ss");

            String formattedtarih = tarih.format(c);
            String formattedsaat = saat.format(c);

            if (addToWork(Name, formattedtarih, formattedsaat, "38.681134,27.312042")) {
                mailButton.setVisibility(View.INVISIBLE);
            } else {
                Toast.makeText(this, "Kayıt Başarısız oldu. Tekrar deneyin.", Toast.LENGTH_LONG).show();
            }
        } catch (Exception exp) {
            Log.println(Log.ERROR, "Exp:", exp.toString());
        }
    }

    public boolean addToWork(String WORK_Name, String WORK_Tarih, String WORK_Saat, String WORK_Konum) {
        if (WORK_Name.matches("") || WORK_Tarih.matches("") || WORK_Saat.matches("") || WORK_Konum.matches("")) {
            Toast.makeText(this, "Tüm Bilgileri Eksiksiz Doldurunuz", Toast.LENGTH_LONG).show();
            return false;
        } else {
            db.WORK_Ekle(WORK_Name, WORK_Tarih, WORK_Saat, WORK_Konum);

            //db.resetTables();
            db.close();
            Toast.makeText(this, "İş Başarıyla Kayıt Edildi. Row: " + db.getRowCount(), Toast.LENGTH_LONG).show();
            return true;
        }
    }
}