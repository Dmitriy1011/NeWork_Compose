package ru.testapp.myapp_compose.repo

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.testapp.myapp_compose.dto.Post

interface RepoPosts {
    val data: Flow<PagingData<Post>>
    suspend fun save(post: Post)
    suspend fun delete(id: Long)
    suspend fun like(id: Long): Post
    suspend fun unLike(id: Long): Post
    suspend fun savePostWithAttachment(post: Post)
}