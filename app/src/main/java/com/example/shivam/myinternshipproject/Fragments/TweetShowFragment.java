package com.example.shivam.myinternshipproject.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shivam.myinternshipproject.R;
import com.example.shivam.myinternshipproject.utils.DatabaseObject;
import com.example.shivam.myinternshipproject.utils.MyConfig;
import com.example.shivam.myinternshipproject.utils.StaticKeys;
import com.example.shivam.myinternshipproject.utils.TinyDB;
import com.example.shivam.myinternshipproject.utils.TweetCompareModel;
import com.example.shivam.myinternshipproject.utils.TwitterFriends;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
public class TweetShowFragment extends Fragment {
    private int mColumnCount = 1;
    DatabaseObject databaseObject;
    TinyDB tinyDB;
    View view;
    List<TweetCompareModel> comp_list,sub_list;
    MyConfig myConfig;
    List<Tweet> homeList,final_list_tweet,adapter_pass_list;
    private OnListFragmentInteractionListener mListener;
    public TweetShowFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_tweet_show_list, container, false);
        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
        final TwitterSession activeSession = TwitterCore.getInstance()
                .getSessionManager().getActiveSession();
        comp_list = new ArrayList<>();
        sub_list = new ArrayList<>();
        adapter_pass_list = new ArrayList<>();
        final_list_tweet = new ArrayList<>();
        StatusesService ss = twitterApiClient.getStatusesService();
        final Call<List<Tweet>> homeCall = ss.homeTimeline(199,null,null,null,null,null,null);
        databaseObject = tinyDB.getObject(StaticKeys.MY_DATABASE_OBJECT_KEY,DatabaseObject.class);
        myConfig = tinyDB.getObject(StaticKeys.MY_CONFIG_KEY,MyConfig.class);
        homeCall.enqueue(new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                 homeList = result.data;
                int size = homeList.size();
                Log.e("My Home List Size: ",String.valueOf(size));
                for (Tweet t : homeList){
                    for (TwitterFriends tf: databaseObject.getActive_list()){
                        if (t.user.name.equals(tf.getName())){
                            final_list_tweet.add(t);
                            comp_list.add(new TweetCompareModel(t.getId(),t.favoriteCount));
                            Log.e("My List: ", t.favoriteCount + " ");

                        }
                    }
                }
                Collections.sort(comp_list, new Comparator<TweetCompareModel>() {
                    @Override
                    public int compare(TweetCompareModel s,TweetCompareModel t1) {
                        return t1.getFav_count()-s.getFav_count();
                    }
                });
                sub_list = comp_list.subList(0,myConfig.getNo_of_tweets_selected()-1);
                for (TweetCompareModel tcm: sub_list){
                    for (Tweet tweet: final_list_tweet){
                        if (tweet.id==tcm.id){
                            adapter_pass_list.add(tweet);
                        }
                    }
                }

                Log.e("COMP TAG: ",comp_list.get(0).fav_count+" ");

                setCustomAdapter();
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });


        return view;
    }

public void setCustomAdapter(){

    if (view instanceof RecyclerView) {
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view;
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        recyclerView.setAdapter(new TweetShowRecyclerViewAdapter(adapter_pass_list, mListener,context,comp_list));
    }

}
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
        tinyDB = new TinyDB(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Tweet item);
    }
}
