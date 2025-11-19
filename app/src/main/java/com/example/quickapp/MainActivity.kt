package com.example.quickapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.quickapp.domain.repo.PostRepository
import com.example.quickapp.model.Post
import com.example.quickapp.ui.theme.PostDetailViewModel
import com.example.quickapp.ui.theme.PostViewModel
import com.example.quickapp.ui.theme.QuickAppTheme
import com.example.quickapp.ui.theme.navigation.AppNavHost


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuickAppTheme {
                val navController = rememberNavController()
                val repository = PostRepository()

                AppNavHost(navController,repository)
                }
            }
        }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(viewModel: PostViewModel, onAddPostClick: () -> Unit, onPostClick: (Int) -> Unit, modifier: Modifier = Modifier) {
    val postList by viewModel.filteredPosts.collectAsState()
    val query by viewModel.query.collectAsState()
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
            items(items = postList) { post ->
                PostItem(post, onPostClick, modifier)
            }
        }
    }
}



@Composable
fun PostItem(post: Post, onPostClick: (Int) -> Unit ,  modifier: Modifier) {
    Column(modifier = modifier.clickable{
        println(post.id)
        onPostClick(post.id)
    }) {
        Text(text = post.title, style = MaterialTheme.typography.titleLarge)
        Text(text = post.body, style = MaterialTheme.typography.bodyMedium)
        HorizontalDivider(modifier = Modifier.padding(horizontal = 4.dp), thickness = 1.dp )
    }
}

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
                    IconButton(onClick = onCancel) { Icon(Icons.Default.ArrowBack, contentDescription = "cancel")
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(detailViewModel: PostDetailViewModel, postID: Int, onBack: () -> Unit, modifier: Modifier = Modifier) {
    val postState = detailViewModel.post.collectAsState()

    LaunchedEffect(postID) {
        detailViewModel.loadPost(postID)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text("PostDetails")},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
        )}
    ) { paddingValues ->
        if (postState == null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        val post = postState!!
        var longerBody = ""
         repeat(5) {longerBody += post.value?.body ?: ""}
        LazyColumn(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            item {
                PostHeader(
                    userName = "User ${post.value?.userID}",
                    userAvatar = R.drawable.avi,
                    title = post.value?.title ?: "Oops",
                    commentCount = 9,
                    onCommentsClick = {})
            }
            item {
                PostBody(body = longerBody)
            }
        }
    }

}

@Composable
fun PostHeader(
    userName: String,
    userAvatar: Int,
    title: String,
    commentCount: Int,
    onCommentsClick: () -> Unit,
    modifier: Modifier = Modifier) {
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically) {
        AvatarElement(
            name = userName,
            photo = userAvatar,
            modifier = Modifier.size(88.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))
        PostTitleContent(
            title = title,
            commentCount = commentCount,
            onCommentsClick = onCommentsClick,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun PostBody(body: String, modifier: Modifier = Modifier) {
    Text(
        text = body,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier.padding(16.dp)
    )
}

@Composable
fun AvatarElement(
    name: String,
    @DrawableRes photo: Int,
    modifier: Modifier = Modifier
)  {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(photo),
            modifier = Modifier
                .size(68.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Text(
            text = name,
            modifier = Modifier.paddingFromBaseline(top = 4.dp)
        )
    }
}

@Composable
fun PostTitleContent(title: String?, commentCount: Int, onCommentsClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(top = 28.dp, bottom = 20.dp, start = 10.dp, end = 10.dp)

    ) {
        Text(text = title ?: "", style = MaterialTheme.typography.titleMedium)
        Text(text = "$commentCount Comments",
            modifier = Modifier.clickable(
                enabled = true,
                onClick = onCommentsClick)
                .padding(vertical = 4.dp),

            style = MaterialTheme.typography.bodySmall)
    }
}

@Preview(showBackground = true)
@Composable
fun AvatarPreview(modifier: Modifier = Modifier) {
    AvatarElement(
        name = stringResource(R.string.name1),
        photo = R.drawable.avi,
        modifier = modifier.padding(8.dp)
    )
    
}

@Preview(showBackground = true)
@Composable
fun TitleContentPreview() {
    PostTitleContent(
        title = "Title1",
        commentCount = 5,
        onCommentsClick = {},
    )
}

