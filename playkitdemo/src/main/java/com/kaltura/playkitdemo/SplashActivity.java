package com.kaltura.playkitdemo;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.kaltura.playkit.PlayKitManager;
import com.kaltura.playkit.Player;
import com.kaltura.playkit.plugins.SamplePlugin;
import com.kaltura.playkitdemo.data.JsonFetchTask;
import com.kaltura.playkitdemo.jsonConverters.ConverterPlayKitApp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import static com.kaltura.playkit.PlayKitManager.registerPlugins;

public class SplashActivity extends AppCompatActivity {


    public static final String CONVERTER_PLAY_KIT_APP = "CONVERTER_PLAY_KIT_APP";

    private static final int DELAY_SHOW_SPLASH_SCREEN = 2000;
    private static final String LOCAL_JSON_FILE_NAME = "playKitApp.json";


    private Date mStartTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        registerPlugins();
        fetchAppData();
    }


    private void fetchAppData() {

        Intent intent = getIntent();
        String url;

        mStartTime = new Date();

        if ((url = intent.getDataString()) != null) { // app was invoked via deep linking
            fetchDeepLink(url);
        } else { // app invocation done by clicking the app icon from device home screen
            fetchLocal();
        }
    }



    private void fetchLocal() {

        InputStream inputStream = null;

        try {

            inputStream = getApplicationContext().getAssets().open(LOCAL_JSON_FILE_NAME);

            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);

            startMainActivity(new String(buffer, "UTF-8"));

        } catch (IOException ex) {

            ex.printStackTrace();

        } finally {

            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }



    private void fetchDeepLink(String url) {

        new JsonFetchTask(SplashActivity.this, new JsonFetchTask.OnJsonFetchedListener() {

            @Override
            public void onJsonFetched(String json) {

                startMainActivity(json);

            }

        }).execute(url);
    }




    private void startMainActivity(String json) {

        if (TextUtils.isEmpty(json)) {
            showMessage(R.string.something_went_wrong);
            return;
        }


        final ConverterPlayKitApp playKitApp = new Gson().fromJson(json, ConverterPlayKitApp.class);

        Date currentTime = new Date();
        final long diffTime = currentTime.getTime() - mStartTime.getTime();


        Thread thread = new Thread(new Runnable() {

            public void run() {

                try {
                    // we want to show the splash screen for at least 2 seconds
                    long sleepTime = DELAY_SHOW_SPLASH_SCREEN - diffTime;
                    Thread.sleep(sleepTime > 0 ? sleepTime : 0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.putExtra(CONVERTER_PLAY_KIT_APP, playKitApp);
                startActivity(intent);
            }
        });

        thread.start();

    }


    private void showMessage(int string) {
        ImageView itemView = (ImageView) findViewById(R.id.imageView);
        Snackbar snackbar = Snackbar.make(itemView, string, Snackbar.LENGTH_LONG);
        snackbar.show();
    }


    private void registerPlugins() {
        PlayKitManager.registerPlugins(SamplePlugin.factory);
    }

}
