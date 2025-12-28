package com.example.quickapp.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickapp.domain.repo.PostRepository
import com.example.quickapp.model.Album
import com.example.quickapp.model.Photos
import com.example.quickapp.model.Post
import com.example.quickapp.model.Todos
import com.example.quickapp.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserProfileViewModel(private val repository: PostRepository): ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    private val _albums = MutableStateFlow<List<Album>>(emptyList())
    private val _photos = MutableStateFlow<List<Photos>>(emptyList())
    private val _todos = MutableStateFlow<List<Todos>>(emptyList())
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val user: StateFlow<User?> = _user
    val albums: StateFlow<List<Album>> = _albums
    val photos: StateFlow<List<Photos>> = _photos
    val todos: StateFlow<List<Todos>> = _todos
    val posts : StateFlow<List<Post>> = _posts


    fun loadUser(userId: Int) {
        viewModelScope.launch {
            try {
                _user.value = repository.getUser(userId)
            } catch (e: Exception) {
                println("exception ${e.message}")
            }
        }
    }
    fun loadUserAlbums(userId: Int) {
        viewModelScope.launch {
            try {
                _albums.value = repository.getUserAlbums(userId)
            } catch (e: Exception) {
                println("exception ${e.message}")
            }
        }
    }

    fun loadAlbumPhotos(albumId: Int) {
        viewModelScope.launch {
            try {
                _photos.value = repository.getAlbumPhotos(albumId)
            } catch (e: Exception) {
                println("exception ${e.message}")
            }
        }
    }

    fun loadUserTodos(userId: Int) {
        viewModelScope.launch {
            try {
                _todos.value = repository.getUsersTodos(userId)
            } catch (e: Exception) {
                println("todos ${e.message}")
            }
        }
    }

    fun loadUsersPosts(userId: Int) {
        viewModelScope.launch {
            try {
                _posts.value = repository.getUserPosts(userId)
            } catch (e: Exception) {
                println("posts ${e.message}")
            }
        }
    }
}