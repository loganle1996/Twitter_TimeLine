package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private String name, screenName;
    private long uId;
    private String profileImUrl;

    public static User fromJson(JSONObject jsonObject) throws JSONException {
        User user = new User();
        user.setName(jsonObject.getString("name"));
        user.setuId(jsonObject.getLong("id"));
        user.setScreenName(jsonObject.getString("screen_name"));
        user.setProfileImUrl(jsonObject.getString("profile_image_url"));
        return user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public long getuId() {
        return uId;
    }

    public void setuId(long uId) {
        this.uId = uId;
    }

    public String getProfileImUrl() {
        return profileImUrl;
    }

    public void setProfileImUrl(String profileImUrl) {
        this.profileImUrl = profileImUrl;
    }
}
