package com.codepath.apps.restclienttemplate.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

//Pass in context and list of Tweets
//for each row, inflate layout
//bind data based on the position of the element
//define view holder
public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {
    private Context context;

    //Pass in context and list of Tweets
    public TweetAdapter(Context context, List<Tweet> tweetList) {
        this.context = context;
        this.tweetList = tweetList;
    }

    private List<Tweet> tweetList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tweet_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Tweet currentTweet = tweetList.get(position);
        viewHolder.bind(currentTweet);
    }

    @Override
    public int getItemCount() {
        return tweetList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgvProfile;
        private TextView tvScreenName, tvBody;

        public ViewHolder(View itemView) {
            super(itemView);
            imgvProfile = itemView.findViewById(R.id.imgV_profile);
            tvScreenName = itemView.findViewById(R.id.tv_screenName);
            tvBody = itemView.findViewById(R.id.tv_body);
        }

        public void bind(Tweet tweet) {
            tvScreenName.setText(tweet.getUser().getScreenName());
            tvBody.setText(tweet.getBody());
            Glide.with(context).load(tweet.getUser().getProfileImUrl()).into(imgvProfile);
        }
    }
}
