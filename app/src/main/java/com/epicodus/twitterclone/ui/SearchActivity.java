package com.epicodus.twitterclone.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.twitterclone.R;
import com.epicodus.twitterclone.adapters.TweetAdapter;
import com.epicodus.twitterclone.models.Tag;
import com.epicodus.twitterclone.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guest on 10/28/15.
 */
public class SearchActivity extends ListActivity{

    private EditText mSearchText;
    private Button mSearchButton;
    private TextView mEmptyText;
    private TweetAdapter mAdapter;
    private ArrayList<Tweet> mTweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mSearchText = (EditText)findViewById(R.id.searchText);
        mSearchButton = (Button)findViewById(R.id.searchButton);
        mEmptyText = (TextView)findViewById(android.R.id.empty);
        mTweets = new ArrayList<>();
        mAdapter = new TweetAdapter(this, mTweets);
        setListAdapter(mAdapter);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag = "#" + mSearchText.getText().toString();
                Tag hashtag = Tag.find(tag);

                if (hashtag == null ){
                    Toast.makeText(SearchActivity.this, mSearchText.getText().toString() + " not found", Toast.LENGTH_LONG).show();
                    mSearchText.setText("");
                    Intent intent = new Intent(SearchActivity.this, SearchActivity.class);
                    startActivity(intent);

                }else {
                    mTweets.clear();
                    List<Tweet> tweetsToAdd = hashtag.getTweets();
                    for(Tweet tweet : tweetsToAdd){
                        mTweets.add(tweet);
                    }
                    mAdapter.notifyDataSetChanged();
                }

            }
        });

    }
}
