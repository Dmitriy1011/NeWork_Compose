package ru.testapp.myapp_compose.composables_post

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import kotlinx.coroutines.flow.Flow
import ru.testapp.myapp_compose.dto.Post

@Composable
fun PostsList(
    data: LazyPagingItems<Post>,
    onDelete: (Long) -> Unit,
    onClickPost: (String) -> Unit,
    onClickEditItem: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)


    ) {
        items(
            count = data.itemCount,
            key = data.itemKey { post -> post.id }
            ) { index ->
            val item = data[index]

            item?.let { post ->
                CardPost(
                    postId = post.id,
                    avatar = post.authorAvatar ?: "",
                    authorName = post.author,
                    publishedDate = post.published,
                    content = post.content,
                    imageRes = post.attachment?.url ?: "",
                    onLikeClick = {},
                    onShareClick = {},
                    onDeletePost = { onDelete(post.id) },
                    ownedByMe = post.ownedByMe,
                    onClickPost = { id -> onClickPost(id) },
                    onClickEdit = { string -> onClickEditItem(string)}
                )
            }
        }
    }
}
