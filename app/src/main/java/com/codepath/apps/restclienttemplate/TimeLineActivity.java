package com.codepath.apps.restclienttemplate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.codepath.apps.restclienttemplate.adapters.TweetAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.github.scribejava.apis.TwitterApi;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TimeLineActivity extends AppCompatActivity {
    private static final String TwitterClient = "TwitterClient";
    private TwitterClient client;
    private RecyclerView recyclerView;
    private TweetAdapter tweetAdapter;
    private List<Tweet> tweetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        client = TwitterApp.getRestClient(this);
        populateHomeTimeLine();
        //find the recycler view
        recyclerView = findViewById(R.id.recyler_view_tweets);
        // initialize a list of tweets and adapater from the data source
        tweetList = new ArrayList<>();
        tweetAdapter = new TweetAdapter(this, tweetList);
        //RecyclerView set up: layout manager and setting up adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(tweetAdapter);
    }

    private void populateHomeTimeLine() {
        client.getHomeTimeLine(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                Log.d(TwitterClient, jsonArray.toString());
                //Iterate through a list of Json objects
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        //convert each json object into a tweet object
                        JSONObject jsonTweetObject = jsonArray.getJSONObject(i);
                        Tweet tweet = Tweet.fromJson(jsonTweetObject);
                        //Add the tweet into the list of tweets
                        tweetList.add(tweet);
                        //notify changes
//                        tweetAdapter.notifyDataSetChanged();
                        tweetAdapter.notifyItemInserted(tweetList.size() - 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TwitterClient, responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e(TwitterClient, errorResponse.toString());
            }
        });
    }
}
