package ru.testapp.myapp_compose.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.testapp.myapp_compose.api.ApiServicePosts
import ru.testapp.myapp_compose.dto.Post
import ru.testapp.myapp_compose.errors.ApiError
import java.io.IOException

class PostPagingSource(
    private val apiServicePosts: ApiServicePosts
) : PagingSource<Long, Post>() {

    override fun getRefreshKey(state: PagingState<Long, Post>): Long? = null

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, Post> {
        try {
            val response = when (params) {
                is LoadParams.Refresh -> {
                    apiServicePosts.getLatestPosts(params.loadSize)
                }

                is LoadParams.Prepend -> return LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = params.key
                )

                is LoadParams.Append ->  {
                    apiServicePosts.getBefore(params.key, params.loadSize)
                }
            }

            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

            val data = response.body().orEmpty()
            return LoadResult.Page(data = data, prevKey = null, nextKey = null)

        } catch (e: IOException) {
            return LoadResult.Error(e)
        }
    }
}