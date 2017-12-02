package com.example.shivam.myinternshipproject.Fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.shivam.myinternshipproject.Fragments.ShowCatogariesFragment.OnListFragmentInteractionListener;

import com.example.shivam.myinternshipproject.R;
import com.example.shivam.myinternshipproject.utils.CategoryDataAcessObject;
import com.example.shivam.myinternshipproject.utils.CategoryObject;
import com.example.shivam.myinternshipproject.utils.MyConfig;
import com.example.shivam.myinternshipproject.utils.StaticKeys;
import com.example.shivam.myinternshipproject.utils.TinyDB;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a  and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {
    TinyDB tinyDB;
    private final List<CategoryObject> mValues;
    private final OnListFragmentInteractionListener mListener;
    MyConfig myConfig;
    CategoryDataAcessObject categoryDataAcessObject;
    List<CategoryObject> categoryObjectList;

    public CategoryRecyclerViewAdapter(List<CategoryObject> items, OnListFragmentInteractionListener listener, Context context) {
        mValues = items;
        mListener = listener;
        tinyDB = new TinyDB(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).getCategory_name());
        myConfig = tinyDB.getObject(StaticKeys.MY_CONFIG_KEY,MyConfig.class);
        categoryDataAcessObject = tinyDB.getObject(StaticKeys.MY_CATEGORY_DATA_OBJECT_KEY,CategoryDataAcessObject.class);
        categoryObjectList = categoryDataAcessObject.getCategoryObjectList();
        if (tinyDB.getObject(StaticKeys.MY_CATEGORY_DATA_OBJECT_KEY, CategoryDataAcessObject.class)
                .getCategoryObjectList()
                .get(position).isActive_category()){
            holder.radioButton.setChecked(true);
        } else {
            holder.radioButton.setChecked(false);
        }
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.mItem.isActive_category()){
                    holder.mItem.setActive_category(false);
                    Log.e("My Tag Again: ",String.valueOf(holder.mItem.isActive_category()));
                   categoryObjectList.get(position).setActive_category(false);
                   categoryDataAcessObject.setCategoryObjectList(categoryObjectList);
                   tinyDB.putObject(StaticKeys.MY_CATEGORY_DATA_OBJECT_KEY,categoryDataAcessObject);
                }
                else {
                    holder.mItem.setActive_category(true);
                    categoryObjectList.get(position).setActive_category(true);
                    categoryDataAcessObject.setCategoryObjectList(categoryObjectList);
                    tinyDB.putObject(StaticKeys.MY_CATEGORY_DATA_OBJECT_KEY,categoryDataAcessObject);
                    Log.e("My Tag Again: ",String.valueOf(holder.mItem.isActive_category()));
                }
            }
        });
        holder.mContentView.setOnClickListener(new View.OnClickListener() {
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
        public ToggleButton radioButton;
        public final TextView mContentView;
        public CategoryObject mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            radioButton=(ToggleButton) view.findViewById(R.id.radio_category);
            mContentView = (TextView) view.findViewById(R.id.category_name_text_view);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
