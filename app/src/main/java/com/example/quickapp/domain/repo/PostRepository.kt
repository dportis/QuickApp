package com.example.quickapp.domain.repo

import com.example.quickapp.domain.RetrofitInstance
import com.example.quickapp.model.Post

class PostRepository {

    suspend fun getPosts() = RetrofitInstance.api.getPosts()
    suspend fun getPost(id: Int) = RetrofitInstance.api.getPost(id)
    suspend fun getUserPosts(userId: Int) = RetrofitInstance.api.getUserPosts(userId)
    suspend fun createPost(post: Post) = RetrofitInstance.api.createPost(post)
    suspend fun getUsers() = RetrofitInstance.api.getUsers()
    suspend fun getUser(userId: Int) = RetrofitInstance.api.getUser(userId)
    suspend fun getUserAlbums(userId: Int) = RetrofitInstance.api.getUserAlbums(userId)
    suspend fun getAlbumPhotos(albumId: Int) = RetrofitInstance.api.getAlbumPhotos(albumId)
    suspend fun getUsersTodos(userId: Int) = RetrofitInstance.api.getUsersTodos(userId)
    suspend fun getComments() = RetrofitInstance.api.getComments()
    suspend fun getPostComments(postID: Int) = RetrofitInstance.api.getPostComments(postID)
}