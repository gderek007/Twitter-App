package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity<button> extends AppCompatActivity  {
    public static final String RESULT_TWEET= "result_tweet";
    Button button;
    TextView compose;
    TwitterClient client;
    EditText charView;
    EditText edits;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        button = findViewById(R.id.btnSend);
        compose = findViewById(R.id.ivCompose);
        charView = findViewById(R.id.charView);
        client=TwitterApp.getRestClient(this);
        //  TODO--character view
//        charView.addTextChangedListener(new TextWatcher()
//        {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count)
//            {int length = s.length();
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int aft)
//            {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s)
//            {
//                // this will show characters remaining
//                charView.setText(280 - s.toString().length() + "/280");
//            }
//        });
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                sendTweet();
               // onSubmit();
            }
        });
    }
    public void onSubmit() {
        // closes the activity and returns to first screen
        this.finish();
    }

    private void sendTweet(){
        client.sendTweet(compose.getText().toString(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try{
                    if (statusCode==200){
                        JSONObject responseJson = new JSONObject(new String(responseBody));
                        Tweet resultTweet = Tweet.fromJSON(responseJson);

                        Intent resultData = new Intent();
                        //wrap parcel
                        resultData.putExtra(RESULT_TWEET, Parcels.wrap(resultTweet));
                        setResult(RESULT_OK,resultData);
                        finish();

                    }
                }
                catch(JSONException e){
                    Log.e("Did not work","Different error codes");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("does not wor",responseBody.toString());
            }
        });
    }
}

