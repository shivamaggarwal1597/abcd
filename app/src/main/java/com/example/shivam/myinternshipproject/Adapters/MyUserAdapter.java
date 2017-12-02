package com.example.shivam.myinternshipproject.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shivam.myinternshipproject.R;
import com.example.shivam.myinternshipproject.utils.TinyDB;
import com.example.shivam.myinternshipproject.utils.TwitterFriends;
import com.example.shivam.myinternshipproject.ViewHolders.FriendsViewHolder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by shivam on 16/11/17.
 */

public class MyUserAdapter extends RecyclerView.Adapter<FriendsViewHolder> {
    SharedPreferences preferences;
    public List<TwitterFriends> friendModels;
    public Context context;
    public ArrayList<String> myList;
    TinyDB tinyDB;
    SharedPreferences.Editor editor;
    public MyUserAdapter(List<TwitterFriends> friendModels, Context context) {
        myList = new ArrayList<String>();
        this.friendModels = friendModels;
        this.context = context;
        tinyDB = new TinyDB(context);
        preferences = context.getSharedPreferences("MY_PREFS",Context.MODE_PRIVATE);
        editor = preferences.edit();

    }

    @Override
    public FriendsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FriendsViewHolder friendsViewHolder = null;
        LayoutInflater li= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutView = li.inflate(R.layout.custom_layout_friend,parent,false);
        friendsViewHolder = new FriendsViewHolder(layoutView, friendModels);
        return friendsViewHolder;
    }

    @Override
    public void onBindViewHolder(final FriendsViewHolder holder, final int position) {
holder.name_text.setText(friendModels.get(position).getName());
holder.name_text.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
    myList.add(friendModels.get(position).getName());
        for (String tf : myList){
            Log.e("My Tag For Check : ", tf);
        }

        Set<String> set = new HashSet<String>();
        set.add(friendModels.get(position).getName());
        editor.putStringSet("key_set",set);
        editor.commit();
        tinyDB.putListString("my_handle_list",myList);
    }
});


    }

    @Override
    public int getItemCount() {
        return friendModels.size();
    }
}
