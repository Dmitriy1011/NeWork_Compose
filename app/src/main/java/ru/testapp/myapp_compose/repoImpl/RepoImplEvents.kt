package ru.testapp.myapp_compose.repoImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.testapp.myapp_compose.api.ApiServicesEvents
import ru.testapp.myapp_compose.dto.Event
import ru.testapp.myapp_compose.paging.EventsPagingSource
import ru.testapp.myapp_compose.repo.RepoEvents
import javax.inject.Inject


class RepoImplEvents @Inject constructor(
    private val apiServiceEvents: ApiServicesEvents
) : RepoEvents {
    override val data: Flow<PagingData<Event>> = Pager(
        config = PagingConfig(pageSize = 5, enablePlaceholders = false),
        pagingSourceFactory = { EventsPagingSource(apiServiceEvents) }
    ).flow

    override suspend fun save(event: Event) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun like(id: Long): Event {
        TODO("Not yet implemented")
    }

    override suspend fun unLike(id: Long): Event {
        TODO("Not yet implemented")
    }

    override suspend fun saveEventWithAttachment(event: Event) {
        TODO("Not yet implemented")
    }
}