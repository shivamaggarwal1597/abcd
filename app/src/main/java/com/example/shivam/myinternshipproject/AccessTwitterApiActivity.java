package com.example.shivam.myinternshipproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_twitter_api);
        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
        //SearchService sv = twitterApiClient.getSearchService();
        final LinearLayout myAcessLayout
                = (LinearLayout) findViewById(R.id.my_access_layout);
        /*StatusesService statusesService = twitterApiClient.getStatusesService();
        Call<Tweet> call = statusesService.show(524971209851543553L, null, null, null);
        call.enqueue(new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {
                //Do something with result

                Log.e("My result",result.data.text);
            }

            public void failure(TwitterException exception) {
                //Do something on failure
            }
        });
        */
        StatusesService ss = twitterApiClient.getStatusesService();
        //FavoriteService fv = twitterApiClient.getFavoriteService();
        //Service
        final TwitterSession activeSession = TwitterCore.getInstance()
                .getSessionManager().getActiveSession();
        Toast.makeText(AccessTwitterApiActivity.this, " id:- "+activeSession.getUserId(),Toast.LENGTH_SHORT).show();
        final Call<List<Tweet>> homeCall = ss.homeTimeline(20,null,null,null,null,null,null);
        homeCall.enqueue(new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                List<Tweet> homeList = result.data;
                TweetView tweetView = new TweetView(AccessTwitterApiActivity.this,homeList.get(0),R.style.tw__TweetDarkWithActionsStyle);
                myAcessLayout.addView(tweetView);
                homeList.get(0).
                String favCount = homeList.get(0).favoriteCount.toString();
                Toast.makeText(AccessTwitterApiActivity.this,favCount,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });
      /* Call<List<Tweet>> tweetCall = fv.list(activeSession.getUserId(),null,null,null,null,false);
        tweetCall.enqueue(new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                List<Tweet> t = result.data;
                Toast.makeText(AccessTwitterApiActivity.this,"size :-"+t.size(),Toast.LENGTH_SHORT).show();
                Log.e("my super : ",t.get(0).text);
                  TweetView tweetView = new TweetView(AccessTwitterApiActivity.this,t.get(0), R.style.tw__TweetDarkWithActionsStyle);
//                Toast.makeText(AccessTwitterApiActivity.this,t.size(),Toast.LENGTH_SHORT).show();
                myAcessLayout.addView(tweetView);
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });*/
       /* Call<Search> twits=sv.tweets("#twitter",null,null,null,"recent",null,null,null,null,true);
        twits.enqueue(new Callback<Search>() {
            @Override
            public void success(Result<Search> result) {

                List<Tweet> l=result.data.tweets;
                Log.e("MY Search Result",l.get(0).text);
                //TweetView tweetView = new TweetView(AccessTwitterApiActivity.this,l.get(1), R.style.tw__TweetDarkWithActionsStyle);
             //   myAcessLayout.addView(tweetView);

            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(AccessTwitterApiActivity.this,"error",Toast.LENGTH_LONG).show();
            }
        });
*/       /* final TwitterSession activeSession = TwitterCore.getInstance()
                .getSessionManager().getActiveSession();

        // example of custom OkHttpClient with logging HttpLoggingInterceptor
        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        final OkHttpClient customClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor).build();

        // pass custom OkHttpClient into TwitterApiClient and add to TwitterCore
        final TwitterApiClient customApiClient;
        if (activeSession != null) {
            customApiClient = new TwitterApiClient(activeSession, customClient);
            TwitterCore.getInstance().addApiClient(activeSession, customApiClient);
            Log.e("My fuckin Tag", "IN FIRST PART");
          //  FavoriteService fv = customApiClient.getFavoriteService();
           // fv.list(activeSession.getUserId(),null,5,null,null,false);
        } else {
            customApiClient = new TwitterApiClient(customClient);
            TwitterCore.getInstance().addGuestApiClient(customApiClient);
            customApiClient.getListService();
        }
*/
    }
}
