package com.example.quickapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.compose.rememberNavController
import com.example.quickapp.domain.repo.PostRepository
import com.example.quickapp.model.Comments
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(
    detailViewModel: PostDetailViewModel,
    postId: Int,
    onBack: () -> Unit,
    onPostsClicked: (Int) -> Unit,
    modifier: Modifier = Modifier) {
    val postState = detailViewModel.post.collectAsState()
    val commentState = detailViewModel.comments.collectAsState()
    val userPostsState = detailViewModel.posts.collectAsState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val collapseFraction = scrollBehavior.state.collapsedFraction

    LaunchedEffect(postId) {
        detailViewModel.loadPost(postId)
        detailViewModel.loadPostComments(postId)
    }

    LaunchedEffect(postState.value) {
        val loadedPost = postState.value
        if (loadedPost != null) {
            detailViewModel.loadUserPosts(loadedPost.userId)
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {Text("PostDetails")},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                scrollBehavior = scrollBehavior
        )}
    ) { paddingValues ->
        if (postState == null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        var longerBody = ""
         repeat(5) {longerBody += postState.value?.body ?: ""}
        println("UserID: ${postState.value?.userId}")
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()) {
            item {
                PostHeader(
                    userName = "User ${postState.value?.userId} ",
                    userAvatar = R.drawable.avi,
                    title = postState.value?.title ?: "Oops",
                    commentCount = commentState.value.size,
                    onCommentsClick = { showBottomSheet = true},
                    userPosts = userPostsState.value,
                    collapseFraction = collapseFraction,
                    onPostsClick = {
                        val currentUserId = postState.value?.userId ?: 0
                        onPostsClicked(currentUserId)
                    })
            }
            item {
                PostBody(body = longerBody)
            }
        }
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false},
                sheetState = sheetState
            ) { CommentsSheetContent(comments = commentState.value,onClose = {}) }
        }
    }

}

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
            .padding(16.dp),
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
                    .padding(start = 12.dp),
                textAlign = TextAlign.Center
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

@Composable
fun CommentsSheetContent(
    comments: List<Comments>,
    onClose : () -> Unit,
    modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .heightIn(min = 200.dp)
    ) {
        Text(
            text = "Comments",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        HorizontalDivider()

        LazyColumn {
            items(comments.size) { index ->
                Text(
                    text = comments[index].body
                )
                HorizontalDivider(thickness = 1.dp)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onClose,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Close")
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

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

