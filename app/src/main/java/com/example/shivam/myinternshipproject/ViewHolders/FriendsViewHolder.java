package com.example.shivam.myinternshipproject.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.shivam.myinternshipproject.R;
import com.example.shivam.myinternshipproject.utils.TwitterFriends;

import java.util.List;

/**
 * Created by shivam on 17/11/17.
 */

public class FriendsViewHolder extends RecyclerView.ViewHolder {
    public TextView name_text;
    public CheckBox checkBox;
    public List<TwitterFriends> taskObj;
    public FriendsViewHolder(final View itemView, List<TwitterFriends> eventModels) {
        super(itemView);
        this.taskObj = eventModels;
        name_text = (TextView) itemView.findViewById(R.id.name_text_view_friends);
        checkBox = (CheckBox)itemView.findViewById(R.id.check_box_friends);
    }
}
