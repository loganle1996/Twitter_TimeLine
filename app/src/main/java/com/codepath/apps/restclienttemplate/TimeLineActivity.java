package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.adapters.TweetAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TimeLineActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeContainer;
    private static final String TwitterClient = "TwitterClient";
    private TwitterClient client;
    private RecyclerView recyclerView;
    private TweetAdapter tweetAdapter;
    private List<Tweet> tweetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Only ever call `setContentView` once right at the top
        setContentView(R.layout.activity_time_line);
        //Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        client = TwitterApp.getRestClient(this);
        // initialize a list of tweets and adapater from the data source
        tweetList = new ArrayList<>();
        populateHomeTimeLine();
        //find the recycler view
        recyclerView = findViewById(R.id.recyler_view_tweets);
        tweetAdapter = new TweetAdapter(this, tweetList);
        //RecyclerView set up: layout manager and setting up adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(tweetAdapter);

        //populateHomeTimeLine();

        //Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Your code to refresh the list here.
                //Make sure you call swipeContainer.setRefreshing(false)
                //once the network request has completed successfully
                fetchTimelineAsync(0);
            }
        });

        //Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    public void fetchTimelineAsync(int page) {
        populateHomeTimeLine();
    }

    private void populateHomeTimeLine() {
        client.getHomeTimeLine(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                if (tweetList.size() > 0) { // if there are old items, clear them.
                    tweetAdapter.clear();
                    tweetList.clear();
                }
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
                        if (swipeContainer.isRefreshing()) {
                            swipeContainer.setRefreshing(false);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TwitterClient, responseString);
            }

        });
    }


}
