package ru.testapp.myapp_compose.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.testapp.myapp_compose.api.ApiServicesEvents
import ru.testapp.myapp_compose.dto.Event
import ru.testapp.myapp_compose.errors.ApiError
import java.io.IOException

class EventsPagingSource(
    private val apiServiceEvents: ApiServicesEvents
) : PagingSource<Long, Event>() {

    override fun getRefreshKey(state: PagingState<Long, Event>): Long? = null

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, Event> {
        try {
            val response = when (params) {
                is LoadParams.Refresh -> {
                    apiServiceEvents.getLatestEvents(params.loadSize)
                }

                is LoadParams.Prepend -> return LoadResult.Page(
                    data = emptyList(),
                    nextKey = null,
                    prevKey = params.key
                )

                is LoadParams.Append -> return LoadResult.Page(
                    data = emptyList(),
                    nextKey = params.key,
                    prevKey = null
                )
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