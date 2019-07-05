package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Tweet {
    // list attributes
    public String body;
    public long uid;
    public User user;
    public String createdAt;
    public String mediaURL;
    //deserialize JSON
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException{
        Tweet tweet = new Tweet();
        // extract the values from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid=jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        try {
            tweet.mediaURL = jsonObject.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getString("media_url_https");
        }
        catch(JSONException e){
            Log.e("JSON","no media url");
        }
        return tweet;

    }
}
