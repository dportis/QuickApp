package com.example.quickapp.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickapp.domain.repo.PostRepository
import com.example.quickapp.model.Comments
import com.example.quickapp.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostDetailViewModel(private val repository: PostRepository) : ViewModel() {
    private val _post = MutableStateFlow<Post?>(null)
    private val _posts = MutableStateFlow<List<Post?>>(emptyList())
    private val _comments = MutableStateFlow<List<Comments>>(emptyList())

    val post: StateFlow<Post?> = _post
    val posts: StateFlow<List<Post?>> = _posts
    val comments: StateFlow<List<Comments>> = _comments

    fun loadPost(id: Int) {
        viewModelScope.launch {
            try {
                _post.value = repository.getPost(id)
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    fun loadAllComments() {
        viewModelScope.launch {
            try {
                _comments.value = repository.getComments()
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    fun loadPostComments(id: Int) {
        viewModelScope.launch {
            try {
                _comments.value = repository.getPostComments(id)
            } catch (e: Exception){
                println(e)
            }
        }
    }

    fun loadUserPosts(userId: Int) {
        viewModelScope.launch {
            try {
                _posts.value = repository.getUserPosts(userId)
            } catch (e: Exception) {
                println(e)
            }
        }
    }

}