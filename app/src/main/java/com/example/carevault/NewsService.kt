package com.example.carevault

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
//https://newsapi.org/v2/top-headlines?country=us&apiKey=5778d4e963de4955979ec4d349fcb16d
//https://newsapi.org/v2/top-headlines?apiKey=$5778d4e963de4955979ec4d349fcb16d&county=in&page=1
const val API_KEY ="5778d4e963de4955979ec4d349fcb16d"
const val BASE_URL="https://newsapi.org/"
interface NewsInterface {
    @GET("v2/top-headlines")
    fun getHeadlines(@Query("country")countryCode:String,@Query("category")categoryWise:String,@Query("apiKey")apiKey:String= API_KEY) : Call<news1>
}
//,@Query("category")category:String="sports"
object NewsService{
    val newsInstance:NewsInterface
    //val url="https://quotable.io/"
    val url="https://newsapi.org/v2/top-headlines?country=us&apiKey=5778d4e963de4955979ec4d349fcb16d"
    init {
        val retrofit=Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newsInstance=retrofit.create(NewsInterface::class.java)
    }
}
