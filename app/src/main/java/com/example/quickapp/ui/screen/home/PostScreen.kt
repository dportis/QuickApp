package com.example.quickapp.ui.screen.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.quickapp.ui.components.PostItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(viewModel: PostViewModel, onAddPostClick: () -> Unit, onPostClick: (Int) -> Unit, modifier: Modifier = Modifier) {
    val postList by viewModel.filteredPosts.collectAsState()
    val query by viewModel.query.collectAsState()
    val shuffledPostList = remember(postList) {
        postList.shuffled()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    OutlinedTextField(
                        shape = RoundedCornerShape(
                            topStart = 4.dp,
                            bottomStart = 4.dp,
                            topEnd = 4.dp,
                            bottomEnd = 4.dp),
                        value = query,
                        onValueChange = {viewModel.updateQuery(it)},
                        placeholder = {Text("Search here")},
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp, bottom = 8.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddPostClick) {
                Icon(Icons.Default.Add, contentDescription = "add post")
            }
        }
    ) { innerPadding ->
        LazyColumn(modifier = modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(16.dp)) {
            items(items = shuffledPostList) { post ->
                PostItem(post, onPostClick, modifier)
            }
        }
    }
}
