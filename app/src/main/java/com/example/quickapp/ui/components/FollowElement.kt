package com.example.quickapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun FollowElement(
    modifier: Modifier = Modifier,
    title: String,
    count: Int,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable{ onClick()}
            .padding(4.dp)
    ) {

        Text(text = "$count",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(1.dp))
        Text(
            text = title,
            modifier = Modifier.padding(all = 8.dp),
            fontStyle = FontStyle.Normal,
            style = MaterialTheme.typography.bodySmall
        )
    }
}