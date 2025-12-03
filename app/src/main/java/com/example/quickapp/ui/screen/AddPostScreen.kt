package com.example.quickapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.quickapp.ui.screen.home.PostViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPostScreen(viewModel: PostViewModel,onPostCreated :(String, String) -> Unit, onCancel: () -> Unit, modifier: Modifier = Modifier) {
    val title by viewModel.title.collectAsState()
    val body by viewModel.body.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text ("Add Title") },
                navigationIcon = {
                    IconButton(onClick = onCancel) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "cancel")
                    }
                }
            )
        }) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)) {
            OutlinedTextField(
                value = title,
                onValueChange = {viewModel.updateTitle(it)},
                label = {Text("Title")},
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(8.dp))
            OutlinedTextField(
                value = body,
                onValueChange = {viewModel.updateBody(it)},
                label = {Text("Body")},
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(16.dp))
            Button(onClick = {
                viewModel.createPost(title,body)
                onPostCreated(title,body) },
                modifier = Modifier.align(Alignment.End)) {
                Text("Save")
            }
        }
    }
}
