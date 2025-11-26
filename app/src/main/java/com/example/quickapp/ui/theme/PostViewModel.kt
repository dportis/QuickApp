package com.example.quickapp.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickapp.domain.repo.PostRepository
import com.example.quickapp.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PostViewModel(private val repository: PostRepository) : ViewModel() {

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    private val _query = MutableStateFlow("")
    private val _title = MutableStateFlow("")
    private val _body = MutableStateFlow("")
    val posts : StateFlow<List<Post>> get() = _posts
    val query: StateFlow<String> = _query
    val title: StateFlow<String> = _title
    val body: StateFlow<String> = _body

    val filteredPosts = combine(posts, query) {posts, q ->
        if (q.isBlank()) posts else posts.filter { it.title.contains(q, ignoreCase = true) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())



    init {
        fetchPosts()
    }

    private fun fetchPosts(){
        viewModelScope.launch {
            try {
                _posts.value = repository.getPosts()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun createPost(title: String, body: String) {
        viewModelScope.launch {
            try {
                val newPost = Post(
                    userId = 1,
                    id = 0,
                    title = title,
                    body = body
                )
                val createdPost = repository.createPost(newPost)

                _posts.value = _posts.value + createdPost
            } catch (e: java.lang.Exception){
                e.printStackTrace()
            }
        }
    }

    fun updateQuery(value: String) {_query.value = value}

    fun updateTitle(value: String) {_title.value = value}
    fun updateBody(value: String) {_body.value = value}
}