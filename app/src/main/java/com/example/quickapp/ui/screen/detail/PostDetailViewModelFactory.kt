package com.example.quickapp.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.quickapp.domain.repo.PostRepository

class PostDetailViewModelFactory(
    private val repository: PostRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(PostDetailViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return PostDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}