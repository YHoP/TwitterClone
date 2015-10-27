package com.epicodus.twitterclone.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.epicodus.twitterclone.R;
import com.epicodus.twitterclone.models.Tweet;
import com.epicodus.twitterclone.models.User;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class TweetActivity extends AppCompatActivity {

    private Tweet mTweet;
    private User mUser;
    private TextView mTweetContentLabel, mTweetUserLabel, mtweetDateLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);

        mTweetContentLabel = (TextView)findViewById(R.id.tweetContentLabel);
        mTweetUserLabel = (TextView)findViewById(R.id.tweetUserLabel);
        mtweetDateLabel = (TextView)findViewById(R.id.tweetDateLabel);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        mTweetContentLabel.setText(bundle.getString("content"));
        mTweetUserLabel.setText("By: " + bundle.getString("user"));
        mtweetDateLabel.setText(formattedTime(bundle.getLong("createdat")));

    }

    private String formattedTime(long createdat) {
        Context context = MainActivity.mAdapter.getContext();
        SimpleDateFormat formatter = new SimpleDateFormat(context.getString(R.string.formatted_time));
        formatter.setTimeZone(TimeZone.getTimeZone(context.getString(R.string.timezone)));
        return formatter.format(createdat);
    }

}
