package com.example.quickapp.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.quickapp.model.Album
import com.example.quickapp.model.Post
import com.example.quickapp.model.Todos
import com.example.quickapp.model.User
import com.example.quickapp.model.UserPersona

@Composable
fun UserProfileHeader(
    user: User,
    userAvatar: Int,
    userPosts: List<Post>,
    collapseFraction: Float,
    albums: List<Album>,
    todos: List<Todos>,
    onPostClick: () -> Unit,
    onFollowerClick: () -> Unit,
    modifier: Modifier = Modifier) {

    val persona = UserPersona.fromId(user.id)
    CollapsingAvatarHeader(
        collapseFraction = collapseFraction,
        userName = user.userName,
        userAvatar = userAvatar,
        userPosts = userPosts,
        onPostsClick = onPostClick,
        onFollowersClick = onFollowerClick
    )

    Spacer(Modifier.width(16.dp))

    LazyRow {
        items(albums) {
            album ->
            AlbumItem(album = album)
        }
    }
    Spacer(Modifier.height(16.dp))


}