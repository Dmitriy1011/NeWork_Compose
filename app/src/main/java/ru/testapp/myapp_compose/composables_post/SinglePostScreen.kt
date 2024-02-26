package ru.testapp.myapp_compose.composables_post

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.testapp.myapp_compose.dto.Post
import ru.testapp.myapp_compose.viewmodels.ViewModelPosts

@Composable
fun SinglePostScreen(
    postsList: List<Post>,
    postId: String,
    viewModelPosts: ViewModelPosts = hiltViewModel(),
) {
    postsList.find { it.id == postId.toLong() }?.let { post ->
        post.authorAvatar?.let { avatar ->
            post.attachment?.url?.let { imgRes ->
                Surface(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                    CardPost(
                        postId = post.id,
                        avatar = avatar,
                        authorName = post.author,
                        publishedDate = post.published,
                        content = post.content,
                        imageRes = imgRes,
                        onLikeClick = {},
                        onShareClick = {},
                        onDeletePost = { viewModelPosts.deletePost(post.id) },
                        ownedByMe = post.ownedByMe,
                        onClickPost = {},
                        onClickEdit = {}
                    )
                }
            }
        }
    }
}