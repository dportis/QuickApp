package com.example.quickapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.quickapp.domain.repo.PostRepository
import com.example.quickapp.ui.screen.AddPostScreen
import com.example.quickapp.ui.screen.detail.PostDetailScreen
import com.example.quickapp.ui.screen.detail.PostDetailViewModel
import com.example.quickapp.ui.screen.detail.PostDetailViewModelFactory
import com.example.quickapp.ui.screen.home.PostScreen
import com.example.quickapp.ui.screen.home.PostViewModel
import com.example.quickapp.ui.screen.home.PostViewModelFactory
import com.example.quickapp.ui.screen.profile.UserProfileScreen
import com.example.quickapp.ui.screen.profile.UserProfileViewModel
import com.example.quickapp.ui.screen.profile.UserProfileViewModelFactory

@Composable
fun AppNavHost(navController: NavHostController, repository: PostRepository) {
    NavHost(navController, startDestination = "post_list"){
        composable("post_list") {
            val viewModel : PostViewModel = viewModel(
                factory = PostViewModelFactory(repository)
            )
            PostScreen(viewModel = viewModel,
               navController = navController,
               onPostClick = {id -> navController.navigate("post_detail/$id")},
               onAddPostClick = {navController.navigate("add_post")},
               onHeaderClick = {userId -> navController.navigate("profile_screen/$userId")})
       }
        composable("add_post") {
            val viewModel : PostViewModel = viewModel(
                factory = PostViewModelFactory(repository)
            )
            AddPostScreen(viewModel = viewModel,
                onPostCreated = {title, body -> { navController.popBackStack()
            }},
                onCancel = {navController.popBackStack()})
        }
        composable("post_detail/{postId}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("postId")?.toInt() ?: 0
            val viewModel : PostDetailViewModel = viewModel(
                factory = PostDetailViewModelFactory(repository)
            )
            PostDetailScreen(detailViewModel = viewModel,
                postId = id,
                onBack = {navController.popBackStack()},
                onPostsClicked = {userId -> navController.navigate("user/$userId/posts")})
        }
        composable("user/{userId}/posts") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toInt() ?: 0
            val viewModel : PostViewModel = viewModel(
                factory = PostViewModelFactory(repository)
            )

            LaunchedEffect(userId) {
                viewModel.loadUserPosts(userId)
            }
            PostScreen(viewModel,
                onPostClick = {id -> navController.navigate("post_detail/$id")},
                navController = navController,
                onAddPostClick = {navController.navigate("add_post")},
                onHeaderClick = {})
        }
        composable("home_screen") {
            val viewModel : PostViewModel = viewModel(
                factory = PostViewModelFactory(repository)
            )
            PostScreen(viewModel = viewModel,
                navController = navController,
                onPostClick = {id -> navController.navigate("post_detail/$id")},
                onAddPostClick = {navController.navigate("add_post")},
                onHeaderClick = {})
        }
        composable("profile_screen/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toInt() ?: 0
            val viewModel : UserProfileViewModel = viewModel(
              factory = UserProfileViewModelFactory(repository)
            )

            println("userId for profile screen $userId")

            UserProfileScreen(
                userId = userId,
                userViewModel = viewModel,
                onBack = {navController.popBackStack()},
                onPhotoClicked = {},
                onPostsClicked = { },
                onFollowersClicked = {},
            )
        }
    }
}