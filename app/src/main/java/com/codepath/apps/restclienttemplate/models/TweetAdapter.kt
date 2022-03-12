package com.codepath.apps.restclienttemplate.models

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.apps.restclienttemplate.R
import java.util.ArrayList

class TweetAdapter(val tweet:ArrayList<Tweet>): RecyclerView.Adapter<TweetAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

       val context=parent.context
        val inflater=LayoutInflater.from(context)
        val view=inflater.inflate(R.layout.iteim_tweet,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TweetAdapter.ViewHolder, position: Int) {
       val tweet:Tweet=tweet.get(position)
        holder.tvuserName.text=tweet.user?.name
        holder.tvtweetBody.text=tweet.body
        holder.tvTime.text=tweet.createdAt
        Glide.with(holder.itemView).load(tweet.user?.puplicImageUrl).into(holder.ivProfileImage)


    }

    override fun getItemCount(): Int {
       return tweet.size
    }
    fun clear() {
        tweet.clear()
        notifyDataSetChanged()
    }

    // Add a list of items -- change to type used
    fun addAll(tweetList: List<Tweet>) {
        tweet.addAll(tweetList)
        notifyDataSetChanged()
    }
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val ivProfileImage=itemView.findViewById<ImageView>(R.id.ivProfileImage)
        val tvuserName=itemView.findViewById<TextView>(R.id.tvUserName)
        val tvtweetBody=itemView.findViewById<TextView>(R.id.tvTweetBody)
        val tvTime=itemView.findViewById<TextView>(R.id.tvTimestamp)
    }


}