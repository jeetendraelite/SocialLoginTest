package com.example.jeetendraachtani.sociallogintest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

   @BindView(R.id.btn_facebook_login)
   Button facebook_btn;

   @BindView(R.id.btn_google_login)
   Button google_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        facebook_btn.setOnClickListener(this);
        google_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_facebook_login:
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                break;
            case R.id.btn_google_login:
                startActivity(new Intent(WelcomeActivity.this,GoogleSignInActivity.class));
                break;
        }
    }
}
