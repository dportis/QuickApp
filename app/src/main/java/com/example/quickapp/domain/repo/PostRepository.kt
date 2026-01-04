package com.example.quickapp.domain.repo

import com.example.quickapp.model.Album
import com.example.quickapp.model.Comments
import com.example.quickapp.model.Photos
import com.example.quickapp.model.Post
import com.example.quickapp.model.Todos
import com.example.quickapp.model.User

interface PostRepository {
    suspend fun getPosts(): List<Post>
    suspend fun getPost(id: Int): Post
    suspend fun getUser(id: Int): User
    suspend fun getUserPosts(userId: Int): List<Post>
    suspend fun getUserAlbums(userId: Int): List<Album>
    suspend fun getUsersTodos(userId: Int): List<Todos>
    suspend fun getUsers() : List<User>
    suspend fun getComments(): List<Comments>
    suspend fun getPostComments(postID: Int): List<Comments>
    suspend fun getAlbumPhotos(albumId: Int) : List<Photos>

    suspend fun createPost(post: Post): Post

}