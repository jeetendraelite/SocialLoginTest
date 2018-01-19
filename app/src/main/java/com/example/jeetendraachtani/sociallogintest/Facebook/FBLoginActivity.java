package com.example.jeetendraachtani.sociallogintest.Facebook;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jeetendraachtani.sociallogintest.MainActivity;
import com.example.jeetendraachtani.sociallogintest.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.util.Arrays.asList;

public class FBLoginActivity extends AppCompatActivity {

    private CallbackManager mCallbackManager;
/*
   // @BindView(R.id.user_linearLayout)
   // LinearLayout layout;

    @BindView(R.id.iv_profile_pic)
    ImageView profile_pic;
    @BindView(R.id.tv_email)
    TextView email;
    @BindView(R.id.tv_username)
    TextView username;*/

    TextView name,email;
    ImageView profile_pic;
    LinearLayout layout;
    Button btn_fb_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_fblogin);
        mCallbackManager = CallbackManager.Factory.create();

        layout=findViewById(R.id.user_linearLayout);
        profile_pic=findViewById(R.id.iv_profile_pic);
        email=findViewById(R.id.tv_email);
       name=findViewById(R.id.tv_name);
        btn_fb_login = (Button)findViewById(R.id.fb_login);


        btn_fb_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    LoginManager.getInstance().logInWithReadPermissions(FBLoginActivity.this,
                            Arrays.asList("public_profile","user_friends", "email"));

                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success","Login");


                        GraphRequest data_Request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {

                                        JSONObject jsonresponse,profile_pic_data,profile_picUrl;
                                        String JsonData= object.toString();

                                        Log.d("TAG",JsonData);
                                        try {
                                            jsonresponse= new JSONObject(JsonData);
                                            name.setText(jsonresponse.get("name").toString());
                                            email.setText(jsonresponse.get("email").toString());
                                            profile_pic_data= new JSONObject(jsonresponse.get("picture").toString());
                                            profile_picUrl = new JSONObject(profile_pic_data.getString("data"));
                                            Glide.with(FBLoginActivity.this).load(profile_picUrl.getString("url")).into(profile_pic);
                                            //  Picasso.with(FBLoginActivity.this).load(profile_picUrl.getString("url")).into(profile_pic);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                }
                        );


                        Bundle permission_param = new Bundle();
                        permission_param.putString("fields","id,name,email,picture.width(120).height(120)");
                        data_Request.setParameters(permission_param);
                        data_Request.executeAsync();
                        Toast.makeText(FBLoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                    }


                    @Override
                    public void onCancel() {
                        Toast.makeText(FBLoginActivity.this, "Login Cancelled", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(FBLoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(mCallbackManager.onActivityResult(requestCode,resultCode,data)){
            return;
        }
    }
}
