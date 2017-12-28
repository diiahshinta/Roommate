package com.example.diahshintadewi.roommate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.diahshintadewi.roommate.account.Login;

/**
 * Created by Diah Shinta Dewi on 12/27/2017.
 */

public class Splashscreen extends Activity {
    private static int splashInterval = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splashscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splashscreen.this, Login.class);
                startActivity(i);
                this.finish();
            }

            private void finish() {
            }
        }, splashInterval);
    }
}
