package ru.testapp.myapp_compose.api

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import ru.testapp.myapp_compose.dto.Event
import ru.testapp.myapp_compose.dto.Media
import ru.testapp.myapp_compose.dto.Post

interface ApiServicesEvents {

    @GET("/api/events/latest/")
    suspend fun getLatestEvents(@Query("count") count: Int): Response<List<Event>>

    @GET("/api/events/{event_id}/before/")
    suspend fun getBefore(
        @Path("event_id") int: Int,
        @Query("count") count: Int
    ): Response<List<Event>>

    @POST("/api/events/")
    suspend fun save(@Body event: Event): Response<Event>

    @POST("/api/events/{event_id}/")
    suspend fun like(@Path("event_id") id: Long): Response<Event>

    @POST("/api/events/{event_id}/")
    suspend fun unlike(@Path("event_id") id: Long): Response<Event>

    @DELETE("/api/events/{event_id}/")
    suspend fun delete(@Path("event_id") id: Long): Response<Unit>

    @Multipart
    @POST("/api/media")
    suspend fun uploadMedia(@Part file: MultipartBody.Part): Response<Media>
}