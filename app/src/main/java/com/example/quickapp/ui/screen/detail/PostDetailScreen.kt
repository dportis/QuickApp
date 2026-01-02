package com.example.quickapp.ui.screen.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.example.quickapp.R
import com.example.quickapp.model.UserPersona
import com.example.quickapp.ui.components.CommentsSheetContent
import com.example.quickapp.ui.components.PostBody
import com.example.quickapp.ui.components.PostDetailHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(
    detailViewModel: PostDetailViewModel,
    postId: Int,
    onBack: () -> Unit,
    onPostsClicked: (Int) -> Unit) {
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
        var persona = ""
        if (postState.value?.userId != null){
            persona = UserPersona.fromId(postState.value!!.userId).displayName
        }
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()) {
            item {
                PostDetailHeader(
                    userName = persona,
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