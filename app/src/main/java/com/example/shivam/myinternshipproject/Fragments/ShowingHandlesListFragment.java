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

import com.example.shivam.myinternshipproject.MyTwitterApiClient;
import com.example.shivam.myinternshipproject.R;

import com.example.shivam.myinternshipproject.utils.DatabaseObject;
import com.example.shivam.myinternshipproject.utils.FriendsResponseModel;
import com.example.shivam.myinternshipproject.utils.MyConfig;
import com.example.shivam.myinternshipproject.utils.StaticKeys;
import com.example.shivam.myinternshipproject.utils.TinyDB;
import com.example.shivam.myinternshipproject.utils.TwitterFriends;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link HandlesFragmentInteractionListener}
 * interface.
 */
public class ShowingHandlesListFragment extends Fragment {
    DatabaseObject databaseObject;
    TinyDB tinyDB;
    MyConfig myConfig;
    View view;
    List<TwitterFriends> tf;
    Map<String,List<TwitterFriends>> map;
    List<TwitterFriends> twitterFriends;
    long cursor=-1;
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private HandlesFragmentInteractionListener mListener;
    public ShowingHandlesListFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_item_handles, container, false);
        twitterFriends = new ArrayList<TwitterFriends>();
        myConfig = tinyDB.getObject(StaticKeys.MY_CONFIG_KEY,MyConfig.class);
        final TwitterSession activeSession = TwitterCore.getInstance()
                .getSessionManager().getActiveSession();
        MyTwitterApiClient my = new MyTwitterApiClient(activeSession);
        if (tinyDB.getObject(StaticKeys.MY_CONFIG_KEY, MyConfig.class).getUser_login_count()==1){
            //As it should fetch the handles only once as they won't update regularly
            //Also, ISSUE --> ABLE TO FETCH ONLY 20 HANDLES
            databaseObject = new DatabaseObject();
            my.getCustomService().list(myConfig.getId_of_user(), -1).enqueue(new retrofit2.Callback<FriendsResponseModel>() {
                @Override
                public void onResponse(Call<FriendsResponseModel> call, Response<FriendsResponseModel> response) {
                    tf = fetchResults(response);
                    twitterFriends.addAll(tf);
                    Log.e("onResponse", response.toString() + " size : " + twitterFriends.size() + " next_cursor : " + response.body().getNextCursorStr());
                    cursor = Long.parseLong(response.body().getNextCursorStr());
                    Log.e("SHowing Handles","working");
                    databaseObject.setMp("all",twitterFriends);

                    tinyDB.putObject(StaticKeys.MY_DATABASE_OBJECT_KEY,databaseObject);

                    setAdapterCustom();
                }

                @Override
                public void onFailure(Call<FriendsResponseModel> call, Throwable t) {
                    Log.e("Showin handles","Fai;ure");
                    // Toast.makeText(CategoryActivity.this, "not Successful called", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
          databaseObject =  tinyDB.getObject(StaticKeys.MY_DATABASE_OBJECT_KEY,DatabaseObject.class);
            map= databaseObject.getMp();
            twitterFriends = map.get("all");
            setAdapterCustom();
        }
        return view;
    }

    public void setAdapterCustom(){
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
//           twitterFriends = map.get("all");
            recyclerView.setAdapter(new HandlesRecyclerViewAdapter(twitterFriends, mListener,getActivity()));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HandlesFragmentInteractionListener) {
            mListener = (HandlesFragmentInteractionListener) context;
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
    private List<TwitterFriends> fetchResults(Response<FriendsResponseModel> response) {
        FriendsResponseModel responseModel = response.body();
        return responseModel.getResults();
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface HandlesFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(TwitterFriends item);
    }
}
