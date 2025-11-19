package com.example.quickapp.domain.repo

import com.example.quickapp.domain.RetrofitInstance
import com.example.quickapp.model.Post

class PostRepository {

    suspend fun getPosts() = RetrofitInstance.api.getPosts()
    suspend fun getPost(id: Int) = RetrofitInstance.api.getPost(id)
    suspend fun createPost(post: Post) = RetrofitInstance.api.createPost(post)
}