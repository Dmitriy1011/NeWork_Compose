package ru.testapp.myapp_compose.repo

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.testapp.myapp_compose.dto.Event

interface RepoEvents {
    val data: Flow<PagingData<Event>>
    suspend fun save(event: Event)
    suspend fun delete(id: Long)
    suspend fun like(id: Long): Event
    suspend fun unLike(id: Long): Event
    suspend fun saveEventWithAttachment(event: Event)
}