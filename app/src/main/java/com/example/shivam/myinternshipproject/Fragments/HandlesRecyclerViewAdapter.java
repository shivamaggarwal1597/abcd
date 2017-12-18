package com.example.shivam.myinternshipproject.Fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shivam.myinternshipproject.Fragments.ShowingHandlesListFragment.HandlesFragmentInteractionListener;

import com.example.shivam.myinternshipproject.R;
import com.example.shivam.myinternshipproject.utils.DatabaseObject;
import com.example.shivam.myinternshipproject.utils.StaticKeys;
import com.example.shivam.myinternshipproject.utils.TinyDB;
import com.example.shivam.myinternshipproject.utils.TwitterFriends;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class HandlesRecyclerViewAdapter extends RecyclerView.Adapter<HandlesRecyclerViewAdapter.ViewHolder> {
    DatabaseObject databaseObject;
    private final List<TwitterFriends> mValues;
    Context context;
    private final HandlesFragmentInteractionListener mListener;
    TinyDB tinyDB;
    List<TwitterFriends> active_list;
    TwitterFriends tw;
    List<TwitterFriends> all_handles_list;
    public HandlesRecyclerViewAdapter(List<TwitterFriends> items, HandlesFragmentInteractionListener listener, Context context) {
        mValues = items;
        mListener = listener;
        this.context = context;
        all_handles_list = new ArrayList<>();
        active_list=new ArrayList<>();
        tinyDB = new TinyDB(context);
        }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_show_handles, parent, false);
        //databaseObject= tinyDB.getObject(StaticKeys.MY_DATABASE_OBJECT_KEY,DatabaseObject.class);
        databaseObject= tinyDB.getObject(StaticKeys.MY_DATABASE_OBJECT_KEY,DatabaseObject.class);
        active_list = databaseObject.getActive_list();

        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).getName());
        Picasso.with(context).load(mValues.get(position).getProfilePictureUrl()).into(holder.imageView);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    tw =  mValues.get(position);
                 if (!active_list.contains(tw)){
                     active_list.add(tw);
                     databaseObject.setActive_list(active_list);
                     Log.e("Showing List : ",String.valueOf(active_list.size()));
                     tinyDB.putObject(StaticKeys.MY_DATABASE_OBJECT_KEY,databaseObject);
                 }
                 else {
                     active_list.remove(tw);
                     databaseObject.setActive_list(active_list);
                     tinyDB.putObject(StaticKeys.MY_DATABASE_OBJECT_KEY,databaseObject);
                     Toast.makeText(context,"Removed from list",Toast.LENGTH_SHORT).show();
                 }
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
        public final TextView mContentView;
        public TwitterFriends mItem;
        public ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.name_of_handle);
            imageView = (ImageView)view.findViewById(R.id.profile_image);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
