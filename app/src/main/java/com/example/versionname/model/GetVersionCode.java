package com.example.versionname.model;

import android.os.AsyncTask;

import org.jsoup.Jsoup;

public class GetVersionCode extends AsyncTask<Void, String, String> {
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