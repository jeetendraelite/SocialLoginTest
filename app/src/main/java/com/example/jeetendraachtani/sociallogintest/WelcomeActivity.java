package com.example.jeetendraachtani.sociallogintest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jeetendraachtani.sociallogintest.Facebook.FBLoginActivity;
import com.example.jeetendraachtani.sociallogintest.Twitter.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

   @BindView(R.id.btn_facebook_login)
   Button facebook_btn;

   @BindView(R.id.btn_google_login)
   Button google_btn;

    @BindView(R.id.btn_twitter_login)
    Button twitter_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        facebook_btn.setOnClickListener(this);
        google_btn.setOnClickListener(this);
        twitter_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        ButterKnife.bind(this);
        switch (view.getId()){
            case R.id.btn_facebook_login:
                startActivity(new Intent(WelcomeActivity.this,FBLoginActivity.class));
                break;
            case R.id.btn_google_login:
                startActivity(new Intent(WelcomeActivity.this,GoogleSignInActivity.class));
                break;
            case R.id.btn_twitter_login:
                startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
                break;
        }
    }
}
