package com.techease.pacific.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.techease.pacific.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ((AppCompatActivity) this).getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

            Thread timer = new Thread() {
                public void run() {
                    try {
                        sleep(3000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {

                        startActivity(new Intent(MainActivity.this,FullscreenActivity.class));
                        finish();
                    }
                }
            };
            timer.start();
    }
}
