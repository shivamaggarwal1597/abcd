package com.example.shivam.myinternshipproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.example.shivam.myinternshipproject.utils.MyConfig;
import com.example.shivam.myinternshipproject.utils.StaticKeys;
import com.example.shivam.myinternshipproject.utils.TinyDB;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;


public class MainActivity extends AppCompatActivity {
    TwitterLoginButton loginButton;
    TinyDB tinyDB;
    MyConfig myConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        setContentView(R.layout.activity_main);
        tinyDB = new TinyDB(MainActivity.this);
        if (tinyDB.getObject(StaticKeys.MY_CONFIG_KEY,MyConfig.class)==null){
            Toast.makeText(MainActivity.this,"Working",Toast.LENGTH_LONG).show();
            Log.e("hgvjhbkjn","abc");
            loginButton = (TwitterLoginButton) findViewById(R.id.login_button);
            loginButton.setCallback(new Callback<TwitterSession>() {
                @Override
                public void success(Result<TwitterSession> result) {
                    TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                    TwitterAuthToken authToken = session.getAuthToken();
                    String token = authToken.token;
                    String secret = authToken.secret;
                    myConfig = new MyConfig(true);
                    myConfig.setId_of_user(session.getUserId());
                    myConfig.setName_of_user(session.getUserName());
                    myConfig.setLoggedIn(true);
                    myConfig.setUser_login_count();
                    tinyDB.putObject("my_config_object",myConfig);
                    login();

                }

                @Override
                public void failure(TwitterException exception) {
                    Toast.makeText(MainActivity.this,"Failure",Toast.LENGTH_LONG).show();
                }
            });

        }
        else {

            myConfig = tinyDB.getObject(StaticKeys.MY_CONFIG_KEY,MyConfig.class);
            myConfig.setUser_login_count();
            tinyDB.putObject(StaticKeys.MY_CONFIG_KEY,myConfig);
            Log.e("hgvjhbkjn",String.valueOf(myConfig.getUser_login_count()));
            login();
        }

    }

    public void login(){
        Intent intent = new Intent(MainActivity.this,MajorActivity.class);
        startActivity(intent);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

}
