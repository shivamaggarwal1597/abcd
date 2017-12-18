package com.example.shivam.myinternshipproject.Fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.shivam.myinternshipproject.Fragments.TweetShowFragment.OnListFragmentInteractionListener;
import com.example.shivam.myinternshipproject.R;
import com.example.shivam.myinternshipproject.utils.MyConfig;
import com.example.shivam.myinternshipproject.utils.StaticKeys;
import com.example.shivam.myinternshipproject.utils.TinyDB;
import com.example.shivam.myinternshipproject.utils.TweetCompareModel;
import com.example.shivam.myinternshipproject.utils.TwitterFriends;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;
public class TweetShowRecyclerViewAdapter extends RecyclerView.Adapter<TweetShowRecyclerViewAdapter.ViewHolder> {

    private final List<Tweet> mValues;
    MyConfig myConfig;
    TinyDB tinyDB;
    Context context;
    List<TwitterFriends> active_list;
    int no_of_tweets_selected;

    List<TweetCompareModel> compareModels,sub_list;
    private final OnListFragmentInteractionListener mListener;

    public TweetShowRecyclerViewAdapter(List<Tweet> items, OnListFragmentInteractionListener listener,
                                        Context context,
                                        List<TweetCompareModel> compareModelList) {
        mValues = items;
        compareModels = compareModelList;
        this.context=context;
        mListener = listener;
        tinyDB = new TinyDB(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment__tweet_show, parent, false);
        myConfig=tinyDB.getObject(StaticKeys.MY_CONFIG_KEY, MyConfig.class);
        no_of_tweets_selected=myConfig.getNo_of_tweets_selected();
        sub_list=compareModels.subList(0,no_of_tweets_selected-1);
        for (TweetCompareModel tcm: sub_list){

        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tweet_content.setText(mValues.get(position).text);
        holder.tweet_time_text.setText(mValues.get(position).createdAt);
        holder.tweeter_name.setText(mValues.get(position).user.name);
        Picasso.with(context).load(mValues.get(position).user.profileImageUrl).into(holder.profile_image);
        if (mValues.get(position).entities.media.size()!=0){
            Picasso.with(context).load(mValues.get(position).entities.media.get(0).mediaUrl).into(holder.tweet_content_image);
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
   //     public final TextView mIdView;
     //   public final TextView mContentView;
        public Tweet mItem;
        public ImageView profile_image;
        public TextView tweeter_name;
        public TextView tweet_content;
        public ImageView tweet_content_image;
        public TextView tweet_time_text;



        public ViewHolder(View view) {
            super(view);
            mView = view;
            //mIdView = (TextView) view.findViewById(R.id.id);
       ///     mContentView = (TextView) view.findViewById(R.id.content);
            profile_image = (ImageView)view.findViewById(R.id.tweet_profile_image);
            tweeter_name = (TextView)view.findViewById(R.id.user_name_text_view);
            tweet_content = (TextView)view.findViewById(R.id.tweet_content_text_view);
            tweet_content_image = (ImageView)view.findViewById(R.id.tweet_image_view);
            tweet_time_text = (TextView)view.findViewById(R.id.tweet_time_text_view);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + tweet_content.getText() + "'";
        }
    }
}
