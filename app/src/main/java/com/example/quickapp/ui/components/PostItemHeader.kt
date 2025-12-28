package com.example.quickapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.quickapp.model.UserPersona

@Composable
fun PostItemHeader(userId: Int, onHeaderClick: (Int) -> Unit, modifier: Modifier = Modifier) {
    val userPersona = UserPersona.fromId(userId)
    Row(modifier = modifier
        .clickable{ onHeaderClick(userId) }
        .padding(4.dp)
        .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(userPersona.avatarResource),
            contentDescription = null,
            modifier = modifier
//                .padding(4.dp)
                .size(44.dp)
                .clip(CircleShape)
        )
        Column {
            Text(
                text = userPersona.displayName,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Start,
                modifier = modifier.padding(start = 8.dp, top = 8.dp, bottom = 4.dp)
            )
            Text(
                text = userPersona.handle,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Start,
                modifier = modifier.padding(start = 8.dp)
            )
        }
    }
}