package com.example.quickapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.quickapp.model.Post

@Composable
fun PostItem(post: Post, onPostClick: (Int) -> Unit ,  modifier: Modifier) {
    Column(modifier = modifier.clickable{
        onPostClick(post.id)
    }) {
        Text(text = post.title, style = MaterialTheme.typography.titleLarge)
        Text(text = post.body, style = MaterialTheme.typography.bodyMedium)
        HorizontalDivider(modifier = Modifier.padding(horizontal = 4.dp), thickness = 1.dp )
    }
}