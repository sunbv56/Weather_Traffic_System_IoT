package com.example.myapplication.main.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;


public class ShowChart extends AppCompatActivity {
    WebView tempChart;
    WebView humChart;
    WebView rainChart;
    WebView airChart;
    WebView lightChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_chart);

        Button buttonHome = findViewById(R.id.btnHome);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowChart.this, MainActivity.class);
                startActivity(intent);
            }
        });

        tempChart = findViewById(R.id.tempChart);
        tempChart.setWebViewClient(new WebViewClient());
        tempChart.loadUrl("https://thingspeak.com/channels/2332757/charts/1?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=30&title=Nhi%C3%AA%CC%A3t+%C4%91%C3%B4%CC%A3&type=line");
        WebSettings tempChartSettings = tempChart.getSettings();
        tempChartSettings.setJavaScriptEnabled(true);

        humChart = findViewById(R.id.humChart);
        humChart.setWebViewClient(new WebViewClient());
        humChart.loadUrl("https://thingspeak.com/channels/2332757/charts/2?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=30&title=%C4%90%C3%B4%CC%A3+%C3%A2%CC%89m&type=line");
        WebSettings humChartSettings = humChart.getSettings();
        humChartSettings.setJavaScriptEnabled(true);

        rainChart = findViewById(R.id.rainChart);
        rainChart.setWebViewClient(new WebViewClient());
        rainChart.loadUrl("https://thingspeak.com/channels/2332757/charts/3?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=30&title=M%C6%B0a&type=line");
        WebSettings rainChartSettings = rainChart.getSettings();
        rainChartSettings.setJavaScriptEnabled(true);

        lightChart = findViewById(R.id.lightChart);
        lightChart.setWebViewClient(new WebViewClient());
        lightChart.loadUrl("https://thingspeak.com/channels/2332757/charts/4?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=30&title=C%C6%B0%C6%A1%CC%80ng+%C4%91%C3%B4%CC%A3+a%CC%81nh+sa%CC%81ng&type=line");
        WebSettings lightChartSettings = lightChart.getSettings();
        lightChartSettings.setJavaScriptEnabled(true);

        airChart = findViewById(R.id.airChart);
        airChart.setWebViewClient(new WebViewClient());
        airChart.loadUrl("https://thingspeak.com/channels/2332757/charts/5?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=30&title=Ch%C3%A2%CC%81t+l%C6%B0%C6%A1%CC%A3ng+kh%C3%B4ng+khi%CC%81&type=line");
        WebSettings airChartSettings = airChart.getSettings();
        airChartSettings.setJavaScriptEnabled(true);

    }
    private class TempClient extends WebViewClient{
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }
    }


}

