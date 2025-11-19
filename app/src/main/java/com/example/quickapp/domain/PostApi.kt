package com.example.quickapp.domain

import com.example.quickapp.model.Post
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PostApi {
    @GET("posts")
    suspend fun getPosts() : List<Post>

    @GET("posts/{id}")
    suspend fun getPost(@Path("id") id : Int) : Post

    @POST("posts")
    suspend fun createPost(@Body post: Post) : Post
}