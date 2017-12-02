package com.example.shivam.myinternshipproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shivam.myinternshipproject.Fragments.ShowCatogariesFragment;
import com.example.shivam.myinternshipproject.Fragments.ShowingHandlesListFragment;
import com.example.shivam.myinternshipproject.utils.CategoryObject;
import com.example.shivam.myinternshipproject.utils.MyConfig;
import com.example.shivam.myinternshipproject.utils.TinyDB;
import com.example.shivam.myinternshipproject.utils.TwitterFriends;

public class MajorActivity extends AppCompatActivity implements ShowCatogariesFragment.OnListFragmentInteractionListener,ShowingHandlesListFragment.HandlesFragmentInteractionListener{

    private TextView mTextMessage;
    TinyDB tinyDB;
    MyConfig myConfig ;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    ShowCatogariesFragment showCatogariesFragment = new ShowCatogariesFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame,showCatogariesFragment,"Catogaries");
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_dashboard:
                    //This is my Handles SHowing page
                    ShowingHandlesListFragment showingHandlesListFragment=new ShowingHandlesListFragment();
                    FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.frame,showingHandlesListFragment,"Handles");
                    fragmentTransaction1.commit();
                    return true;
                case R.id.navigation_notifications:
                    //This is my Tweets Showing Page
              //      mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_major);
        tinyDB = new TinyDB(MajorActivity.this);
        myConfig = tinyDB.getObject("my_config_object",MyConfig.class);
        Toast.makeText(MajorActivity.this,"Welcome "+myConfig.getName_of_user(),Toast.LENGTH_SHORT).show();
        ShowCatogariesFragment showCatogariesFragment = new ShowCatogariesFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,showCatogariesFragment,"Catogaries");
        fragmentTransaction.commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onListFragmentInteraction(CategoryObject item) {
        Toast.makeText(MajorActivity.this,item.getCategory_name(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onListFragmentInteraction(TwitterFriends item) {
        Toast.makeText(MajorActivity.this,item.getName(),Toast.LENGTH_SHORT).show();
    }
}