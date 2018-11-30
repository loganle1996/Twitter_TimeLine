package com.codepath.apps.restclienttemplate.models;

import android.icu.lang.UScript;

import org.json.JSONException;
import org.json.JSONObject;

public class Tweet {
    private String body, createdAt;
    private long uid;
    private User user;

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.setBody(jsonObject.getString("text")); // get the body of the tweet
        tweet.setUid(jsonObject.getLong("id"));
        tweet.setCreatedAt(jsonObject.getString("created_at"));
        tweet.setUser(User.fromJson(jsonObject.getJSONObject("user")));
        return tweet;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }
}
