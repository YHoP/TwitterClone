package com.epicodus.twitterclone.ui;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.epicodus.twitterclone.R;
import com.epicodus.twitterclone.models.Response;
import com.epicodus.twitterclone.models.Tweet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

public class TweetActivity extends ListActivity {

    private Tweet mTweet;
    private TextView mTweetContentLabel, mTweetUserLabel, mtweetDateLabel;
    private Button mRespondButton;
    private EditText mRespondText;
    private ArrayList<String> mResponse;
    private ArrayAdapter<String> mAdapter;

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

        mTweet = Tweet.find(bundle.getString("content"));

        mRespondButton = (Button)findViewById(R.id.respondButton);
        mRespondText = (EditText)findViewById(R.id.respondText);
        mResponse = new ArrayList<>();

        for (Response response : mTweet.response()){
            mResponse.add(response.getContent());
        }
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mResponse);
        setListAdapter(mAdapter);

        mRespondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRespond();
            }
        });
    }

    private void addRespond() {
        String respondText = mRespondText.getText().toString();
        Response newResponse = new Response(respondText, mTweet);
        newResponse.save();
        mResponse.add(respondText);
        mAdapter.notifyDataSetChanged();
    }

    private String formattedTime(long createdat) {
        Context context = MainActivity.mAdapter.getContext();
        SimpleDateFormat formatter = new SimpleDateFormat(context.getString(R.string.formatted_time));
        formatter.setTimeZone(TimeZone.getTimeZone(context.getString(R.string.timezone)));
        return formatter.format(createdat);
    }

}
