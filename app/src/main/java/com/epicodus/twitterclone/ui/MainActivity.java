package com.epicodus.twitterclone.ui;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.epicodus.twitterclone.R;
import com.epicodus.twitterclone.adapters.TweetAdapter;
import com.epicodus.twitterclone.models.Tweet;
import com.epicodus.twitterclone.models.User;

import java.util.ArrayList;


public class MainActivity extends ListActivity {

    public static String TAG = MainActivity.class.getSimpleName();
    public static final String TWEET = "Tweet";

    private SharedPreferences mPreferences;
    private User mUser;
    private EditText mTweetText;
    private Button mSubmitButton;
    private ArrayList mTweets;
    public static TweetAdapter mAdapter;
    private ImageView mSearchImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreferences = getApplicationContext().getSharedPreferences("twitter", Context.MODE_PRIVATE);

        mTweetText = (EditText) findViewById(R.id.newTweetEdit);
        mSubmitButton = (Button) findViewById(R.id.tweetSubmitButton);
        mTweets = (ArrayList) Tweet.all();
        mSearchImage = (ImageView) findViewById(R.id.searchButtonImage);

        mAdapter = new TweetAdapter(this, mTweets);
        setListAdapter(mAdapter);

        if (!isRegistered()) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tweetContent = mTweetText.getText().toString();
                Tweet tweet = new Tweet(tweetContent, mUser);
                tweet.save();
                tweet.parseHashTags();

                mTweets.add(tweet);
                mAdapter.notifyDataSetChanged();
                mTweetText.getText().clear();

                /* Clears input and hides keyboard
                mTweetText.setText("");
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                */

            }
        });

        mSearchImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);

            }
        });
    }

    private boolean isRegistered() {
        String username = mPreferences.getString("username", null);
        if (username == null) {
            return false;
        } else {
            setUser(username);
            return true;
        }
    }

    private void setUser(String username) {
        User user = User.find(username);
        if (user != null) {
            mUser = user;
        } else {
            mUser = new User(username);
            mUser.save();
        }
        Toast.makeText(this, "Welcome " + mUser.getName(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Tweet thisTweet = (Tweet) mAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putString("content", thisTweet.getContent());
        bundle.putString("user", thisTweet.getUser().getName());
        bundle.putLong("createdat", thisTweet.getCreatedAt());

        Intent intent = new Intent(this, TweetActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
