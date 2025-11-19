package com.example.quickapp.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.quickapp.AddPostScreen
import com.example.quickapp.PostDetailScreen
import com.example.quickapp.PostScreen
import com.example.quickapp.domain.repo.PostRepository
import com.example.quickapp.ui.theme.PostDetailViewModel
import com.example.quickapp.ui.theme.PostDetailViewModelFactory
import com.example.quickapp.ui.theme.PostViewModel
import com.example.quickapp.ui.theme.PostViewModelFactory

@Composable
fun AppNavHost(navController: NavHostController, repository: PostRepository) {
    NavHost(navController, startDestination = "post_list"){
        composable("post_list") {
            val viewModel : PostViewModel = viewModel(
                factory = PostViewModelFactory(repository)
            )
            PostScreen(viewModel = viewModel,
               onPostClick = {id -> navController.navigate("post_detail/$id")},
               onAddPostClick = {navController.navigate("add_post")})
       }
        composable("add_post") {
            val viewModel : PostViewModel = viewModel(
                factory = PostViewModelFactory(repository)
            )
            AddPostScreen(viewModel = viewModel ,onPostCreated = {title, body -> {
                navController.popBackStack()
            }},
                onCancel = {navController.popBackStack()})
        }
        composable("post_detail/{postID}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("postID")?.toInt() ?: 0
            println(id)
            val viewModel : PostDetailViewModel = viewModel(
                factory = PostDetailViewModelFactory(repository)
            )
            PostDetailScreen(detailViewModel = viewModel, postID = id, onBack = {navController.popBackStack()})
        }
    }
}