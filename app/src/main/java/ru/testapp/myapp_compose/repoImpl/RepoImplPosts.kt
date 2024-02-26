package ru.testapp.myapp_compose.repoImpl

import android.accounts.NetworkErrorException
import androidx.paging.Pager
import androidx.paging.PagingConfig
import ru.testapp.myapp_compose.api.ApiServicePosts
import ru.testapp.myapp_compose.dto.Post
import ru.testapp.myapp_compose.paging.PostPagingSource
import ru.testapp.myapp_compose.repo.RepoPosts
import java.io.IOException
import javax.inject.Inject

class RepoImplPosts @Inject constructor(
    private val apiService: ApiServicePosts
) : RepoPosts {
    override val data = Pager(
        config = PagingConfig(pageSize = 5, enablePlaceholders = false),
        pagingSourceFactory = { PostPagingSource(apiService) }
    ).flow

    override suspend fun save(post: Post) {
        try {
            apiService.save(post)
        } catch (e: IOException) {
            throw NetworkErrorException(e)
        }
    }

    override suspend fun delete(id: Long) {
        try {
            apiService.delete(id)
        } catch (e: IOException) {
            throw NetworkErrorException(e)
        }
    }

    override suspend fun like(id: Long): Post {
        try {
            val response = apiService.like(id)
            if (!response.isSuccessful) {
                throw RuntimeException(response.message())
            }

            return response.body() ?: throw RuntimeException("body is null")
        } catch (e: IOException) {
            throw NetworkErrorException(e)
        }
    }

    override suspend fun unLike(id: Long): Post {
        try {
            val response = apiService.unlike(id)
            if (!response.isSuccessful) {
                throw RuntimeException(response.message())
            }

            return response.body() ?: throw RuntimeException("body is null")
        } catch (e: IOException) {
            throw NetworkErrorException(e)
        }
    }

    override suspend fun savePostWithAttachment(post: Post) {
        TODO("Not yet implemented")
    }
}