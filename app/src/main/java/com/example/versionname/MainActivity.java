package com.example.versionname;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    String currentVersion = BuildConfig.VERSION_NAME;
    String onlineVersion;
    TextView tv_current, tv_online;
    Button bt_open, bt_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

    }

    private void initViews() {
        tv_current = findViewById(R.id.tv_current);
        tv_online = findViewById(R.id.tv_online);
        tv_current.setText("Current version: "+currentVersion);

        GetVersionCode getVersionCode = new GetVersionCode();
        try {
            onlineVersion = getVersionCode.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        tv_online.setText("Online version: "+onlineVersion);

        if(compare(currentVersion,onlineVersion)){
            bt_open.setVisibility(View.VISIBLE);
            bt_open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callDetailsActivity();
                }
            });
        }
    }

    private void callDetailsActivity() {
        Intent intent = new Intent(this, DetailsActivity.class);
        startActivity(intent);
    }


    private boolean compare(String currentVersion, String onlineVersion) {
        String str1 = currentVersion.replace(".","");
        String str2 = currentVersion.replace(".","");
        if (onlineVersion != null && !onlineVersion.isEmpty()) {
            if (Float.parseFloat(str1) < Float.parseFloat(str2)) {
                return false;
            }
        }
        return false;
    }


    @SuppressLint("StaticFieldLeak")
    private static class GetVersionCode extends AsyncTask<Void, String, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String newVersion = null;
            try {
                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + "net.orizinal.subway" + "&hl=en&gl=US")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select(".hAyfc .htlgb")
                        .get(7)
                        .ownText();
                return newVersion;
            } catch (Exception e) {
                return newVersion;
            }

        }
    }
}