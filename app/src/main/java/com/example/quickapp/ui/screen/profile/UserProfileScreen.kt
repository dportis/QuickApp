package com.example.quickapp.ui.screen.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.quickapp.model.UserPersona
import com.example.quickapp.ui.components.UserProfileHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    userId: Int,
    userViewModel: UserProfileViewModel,
    onBack: () -> Unit,
    onPhotoClicked: (Int) -> Unit,
    onPostsClicked: () -> Unit,
    onFollowersClicked: () -> Unit) {

    val userState = userViewModel.user.collectAsState()
    val albumsState = userViewModel.albums.collectAsState()
    val postsState = userViewModel.posts.collectAsState()
    val todosState = userViewModel.todos.collectAsState()

    val name = UserPersona.fromId(userState.value?.id ?: 0)
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val collapseFraction = scrollBehavior.state.collapsedFraction

    LaunchedEffect(userId) {
        userViewModel.loadUser(userId)
        userViewModel.loadUserAlbums(userId)
        userViewModel.loadUserTodos(userId)
        userViewModel.loadUsersPosts(userId)
    }

    Scaffold(
//        modifier = modifier.nestedScroll()
        topBar = {
            TopAppBar(
                title = { Text(name.displayName)},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()) {

            val user = userState.value
            if (user != null){
                val persona = UserPersona.fromId(user.id)
                item {
                    UserProfileHeader(
                        user = user,
                        userAvatar = persona.avatarResource,
                        userPosts = postsState.value,
                        collapseFraction = collapseFraction,
                        onPostClick = onPostsClicked,
                        onFollowerClick = onFollowersClicked,
                        albums = albumsState.value,
                        todos = todosState.value)
                }
            }
        }
    }
}