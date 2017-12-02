package com.example.shivam.myinternshipproject.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shivam on 2/12/17.
 */

public class CategoryDataAcessObject {

    public List<CategoryObject> categoryObjectList = new ArrayList<>();

    public CategoryDataAcessObject() {

    }

    public List<CategoryObject> getCategoryObjectList() {
        return categoryObjectList;
    }

    public void setCategoryObjectList(List<CategoryObject> categoryObjectList) {
        this.categoryObjectList = categoryObjectList;
    }
    //this would be initialized in the starting.
    //will be changed later on
    //For adding category functionality

}
