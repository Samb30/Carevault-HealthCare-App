package com.example.carevault

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
//https://newsapi.org/v2/everything?q=trending&sortBy=popularity&apiKey=5778d4e963de4955979ec4d349fcb16d
//https://newsapi.org/v2/everything?q=apple&from=2023-07-20&to=2023-07-20&sortBy=popularity&apiKey=55888d3d22294f5b99d0e4d851ae0505
interface NewsInterface2 {
    @GET("everything")
    fun getHeadlines(@Query("q")q:String,
                     @Query("from")from:String="2024-03-15",@Query("to")to:String="2025-12-25",
                     @Query("sortBy")sortBy:String="popularity",
                     @Query("apiKey")apiKey:String= API_KEY) : Call<news1>
}
object NewsService2 {
    val newsInstance2: NewsInterface2
    val url = "https://newsapi.org/v2/"

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newsInstance2 = retrofit.create(NewsInterface2::class.java)
    }
}