package ru.testapp.myapp_compose.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import ru.testapp.myapp_compose.dto.Token

interface ApiAuth {

    @Multipart
    @POST("/api/users/registration/")
    suspend fun registerUser(
        @Part("login") login: MultipartBody.Part,
        @Part("password") password: MultipartBody.Part,
        @Part("name") name: MultipartBody.Part,
        @Part media: MultipartBody.Part
    ): Response<Token>

    @FormUrlEncoded
    @POST("/api/users/authentication/")
    suspend fun confirmUser(
        @Field("login") login: String,
        @Field("password") password: String
    ): Response<Token>
}