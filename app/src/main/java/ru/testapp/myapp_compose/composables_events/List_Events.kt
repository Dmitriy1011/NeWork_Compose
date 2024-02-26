package ru.testapp.myapp_compose.composables_events

import CardEvent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.Flow
import ru.testapp.myapp_compose.dto.Event

@Composable
fun EventsList(
    data: Flow<PagingData<Event>>,
    modifier: Modifier = Modifier
) {
    val lazyPagingItems = data.collectAsLazyPagingItems()

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(count = lazyPagingItems.itemCount) { index ->
            val item = lazyPagingItems[index]

            item?.let { event ->
                CardEvent(
                    avatar = event.authorAvatar ?: "",
                    authorName = event.author,
                    publishedDate = event.published,
                    eventType = event.type,
                    dateTime = event.dateTime ?: "",
                    imageRes = event.attachment?.url ?: "",
                    content = event.content,
                    ownedByMe = event.ownedByMe
                )
            }
        }
    }
}
