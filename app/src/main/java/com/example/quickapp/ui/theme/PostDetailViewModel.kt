package com.example.quickapp.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickapp.domain.repo.PostRepository
import com.example.quickapp.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostDetailViewModel(private val repository: PostRepository) : ViewModel() {
    private val _post = MutableStateFlow<Post?>(null)

    val post: StateFlow<Post?> = _post


    fun loadPost(id: Int) {
        viewModelScope.launch {
            try {
                _post.value = repository.getPost(id)
            } catch (e: Exception) {
                println(e)
            }
        }
    }
}