package com.example.shivam.myinternshipproject;

import com.example.shivam.myinternshipproject.utils.FollowingHandlesListService;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by shivam on 14/11/17.
 */

public class MyTwitterApiClient extends TwitterApiClient {
    public MyTwitterApiClient(TwitterSession session) {
        super(session);

    }
    public FollowingHandlesListService getCustomService() {
        return getService(FollowingHandlesListService.class);
    }

}

