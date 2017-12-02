package com.example.shivam.myinternshipproject;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.shivam.myinternshipproject.utils.TinyDB;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetui.TweetView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class ShowTweetOfDayActivity extends AppCompatActivity {
    int maxCnt=0;
    String user_name;
    long user_id;
    ArrayList<String> my_list_of_handles;
    TinyDB tinyDB;
   // SharedPreferences sharedPreferences;
    String maxID = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tweet_of_day);
        tinyDB = new TinyDB(getApplicationContext());
        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
        //sharedPreferences = getSharedPreferences("MY_PREFS",MODE_PRIVATE);
    //   set = sharedPreferences.getStringSet("key_set",null);
        final TwitterSession activeSession = TwitterCore.getInstance()
                .getSessionManager().getActiveSession();
        user_id = activeSession.getUserId();
        my_list_of_handles = tinyDB.getListString("my_handle_list");
        for (String d : my_list_of_handles){
            Log.e("MY Handlese: ",d);
        }
        user_name = activeSession.getUserName().toString();
        final LinearLayout myAcessLayout
                = (LinearLayout) findViewById(R.id.my_access_layout1);
        StatusesService ss = twitterApiClient.getStatusesService();
        final Call<List<Tweet>> homeCall = ss.homeTimeline(199,null,null,null,null,null,null);

        homeCall.enqueue(new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                List<Tweet> homeList = result.data;
                int size = homeList.size();
                Log.e("My Home List Size: ",String.valueOf(size));
                String favCount = homeList.get(0).favoriteCount.toString();
                for(Tweet x: homeList){
                 for (String j : my_list_of_handles){
                     if (x.user.name.equals(j)){
                         int countX = x.favoriteCount;
                         String tweetID = x.idStr;
                         if(countX>maxCnt){
                             maxCnt = countX;
                             maxID = tweetID;
                         }
                     }
                 }

                }
                Log.e("MAX Tweet ID","id:  "+maxID+" with count--> "+String.valueOf(maxCnt));
                for (Tweet y : homeList){
                    if (y.idStr.equals(maxID)){
                        TweetView tweetView = new TweetView(ShowTweetOfDayActivity.this,y,R.style.tw__TweetDarkWithActionsStyle);
                        myAcessLayout.addView(tweetView);
                        displayBig(y);
                    }
                }

            }

            @Override
            public void failure(TwitterException exception) {

            }
        });

    }
    private void displayBig(Tweet tweet ){
        Tweet t = tweet;
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("TWEET OF THE DAY");
        builder.setContentText(t.text.toString());
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(t.text.toString()));
        builder.addAction(R.mipmap.ic_launcher,"Add",null);
        builder.addAction(R.mipmap.ic_launcher,"Close",null);
        NotificationManager manager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(2,builder.build());
    }
}
