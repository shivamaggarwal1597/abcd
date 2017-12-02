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

import com.example.shivam.myinternshipproject.Fragments.ShowingHandlesListFragment.HandlesFragmentInteractionListener;

import com.example.shivam.myinternshipproject.R;
import com.example.shivam.myinternshipproject.utils.TwitterFriends;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link TwitterFriends} and makes a call to the
 * specified {@link HandlesFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class HandlesRecyclerViewAdapter extends RecyclerView.Adapter<HandlesRecyclerViewAdapter.ViewHolder> {

    private final List<TwitterFriends> mValues;
    Context context;
    private final HandlesFragmentInteractionListener mListener;

    public HandlesRecyclerViewAdapter(List<TwitterFriends> items, HandlesFragmentInteractionListener listener, Context context) {
        mValues = items;
        mListener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_show_handles, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
       Log.e("URL",mValues.get(position).getProfilePictureUrl().toString());
        holder.mContentView.setText(mValues.get(position).getName());
        Picasso.with(context).load(mValues.get(position).getProfilePictureUrl()).into(holder.imageView);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
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
       // public final TextView mIdView;
        public final TextView mContentView;
        public TwitterFriends mItem;
        public ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            //mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.name_of_handle);
            imageView = (ImageView)view.findViewById(R.id.profile_image);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
