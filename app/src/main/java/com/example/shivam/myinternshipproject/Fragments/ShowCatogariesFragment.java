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
import com.example.shivam.myinternshipproject.utils.CategoryDataAcessObject;
import com.example.shivam.myinternshipproject.utils.CategoryObject;
import com.example.shivam.myinternshipproject.utils.MyConfig;
import com.example.shivam.myinternshipproject.utils.StaticKeys;
import com.example.shivam.myinternshipproject.utils.TinyDB;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ShowCatogariesFragment extends Fragment {

    // TODO: Customize parameters
    private int mColumnCount = 1;
    CategoryDataAcessObject categoryDataAcessObject;
    TinyDB tinyDB;
    List<CategoryObject> categoryObjectList;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ShowCatogariesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);
        categoryObjectList = new ArrayList<>();
        //When user logins the first time, no category is selected
        if (tinyDB.getObject(StaticKeys.MY_CONFIG_KEY, MyConfig.class).getUser_login_count()==1){
            categoryObjectList.add(new CategoryObject("news"));
            categoryObjectList.add(new CategoryObject("education"));
            categoryObjectList.add(new CategoryObject("science"));
            categoryObjectList.add(new CategoryObject("sports"));
            categoryObjectList.add(new CategoryObject("politics"));
            categoryDataAcessObject = new CategoryDataAcessObject();
            categoryDataAcessObject.setCategoryObjectList(categoryObjectList);
            tinyDB.putObject("category_data_access_object",categoryDataAcessObject);
        }
        else {
            categoryDataAcessObject =tinyDB.getObject("category_data_access_object",CategoryDataAcessObject.class);
        }

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                Log.e("My","First");
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                Log.e("My","Second");
            }


            recyclerView.setAdapter(new CategoryRecyclerViewAdapter(categoryDataAcessObject.getCategoryObjectList(), mListener,getActivity()));
        }
        return view;
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
        void onListFragmentInteraction(CategoryObject item);
    }
}
