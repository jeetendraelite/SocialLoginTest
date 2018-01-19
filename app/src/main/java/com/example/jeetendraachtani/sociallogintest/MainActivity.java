package com.example.jeetendraachtani.sociallogintest;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;


// Code for FACEBOOK LOGIN Integration

public class MainActivity extends AppCompatActivity {


   @BindView(R.id.fb_login_btn)
    LoginButton loginButton;

   @BindView(R.id.textview)
    TextView textView;

    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        callbackManager= CallbackManager.Factory.create();
        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                  /*  textView.setText("Login Success for \n"+ loginResult.getAccessToken().getUserId()+"\n"
                    +"\n"+loginResult.getAccessToken().getToken());
*/
                  getUserDetails(loginResult);
            }

            @Override
            public void onCancel() {
                        textView.setText("Login Cancelled");
            }

            @Override
            public void onError(FacebookException error) {

            }
        });

       // LoginManager.getInstance().logOut(); // we can remove it. this is latest update

        // for clearing access token
       /* if (AccessToken.getCurrentAccessToken() != null) {
            LoginManager.getInstance().logOut();
        }*/

     //   GenerateKeyHash();
    }

    private void getUserDetails(LoginResult loginResult) {
        GraphRequest data_Request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Intent intent = new Intent(MainActivity.this,FbUserProfileActivity.class);
                        intent.putExtra("userProfile",object.toString());
                        startActivity(intent);
                    }
                }
        );
        Bundle permission_param = new Bundle();
        permission_param.putString("fields","id,name,email,picture.width(120).height(120)");
        data_Request.setParameters(permission_param);
        data_Request.executeAsync();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

   /* @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }*/
    // This is the code for generating SSL key hash. implement this first time and get key in log
    /*public void GenerateKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getApplicationContext().getPackageName(),
                    PackageManager.GET_SIGNATURES); //GypUQe9I2FJr2sVzdm1ExpuWc4U= android pc -2 key
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
