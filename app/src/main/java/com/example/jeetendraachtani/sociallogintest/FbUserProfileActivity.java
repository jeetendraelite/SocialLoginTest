package com.example.jeetendraachtani.sociallogintest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FbUserProfileActivity extends AppCompatActivity {


    @BindView(R.id.iv_profile_pic11)
    ImageView profile_pic;
    @BindView(R.id.tv_email11)
    TextView email;
    @BindView(R.id.tv_username11)
    TextView username;


    JSONObject response,profile_pic_data,profile_picUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fb_user_profile);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        email.setVisibility(View.GONE);
        String jsondata = intent.getStringExtra("userProfile");
        Log.d("JSONDATA",jsondata);
        try{
            response= new JSONObject(jsondata);
            username.setText(response.get("email").toString());
            profile_pic_data= new JSONObject(response.get("picture").toString());
            profile_picUrl = new JSONObject(profile_pic_data.getString("data"));
            Glide.with(this).load(profile_picUrl.getString("url")).into(profile_pic);



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
