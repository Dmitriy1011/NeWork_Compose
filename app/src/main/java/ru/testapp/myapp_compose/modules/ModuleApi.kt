package ru.testapp.myapp_compose.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import ru.testapp.myapp_compose.BuildConfig.BASE_URL
import ru.testapp.myapp_compose.api.ApiAuth
import ru.testapp.myapp_compose.api.ApiServicePosts
import ru.testapp.myapp_compose.api.ApiServicesEvents
import ru.testapp.myapp_compose.auth.Auth
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleApi {


    @Singleton
    @Provides
    fun provideLogging(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun provideOkhttp(
        auth: Auth
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor { chain ->
            auth.authState.value.token?.let { token ->
                val newRequest = chain.request().newBuilder().addHeader(
                    "Authorization",
                    token
                ).build()
                return@addInterceptor chain.proceed(newRequest)
            }
            chain.proceed(chain.request())
        }
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit =
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).client(client)
            .baseUrl(BASE_URL).build()


    @Singleton
    @Provides
    fun provideApiServicePosts(
        retrofit: Retrofit
    ): ApiServicePosts = retrofit.create<ApiServicePosts>()

    @Singleton
    @Provides
    fun provideApiServiceEvents(
        retrofit: Retrofit
    ): ApiServicesEvents = retrofit.create<ApiServicesEvents>()

    @Singleton
    @Provides
    fun provideApiAuth(
        retrofit: Retrofit
    ): ApiAuth = retrofit.create<ApiAuth>()
}