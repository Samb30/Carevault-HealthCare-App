package com.example.carevault.Articles

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.carevault.Adapters.NewsClicked
import com.example.carevault.Adapters.NewwsAdapter
import com.example.carevault.NewsService
import com.example.carevault.NewsService2
import com.example.carevault.R
import com.example.carevault.news1
import com.example.carevault.news2
import retrofit2.Call
import retrofit2.Callback

class RelatedArticles : AppCompatActivity(), NewsClicked {
    private lateinit var mAdapter: NewwsAdapter
    lateinit var recyclerview: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disease)
        recyclerview = findViewById<RecyclerView>(R.id.recycleview)
        val fitness = findViewById<TextView>(R.id.fixed)
        val swipe=findViewById<SwipeRefreshLayout>(R.id.swipe)
        val layoutManager= LinearLayoutManager(this)
        val back = findViewById<ImageButton>(R.id.imageButton112)
        back.setOnClickListener {
            onBackPressed()
        }

        getNews()
        getNews()
        swipe.setOnRefreshListener {
            getNews()
            swipe.isRefreshing=false
        }
    }
    private fun getNews() {
//        val url1 = intent.getStringExtra("message_key").toString()
//        val verify=intent.getStringExtra("message_key2").toString()
//        var news=NewsService.newsInstance.getHeadlines("in", url1.toString())
//        if(url1.equals("sports")){
//            news=NewsService2.newsInstance2.getHeadlines("cricket")
//        }else if(url1.equals("us")){
//            news=NewsService1.newsInstance1.getHeadlines("us")
//        }else if(url1.equals("au")){
//            news=NewsService1.newsInstance1.getHeadlines("au")
//        }else if(url1.equals("gb")){
//            news=NewsService1.newsInstance1.getHeadlines("gb")
//        }else if(url1.equals("fr")){
//            news=NewsService1.newsInstance1.getHeadlines("fr")
//        }else if(url1.equals("ru")){
//            news= NewsService1.newsInstance1.getHeadlines("ru")
//        }else if(verify.equals("Trending")){
//            news=NewsService1.newsInstance1.getHeadlines("in")
//        }else if(verify.equals("World")){
//            news= NewsService2.newsInstance2.getHeadlines("world breaking")
//        }else {
//            news = NewsService.newsInstance.getHeadlines("in", url1.toString())
//        }
        var news= NewsService.newsInstance.getHeadlines("in", "")
        news= NewsService2.newsInstance2.getHeadlines("disease")
        news.enqueue(object: Callback<news1> {
            override fun onResponse(call: Call<news1>, response: retrofit2.Response<news1>) {
                val news=response.body()
                if(news!=null){
                    Log.d("news",news.toString())
                    val layoutManager= LinearLayoutManager(this@RelatedArticles)
                    recyclerview.layoutManager = layoutManager
                    recyclerview.addItemDecoration(DividerItemDecoration(baseContext,layoutManager.orientation))
                    mAdapter = NewwsAdapter(this@RelatedArticles,this@RelatedArticles, news.articles)
                    recyclerview.adapter = mAdapter
                }
            }

            override fun onFailure(call: Call<news1>, t: Throwable) {
                Log.d("news","error",t)
            }
        })
    }
    override fun onItemClicked(articles: news2) {
        val builder= CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(articles.url))
    }
}