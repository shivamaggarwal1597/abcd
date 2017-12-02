package com.example.shivam.myinternshipproject.utils;

/**
 * Created by shivam on 2/12/17.
 */

public class CategoryObject {

    public String category_name;
    public boolean isActive_category = false;

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
