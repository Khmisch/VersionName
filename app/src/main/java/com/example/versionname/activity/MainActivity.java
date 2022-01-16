package com.example.versionname.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.versionname.BuildConfig;
import com.example.versionname.R;
import com.example.versionname.model.GetVersionCode;

import org.jsoup.Jsoup;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    String currentVersion = BuildConfig.VERSION_NAME;
    String onlineVersion;
    String str1, str2;
    TextView tv_current, tv_online;
    Button bt_open, bt_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

    }

    @SuppressLint("SetTextI18n")
    private void initViews() {
        tv_current = findViewById(R.id.tv_current);
        tv_online = findViewById(R.id.tv_online);
        bt_open = findViewById(R.id.bt_open);
        bt_update = findViewById(R.id.bt_update);

        GetVersionCode getVersionCode = new GetVersionCode();
        try {
            onlineVersion = getVersionCode.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        tv_online.setText("Online version: "+onlineVersion);
        tv_current.setText("Current version: "+currentVersion);

        compareAppVersion();

    }

    private void compareAppVersion() {
        if (onlineVersion != null && !onlineVersion.isEmpty()) {
            str1 = currentVersion.replace(".", "");
            str2 = onlineVersion.replace(".", "");

            if (Integer.parseInt(str1.substring(0, str1.length() - 1))
                    != Integer.parseInt(str2.substring(0,str2.length()-1))) {
                bt_update.setVisibility(View.VISIBLE);
                bt_open.setVisibility(View.INVISIBLE);
                bt_update.setOnClickListener(view -> updateApp());

            } else {
                bt_open.setVisibility(View.VISIBLE);
                bt_open.setOnClickListener(view -> callDetailsActivity());
            }
        }
    }
    private void updateApp() {
        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
        intent.putExtra("update","Successfully updated" );
        startActivity(intent);
    }
    private void callDetailsActivity() {
        Intent intent = new Intent(this, DetailsActivity.class);
        startActivity(intent);
    }

}