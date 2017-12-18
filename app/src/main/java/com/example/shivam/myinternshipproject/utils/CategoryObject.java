package com.example.shivam.myinternshipproject.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shivam on 2/12/17.
 */

public class CategoryObject {

    public String category_name;
    public boolean isActive_category = false;
    public List<TwitterFriends> handles_list = new ArrayList<>();

    public List<TwitterFriends> getHandles_list() {
        return handles_list;
    }

    public void setHandles_list(List<TwitterFriends> handles_list) {
        this.handles_list = handles_list;
    }

    public CategoryObject(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public boolean isActive_category() {
        return isActive_category;
    }

    public void setActive_category(boolean active_category) {
        isActive_category = active_category;
    }
}
