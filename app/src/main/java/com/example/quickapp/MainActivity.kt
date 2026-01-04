package com.example.quickapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.quickapp.domain.repo.PostRepository
import com.example.quickapp.ui.components.AvatarElement
import com.example.quickapp.ui.components.PostTitleContent
import com.example.quickapp.ui.navigation.AppNavHost
import com.example.quickapp.ui.theme.QuickAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var postRepository: PostRepository

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuickAppTheme {
                val navController = rememberNavController()

                AppNavHost(navController,postRepository)
                }
            }
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

