package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>{

    private List<Tweet> mTweets;
    Context context;
    //pass in Tweets array into constructor
    public TweetAdapter(List<Tweet> tweets){
        mTweets = tweets;
    }

    //for each row, inflate the layout and ache references into ViewHolder

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {//only done when creating new row
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);//inflate/render tweet row
        ViewHolder viewHolder = new ViewHolder(tweetView); //create view holder for tweets
        return viewHolder;
    }


    //bind the values based on the position of the element

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {//repopulates data based on viewHolder
        //called after matching xmls
        //get the data according to position
        //final step in showing data to recycler view
        Tweet tweet = mTweets.get(position); //mTweets is reference of Tweets that we carry

        //populate the views according to this data
        holder.tvUserName.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
        holder.tvTimestamp.setText(getRelativeTimeAgo(tweet.createdAt));

        Glide.with(context).load(tweet.user.profileImageUrl).into(holder.ivProfileImage);
        //Glide.with(context).load(tweet.entities.mediaImageUrl).into(holder.ivMedia);

    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    //create ViewHolder class

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivProfileImage; //matches xml name
        public TextView tvUserName; //matches xml name
        public TextView tvBody; //matches xml name
        public TextView tvTimestamp;
        //public ImageView ivMedia;///MEDIA

        public ViewHolder(View itemView){ //constructor takes inflated view as parameter
            //called before binder
            super(itemView);

            //perform findViewById lookups
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvTimestamp = (TextView) itemView.findViewById(R.id.tvTimestamp);
            //ivMedia = (ImageView) itemView.findViewById(R.id.ivMedia);







        }
    }
    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
    // Clean all elements of the recycler
    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        mTweets.addAll(list);
        notifyDataSetChanged();
    }

}
