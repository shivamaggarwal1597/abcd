package com.example.shivam.myinternshipproject;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.params.Geocode;
import com.twitter.sdk.android.core.services.params.Geocode.Distance;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;
import com.twitter.sdk.android.tweetui.TwitterListTimeline;
import com.twitter.sdk.android.tweetui.UserTimeline;
// Not Required
public class ShowTimelineActivity extends AppCompatActivity {
    Button button;
    Geocode geocode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_timeline);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        button = (Button)findViewById(R.id.twitter_api_btn);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final TwitterSession activeSession = TwitterCore.getInstance()
                .getSessionManager().getActiveSession();
        geocode = new Geocode(51.5033,-0.1276,30000,Distance.MILES);
        UserTimeline userTimeline = new UserTimeline.Builder().userId(activeSession.getUserId()).maxItemsPerRequest(20).includeReplies(true).includeRetweets(true).build();
        final SearchTimeline timeline = new SearchTimeline.Builder()
                .query("#twitter")
                .maxItemsPerRequest(50)
                .resultType(SearchTimeline.ResultType.POPULAR)
                .build();
        final TweetTimelineRecyclerViewAdapter adapter =
                new TweetTimelineRecyclerViewAdapter.Builder(this)
                        .setTimeline(userTimeline)
                        .setViewStyle(R.style.tw__TweetLightWithActionsStyle)
                        .build();

        recyclerView.setAdapter(adapter);
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(ShowTimelineActivity.this,AccessTwitterApiActivity.class);
        startActivity(intent);


    }
});
    }
}
