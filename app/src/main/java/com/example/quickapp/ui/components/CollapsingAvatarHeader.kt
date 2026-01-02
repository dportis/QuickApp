package com.example.quickapp.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.example.quickapp.model.Post


@Composable
fun CollapsingAvatarHeader(
    collapseFraction: Float,
    userName: String,
    userAvatar: Int,
    userPosts: List<Post?>,
    onPostsClick: () -> Unit,
    onFollowersClick: () -> Unit = {},
    onPhotoClick: () -> Unit = {}
) {
    val avatarSize by animateDpAsState(
        targetValue = lerp(88.dp, 44.dp, collapseFraction),
        label = "avatarSize"
    )

    val userNameSize by animateFloatAsState(
        targetValue = lerp(20f, 12f, collapseFraction),
        label = "userNameSize"
    )

    val userNameAlpha by animateFloatAsState(
        targetValue = lerp(1f, 0.0f, collapseFraction),
        label = "userNameAlpha"
    )

    val statsAlpha by animateFloatAsState(
        targetValue = lerp(1f,0f, collapseFraction)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
        ) {
            // Avatar
            Image(
                painter = painterResource(userAvatar),
                contentDescription = null,
                modifier = Modifier
                    .size(avatarSize)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(
                modifier = Modifier.height(
                    lerp(12.dp, 4.dp, collapseFraction)
                )
            )

            Text(
                text = userName,
                fontSize = userNameSize.sp,
                modifier = Modifier
                    .alpha(userNameAlpha)
                    .width(116.dp),
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.alpha(statsAlpha),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FollowElement(
                title = "Posts",
                count = userPosts.size,
                onClick = onPostsClick)
            FollowElement(
                title = "Followers",
                count = 120,
                onClick = onFollowersClick)
            FollowElement(
                title = "Photos",
                count = 50,
                onClick = onPhotoClick)
        }
    }
}