package ru.testapp.myapp_compose.composables_post

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.testapp.myapp_compose.viewmodels.AuthViewModel
import ru.testapp.myapp_compose.viewmodels.ViewModelPosts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsFeedScreen(
    onPostClick: (String) -> Unit,
    onItemEditClick: (String) -> Unit,
    postViewModel: ViewModelPosts = hiltViewModel(),
) {

    val data = postViewModel.data.collectAsLazyPagingItems()
    val refreshState =
        rememberSwipeRefreshState(isRefreshing = data.loadState.refresh.endOfPaginationReached)

    SwipeRefresh(
        state = refreshState,
        onRefresh = { data.refresh() }
    ) {
        PostsList(
            data,
            { postId -> postViewModel.deletePost(postId) },
            { postId -> onPostClick(postId) },
            { content -> onItemEditClick(content) }
        )
    }
}


