package com.example.shivam.myinternshipproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shivam.myinternshipproject.Adapters.MyUserAdapter;
import com.example.shivam.myinternshipproject.utils.FriendsResponseModel;
import com.example.shivam.myinternshipproject.utils.TwitterFriends;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity{
// In this activity, the user will choose among the catogories
// User can select from some categories and also he can create his own categories in which he can add twitter handles
    long cursor=-1;
    RecyclerView recyclerView;
    TwitterAuthToken twitterAuthToken;
    long  logged_in_user_id;
    MyUserAdapter myUserAdapter;
    LinearLayoutManager linearLayoutManager;
    Button submit_handles_btn;
    List<TwitterFriends> tf;
    List<TwitterFriends> twitterFriends;

    ArrayList<String> friendsList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
        twitterFriends = new ArrayList<TwitterFriends>();
        submit_handles_btn = (Button)findViewById(R.id.submit_btn_selecting_handles);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view_friends);
        Log.e("User ID 2 : ",String.valueOf(logged_in_user_id));
        final TwitterSession activeSession = TwitterCore.getInstance()
                .getSessionManager().getActiveSession();
        logged_in_user_id = activeSession.getUserId();
        MyTwitterApiClient my = new MyTwitterApiClient(activeSession);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        submit_handles_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this,ShowTweetOfDayActivity.class);

                startActivity(intent);
            }
        });
    my.getCustomService().list(logged_in_user_id, -1).enqueue(new retrofit2.Callback<FriendsResponseModel>() {
        @Override
        public void onResponse(Call<FriendsResponseModel> call, Response<FriendsResponseModel> response) {
            tf = fetchResults(response);
            twitterFriends.addAll(tf);
            Log.e("onResponse", response.toString() + " size : " + twitterFriends.size() + " next_cursor : " + response.body().getNextCursorStr());
            cursor = Long.parseLong(response.body().getNextCursorStr());
            myUserAdapter = new MyUserAdapter(twitterFriends,CategoryActivity.this);
            recyclerView.setAdapter(myUserAdapter);
            Toast.makeText(CategoryActivity.this,"CHOOOSEEE",Toast.LENGTH_SHORT).show();


        }

        @Override
        public void onFailure(Call<FriendsResponseModel> call, Throwable t) {
            Toast.makeText(CategoryActivity.this, "not Successful called", Toast.LENGTH_SHORT).show();
        }
    });
    }



    //}
    private List<TwitterFriends> fetchResults(Response<FriendsResponseModel> response) {
        FriendsResponseModel responseModel = response.body();
        return responseModel.getResults();
    }


}
