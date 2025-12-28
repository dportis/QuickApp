package com.example.quickapp.domain

import com.example.quickapp.model.Album
import com.example.quickapp.model.Comments
import com.example.quickapp.model.Photos
import com.example.quickapp.model.Post
import com.example.quickapp.model.Todos
import com.example.quickapp.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PostApi {
    @GET("posts")
    suspend fun getPosts() : List<Post>

    @GET("posts/{id}")
    suspend fun getPost(@Path("id") id : Int) : Post

    @GET("users")
    suspend fun getUsers() : List<User>

    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: Int): User

    @GET("users/{userId}/posts")
    suspend fun getUserPosts(@Path("userId") userId : Int) : List<Post>

    @GET("users/{userId}/albums")
    suspend fun getUserAlbums(@Path("userId") userId: Int) : List<Album>

    @GET("albums/{albumId}/photos")
    suspend fun getAlbumPhotos(@Path("albumId") albumId: Int): List<Photos>

    @GET("users/{userId}/todos")
    suspend fun getUsersTodos(@Path("userId") userId: Int): List<Todos>
    @POST("posts")
    suspend fun createPost(@Body post: Post) : Post

    @GET("comments")
    suspend fun getComments() : List<Comments>

    @GET("posts/{id}/comments")
    suspend fun getPostComments(@Path("id") id: Int) : List<Comments>
}