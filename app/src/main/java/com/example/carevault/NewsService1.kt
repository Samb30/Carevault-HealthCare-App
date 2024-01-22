package com.example.carevault

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsInterface1 {
    @GET("top-headlines")
    fun getHeadlines(@Query("country")countryCode:String,@Query("apiKey")apiKey:String= API_KEY) : Call<news1>
}
object NewsService1{
    val newsInstance1:NewsInterface1
    val url="https://newsapi.org/v2/"
    init {
        val retrofit= Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newsInstance1=retrofit.create(NewsInterface1::class.java)
    }
}