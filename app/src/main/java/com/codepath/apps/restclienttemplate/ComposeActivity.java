package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;


public class ComposeActivity extends AppCompatActivity {
    TwitterClient client;
    ArrayAdapter<String> tweetAdapter; //List view and Recycler view are views that show data
    private final int REQUEST_CODE = 20;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        client = TwitterApp.getRestClient(this); //gives access to send tweet function to client
        setTitle("Compose Tweet");
        Button button = findViewById(R.id.sendTweet);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etNewItem = (EditText) findViewById(R.id.et_simple);
                ///Potential Character count code
                String itemText = etNewItem.getText().toString();
                etNewItem.setText("");
                client.sendTweet(itemText, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        //convert each object to a Tweet model
                        //add that Tweet model to our data source
                        //notify the adapter that we've added an item
                        try {
                            Tweet tweet = Tweet.fromJSON(response);
                            Intent i = new Intent();
                            i.putExtra("Tweet", Parcels.wrap(tweet));
                            setResult(RESULT_OK, i);
                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        Log.d("TwitterClient", errorResponse.toString());
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.d("TwitterClient", errorResponse.toString());
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.d("TwitterClient", responseString);
                    }
                });




            }
        });
    }



}
