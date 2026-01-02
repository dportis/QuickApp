package com.example.quickapp.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
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

    Spacer(Modifier.height(8.dp))

    Text(
        text = "Albums",
        style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
    LazyRow(modifier = Modifier.padding(horizontal = 8.dp)) {
        items(albums) {
            album ->
            AlbumItem(album = album)
            Spacer(Modifier.width(4.dp))
        }
    }
    Spacer(Modifier.height(16.dp))


}