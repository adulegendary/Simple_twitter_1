package com.codepath.apps.restclienttemplate


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.constraintlayout.motion.utils.Oscillator.TAG
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codepath.apps.restclienttemplate.models.Tweet
import com.codepath.apps.restclienttemplate.models.TweetAdapter

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.android.material.tabs.TabLayout
import okhttp3.Headers
import org.json.JSONException

class TimelineActivity : AppCompatActivity() {
    lateinit var swipeContainer: SwipeRefreshLayout
    lateinit var client:TwitterClient

    lateinit var rvTweets:RecyclerView
    lateinit var adapter: TweetAdapter
    val tweet=ArrayList<Tweet>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)

        client=TwitterApplication.getRestClient(this)
       swipeContainer=findViewById(R.id.swipeContainer)

        swipeContainer.setOnRefreshListener {
            Log.i(TAG,"Refreshing timing")
            populateHomeTimeline()
        }

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
        rvTweets=findViewById(R.id.rvTweet)
        adapter= TweetAdapter(tweet)
        rvTweets.layoutManager=LinearLayoutManager(this)
        rvTweets.adapter=adapter
        populateHomeTimeline()
    }
    fun populateHomeTimeline(){
        client.getHomeTimeLine(object :JsonHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG,"onSuccess! $json")
                val  jsonArray=json.jsonArray
               try {
                   //clear out the old to get the new update tweets
                   adapter.clear()
                   val listOfNewTweets=Tweet.fromJsonArray(jsonArray)
                   tweet.addAll(listOfNewTweets)
                   adapter.notifyDataSetChanged()
                   // Now we call setRefreshing(false) to signal refresh has finished
                   swipeContainer.setRefreshing(false)
               } catch (e:JSONException){
                   Log.e(TAG,"JSON Exception $e")
               }

            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.i(TAG,"ONfAILURE")
            }



        })
    }
}