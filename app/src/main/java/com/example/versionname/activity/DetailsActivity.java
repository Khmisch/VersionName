package com.example.versionname.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.versionname.R;

public class DetailsActivity extends AppCompatActivity {
    TextView tv_updated;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initViews();
    }

    private void initViews() {
        tv_updated = findViewById(R.id.tv_updated);
        Intent intent = getIntent();
        String str = intent.getStringExtra("update");
        tv_updated.setText(str);
    }

}