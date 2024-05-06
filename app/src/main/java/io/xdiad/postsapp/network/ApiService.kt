package io.xdiad.postsapp.network

import io.xdiad.postsapp.model.Post
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("posts")
    suspend fun getPosts(@Query("_page") page: Int, @Query("_limit") limit: Int): Response<List<Post>>

    companion object {
        fun create(): ApiService {
            return Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}