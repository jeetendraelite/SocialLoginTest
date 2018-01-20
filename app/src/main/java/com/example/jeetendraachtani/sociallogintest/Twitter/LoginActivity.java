package com.example.jeetendraachtani.sociallogintest.Twitter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jeetendraachtani.sociallogintest.R;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import retrofit2.Call;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TwitterLoginButton loginButton;
    Button logout;
    TextView usernameTextView;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);

        setContentView(R.layout.activity_login);

        usernameTextView=findViewById(R.id.tv_twitter_username);
        logout=findViewById(R.id.btn_twitter_logout);
        image=findViewById(R.id.iv_twitter_profilepic);
        logout.setOnClickListener(this);
        logout.setVisibility(View.GONE);
        usernameTextView.setVisibility(View.GONE);
        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                loginButton.setVisibility(View.GONE);
                usernameTextView.setVisibility(View.VISIBLE);
                logout.setVisibility(View.VISIBLE);

                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;

                login(session);
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }



    String username, profileImage;
    public  void login(TwitterSession session) {
         // = session.getUserName();
           /* Intent intent = new Intent(LoginActivity.this, Homepage.class);
            intent.putExtra("username",username);
            startActivity(intent);*/

        TwitterCore.getInstance().getApiClient().getAccountService().verifyCredentials(true, true, false).enqueue(new Callback<User>() {
            @Override
            public void success(Result<User> result) {
                username = result.data.screenName;
                profileImage= result.data.profileImageUrl;
                Log.d("TAG", "Name: " + username);
                Log.d("TAG", "Image: " + profileImage);
                usernameTextView.setText(" Welcome user  " + username);
               // image.setImageResource(Integer.parseInt(profileImage));
                Glide.with(LoginActivity.this).load(profileImage).into(image);
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });






//        TwitterCore.getInstance().getApiClient().getAccountService().verifyCredentials(true,false, {
//            @Override
//            public void success(Result<User> result) {
//               User user= result.data;
//                String profileImage= user.profileImageUrl;
//            }
//
//            @Override
//            public void failure(TwitterException exception) {
//
//            }
//        });
    }









    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        loginButton.setVisibility(View.VISIBLE);
        usernameTextView.setVisibility(View.GONE);
        logout.setVisibility(View.GONE);
        image.setVisibility(View.GONE);
    }
}
