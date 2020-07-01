package com.codepath.apps.restclienttemplate.models;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder>{
    Context context;
    List<Tweet> tweets;

    // Pass in the context and list of tweets
    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    // For each row, inflate the layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    // Bind values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data at position
        Tweet tweet = tweets.get(position);

        // Bind the tweet with view holder
        holder.bind(tweet);
    }

    // Get number of Tweets
    @Override
    public int getItemCount() {
        return tweets.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of Tweets
    public void addAll(List<Tweet> tweetList){
        tweets.addAll(tweetList);
        notifyDataSetChanged();
    }



    // Define a ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Define Views within each Tweet
        ImageView ivProfileImage;
        ImageView ivTweetImage;
        TextView tvTweetText;
        TextView tvUserName;

        // ViewHolder constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            ivTweetImage =  itemView.findViewById(R.id.ivTweetImage);
            tvTweetText = itemView.findViewById(R.id.tvTweetText);
            tvUserName = itemView.findViewById(R.id.tvUserName);
        }

        // Function to bind information to view
        public void bind(Tweet tweet){
            // Set text values for TextViews
            tvTweetText.setText(tweet.body);
            tvUserName.setText(tweet.user.userName);

            // Set image for ImageView
            Glide.with(context)
                    .load(tweet.user.profileImageUrl)
                    .into(ivProfileImage);

            // Set image for ivTweetImage
            if(tweet.entities != null) {
                if (tweet.entities.media != null) {
                    Log.i("TweetsAdapter", "bind: Attaching tweet image");
                    Glide.with(context)
                            .load(tweet.entities.media.get(0).mediaUrl)
                            .into(ivTweetImage);
                    Log.i("TweetsAdapter", tweet.entities.media.get(0).mediaUrl);

                    // Set View to VISIBLE in the case that View is GONE (below else-if)
                    ivTweetImage.setVisibility(View.VISIBLE);
                }
            }
            // If View is Visible and if mediaUrl doesn't exist, set Visibility to GONE
            else if (ivTweetImage.getVisibility() == View.VISIBLE){
                ivTweetImage.setVisibility(View.GONE);
            }
        }
    }
}
