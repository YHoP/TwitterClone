package com.epicodus.twitterclone.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by YHoP on 10/27/15.
 */

@Table(name = "Responses", id = "_id")
public class Response extends Model{
    @Column(name = "Content")
    private String mContent;

    @Column(name = "Tweet")
    private Tweet mTweet;

    public Response(){
        super();
    }

    public Response(String content, Tweet tweet){
        super();
        mContent = content;
        mTweet = tweet;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }
    public static List<Response> all(){
        return new Select()
                .from(Response.class)
                .execute();
    }

    public static Response find(String content) {
        return new Select()
                .from(Response.class)
                .where("Content = ?", content)
                .executeSingle();
    }
}
