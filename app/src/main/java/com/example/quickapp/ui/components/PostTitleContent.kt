package com.example.quickapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PostTitleContent(title: String?, commentCount: Int, onCommentsClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(top = 28.dp, bottom = 20.dp, start = 10.dp, end = 10.dp)

    ) {
        Text(text = title ?: "", style = MaterialTheme.typography.titleMedium)
        Text(text = "$commentCount Comments",
            modifier = Modifier
                .clickable(
                    enabled = true,
                    onClick = onCommentsClick
                )
                .padding(vertical = 4.dp),

            style = MaterialTheme.typography.bodySmall)
    }
}