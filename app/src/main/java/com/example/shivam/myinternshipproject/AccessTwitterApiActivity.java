package com.example.shivam.myinternshipproject;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mopub.common.util.Json;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.internal.TwitterApi;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.TweetBuilder;
import com.twitter.sdk.android.core.services.FavoriteService;
import com.twitter.sdk.android.core.services.SearchService;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.Timeline;
import com.twitter.sdk.android.tweetui.TweetScribeClient;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;
import com.twitter.sdk.android.tweetui.TweetUi;
import com.twitter.sdk.android.tweetui.TweetView;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;

public class AccessTwitterApiActivity extends AppCompatActivity {
    Button btn;
    int maxCnt=0;
    String user_name;
    long user_id;
    String maxID = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_twitter_api);
        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
        final TwitterSession activeSession = TwitterCore.getInstance()
                .getSessionManager().getActiveSession();
        user_id = activeSession.getUserId();
        user_name = activeSession.getUserName().toString();
        Log.e("USerName : ",activeSession.getUserName().toString());
        Log.e("User Id : ",String.valueOf(activeSession.getUserId()));
        btn = (Button)findViewById(R.id.Next_BTN);

        final LinearLayout myAcessLayout
                = (LinearLayout) findViewById(R.id.my_access_layout);

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
                   int countX = x.favoriteCount;
                   String tweetID = x.idStr;
                   if(countX>maxCnt){
                       maxCnt = countX;
                       maxID = tweetID;
                   }

                }
                Log.e("MAX Tweet ID","id:  "+maxID+" with count--> "+String.valueOf(maxCnt));
                for (Tweet y : homeList){
                    if (y.idStr.equals(maxID)){
                        TweetView tweetView = new TweetView(AccessTwitterApiActivity.this,y,R.style.tw__TweetDarkWithActionsStyle);
                        myAcessLayout.addView(tweetView);
                        displayBig(y);
                    }
                }

            }

            @Override
            public void failure(TwitterException exception) {

            }
        });

btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent intent = new Intent(AccessTwitterApiActivity.this,ChooseCategoryActivity.class);
        intent.putExtra("user_id",user_id);
        intent.putExtra("user_name",user_name);
        startActivity(intent);


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
