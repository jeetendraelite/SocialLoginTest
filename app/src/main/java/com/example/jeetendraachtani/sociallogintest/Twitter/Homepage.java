package com.example.jeetendraachtani.sociallogintest.Twitter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.jeetendraachtani.sociallogintest.R;

/**
 * Created by jeetendra.achtani on 17-01-2018.
 */

public class Homepage extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        String username = getIntent().getStringExtra("username");

        TextView uname = findViewById(R.id.tv_username12);
        uname.setText(username);

    }
}
