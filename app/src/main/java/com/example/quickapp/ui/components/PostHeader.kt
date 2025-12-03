package com.example.quickapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.quickapp.model.Post

@Composable
fun PostHeader(
    userName: String,
    userAvatar: Int,
    title: String,
    commentCount: Int,
    collapseFraction: Float,
    userPosts: List<Post?>,
    onCommentsClick: () -> Unit,
    onPostsClick: () -> Unit,
    modifier: Modifier = Modifier) {

    CollapsingAvatarHeader(
        collapseFraction = collapseFraction,
        userName = userName,
        userAvatar = userAvatar,
        userPosts = userPosts,
        onPostsClick = onPostsClick
    )

    Spacer(Modifier.width(16.dp))

    Row {
        Column() {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            )

            Button(
                onClick = onCommentsClick,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(4.dp)
            ) {
                Text(
                    text = "$commentCount comments",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}