package com.example.vibraniumtutor;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    Button btnlogin, btnsignup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = findViewById(R.id.Ac_main);
        btnlogin = findViewById(R.id.btn_main_login);
        btnsignup = findViewById(R.id.btn_main_signup);

        intentOpen();

        animdraw();
    }

    private void intentOpen() {
        btnlogin.setOnClickListener(v -> {
            btnlogin.setFocusable(true);
            btnlogin.requestFocus();

            Intent intent = new Intent(MainActivity.this, Login_Activity.class);
            startActivity(intent);
            finish();
        });

        btnsignup.setOnClickListener(v -> {
            btnsignup.setFocusable(true);
            btnsignup.requestFocus();

            Intent intent = new Intent(MainActivity.this, Register_Activity.class);
            startActivity(intent);
            finish();
        });
    }

    private void animdraw() {
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();
    }
}