package com.covisart.bekci;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import data.covisart.bekci.Database;
import data.covisart.bekci.Kayit;

public class ReportData extends AppCompatActivity {

    private static String WORK_ID = "id";
    private static String WORK_Name = "adi";
    private static String WORK_Tarih = "tarih";
    private static String WORK_Saat = "saat";
    private static String WORK_Konum = "konum";
    public ListView dataShow;
    public Database db;
    public Kayit mailData;
    public boolean mailState = false;
    Context mContext;
    ArrayList<HashMap<String, String>> workList, mailList;
    HashMap dataMail;
    ListAdapter adapter;
    String mailTO;
    private BackgroundMail bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_data);

        mContext = this;
        db = new Database(this);
        mailData = new Kayit(this);

        dataShow = findViewById(R.id.ListeView);

        workList = db.WORK_LIST();
        adapter = new SimpleAdapter(this, workList, R.layout.list_row, new String[]{"adi", "tarih", "saat", "konum"}, new int[]{R.id.kimlik, R.id.tarih, R.id.saat, R.id.location});
        dataShow.setAdapter(adapter);
        /*mailList = mailData.WORK_LIST();
        try
        {
            dataMail = mailList.get(0);
            mailState= true;
            Toast.makeText(this, "Yönetici maili kayıtlı", Toast.LENGTH_LONG).show();
        }catch (Exception exp){ Toast.makeText(this, "Yönetici maili kayıtlı değil", Toast.LENGTH_LONG).show();}

        if (mailState)
        {
            adapter = new SimpleAdapter(this, workList, R.layout.list_row, new String[]{"adi", "tarih", "saat", "konum"}, new int[]{R.id.kimlik, R.id.tarih, R.id.saat, R.id.location});
            dataShow.setAdapter(adapter);
            //Log.println(Log.ERROR, "List", workList.toString());
            mailTO = dataMail.get("mail").toString();
            bm = new BackgroundMail(this);
            bm.setGmailUserName("bekci@covisart.com");
            bm.setGmailPassword("*Nobat21*");
            bm.setMailTo(mailTO);
            bm.setFormSubject("Bekci");
            bm.setFormBody("Bekci Denetimi");
            bm.setSendingMessage("Mesaj Gönderiliyor");
            bm.setSendingMessageSuccess("Mesaj Gönderildi");
        }*/
    }

    public void SendData(View v) {
        String dataset = "";

        for (HashMap data : workList) {
            String saved = "Daire:" + getData(data, WORK_Name) + " Tarih:" + getData(data, WORK_Tarih) + " Saat:" + getData(data, WORK_Saat) + " Konum:" + getData(data, WORK_Konum) + "y";
            dataset = dataset + saved;
        }

        //String sUrl = "https://covisart.com/wp-content/service/AppService.php?mail = " + mailTO  + "&data=" + dataset;
        String sUrl = "https://covisart.com/wp-content/service/AppService.php?data=" + dataset;
        Log.println(Log.ERROR, "HashMap: ", sUrl);

        new GetUrlContentTask(this).execute(sUrl);
    }

    public String getData(HashMap data, String key) {
        try {
            return data.get(key).toString();

        } catch (Exception exp) {
            return null;
        }
    }
}

class GetUrlContentTask extends AsyncTask<String, Integer, String> {
    public Database db;
    Context mContext;
    ProgressDialog pd;


    public GetUrlContentTask(Context context) {
        this.mContext = context;
    }

    protected String doInBackground(String... urls) {
        URL url = null;
        try {
            url = new URL(urls[0]);
            Log.println(Log.ERROR, "doInBackground:", " url = new URL(urls[0]);");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            Log.println(Log.ERROR, "doInBackground:", " (HttpURLConnection) url.openConnection()");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            connection.setRequestMethod("GET");
            Log.println(Log.ERROR, "doInBackground:", " connection.setRequestMethod(GET);");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        connection.setDoOutput(true);
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        try {
            connection.connect();
            Log.println(Log.ERROR, "doInBackground:", " connection.connect();");
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader rd = null;
        try {
            rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            Log.println(Log.ERROR, "doInBackground:", " BufferedReader");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String content = "", line;
        try {
            line = rd.readLine();
            Log.println(Log.ERROR, "doInBackground:", " rd.readLine()");
            content += line + "\n";
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.println(Log.ERROR, "doInBackground:", content);
        return content;
    }

    protected void onProgressUpdate(Integer... progress) {
    }

    @Override
    protected void onPreExecute() {
        db = new Database(mContext);
        pd = new ProgressDialog(mContext);

        pd.setMessage("Gönderiliyor...");
        pd.setCancelable(false);
        pd.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        Log.println(Log.ERROR, "onPostExecute:", " Entered method");
        Toast.makeText(mContext, "Your message was sent successfully.", Toast.LENGTH_SHORT).show();
        db.resetTables();
        pd.dismiss();
        mContext.startActivity(new Intent(mContext, MainActivity.class));
        super.onPostExecute(result);
    }
}
