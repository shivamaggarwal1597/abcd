package com.example.shivam.myinternshipproject.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shivam on 2/12/17.
 */


public class DatabaseObject {

    public Map<String,List<TwitterFriends>> mp = new HashMap<>();

    public DatabaseObject() {

    }

    public Map<String, List<TwitterFriends>> getMp() {
        return mp;
    }

    public void setMp(String a,List<TwitterFriends> l) {
        Map<String,List<TwitterFriends>> mp_demo = new HashMap<>();
        mp_demo.put(a,l);
        this.mp = mp_demo;
    }
}
