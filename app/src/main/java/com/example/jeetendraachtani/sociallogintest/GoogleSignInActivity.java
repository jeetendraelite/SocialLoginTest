package com.example.jeetendraachtani.sociallogintest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoogleSignInActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.profile_section)
     LinearLayout prof_section;

    @BindView(R.id.btn_logout)
    Button SignOut;

    @BindView(R.id.btn_login)
    SignInButton signInButton;

    @BindView(R.id.name)
    TextView Name;

    @BindView(R.id.email)
    TextView Email;

    @BindView(R.id.profile_pic)
    ImageView imageView;

    GoogleApiClient googleApiClient;
    private static final int REQ_CODE= 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_sign_in);
        ButterKnife.bind(this);
        signInButton.setOnClickListener(this);
        SignOut.setOnClickListener(this);
        prof_section.setVisibility(View.GONE);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();
        googleApiClient= new GoogleApiClient.Builder(this).enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                SignIn();
                break;

            case R.id.btn_logout:
                SignOut();
                break;
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void SignIn(){
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_CODE);

    }

    private void SignOut(){
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {

            @Override
            public void onResult(@NonNull Status status) {
                UpdateUI(false);
            }
        });

    }

    private void HandleResult(GoogleSignInResult result){
                if(result.isSuccess()){
                    ButterKnife.bind(this);
                    GoogleSignInAccount  account = result.getSignInAccount();
                    String name = account.getDisplayName();
                    String email= account.getEmail();
                    String img_url= account.getPhotoUrl().toString();
                    Name.setText(name);
                    Email.setText(email);
                    Glide.with(this).load(img_url).into(imageView);
                    UpdateUI(true);

                }
                else{
                    UpdateUI(false);
                }

    }

    private void UpdateUI(boolean isLogin){
        if(isLogin){
            prof_section.setVisibility(View.VISIBLE);
            signInButton.setVisibility(View.GONE);
        }
        else{
            prof_section.setVisibility(View.GONE);
            signInButton.setVisibility(View.VISIBLE);
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            HandleResult(result);

        }

    }
}
