package com.example.quickapp.domain.repo

import com.example.quickapp.domain.PostApi
import com.example.quickapp.model.Album
import com.example.quickapp.model.Comments
import com.example.quickapp.model.Photos
import com.example.quickapp.model.Post
import com.example.quickapp.model.Todos
import com.example.quickapp.model.User
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postApi: PostApi
) : PostRepository{
    override suspend fun getPosts(): List<Post> = postApi.getPosts()

    override suspend fun getPost(id: Int): Post = postApi.getPost(id)

    override suspend fun getUser(id: Int): User = postApi.getUser(id)

    override suspend fun getUserPosts(userId: Int): List<Post> = postApi.getUserPosts(userId)

    override suspend fun getUserAlbums(userId: Int): List<Album> = postApi.getUserAlbums(userId)

    override suspend fun getUsersTodos(userId: Int): List<Todos> = postApi.getUsersTodos(userId)

    override suspend fun getUsers(): List<User> = postApi.getUsers()

    override suspend fun getComments(): List<Comments> = postApi.getComments()

    override suspend fun getPostComments(postID: Int): List<Comments> = postApi.getPostComments(postID)

    override suspend fun getAlbumPhotos(albumId: Int): List<Photos> = postApi.getAlbumPhotos(albumId)

    override suspend fun createPost(post: Post) = postApi.createPost(post)
}