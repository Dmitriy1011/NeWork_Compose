package ru.testapp.myapp_compose.repoImpl

import android.accounts.NetworkErrorException
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import ru.testapp.myapp_compose.api.ApiAuth
import ru.testapp.myapp_compose.auth.Auth
import ru.testapp.myapp_compose.repo.RepoAuth
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoImplAuth @Inject constructor(
    private val api: ApiAuth
) : RepoAuth {

    @Inject
    lateinit var auth: Auth

    override suspend fun verifyUser(login: String, password: String) {
        try {
            val response = api.confirmUser(login, password)

            if(!response.isSuccessful) {
                throw RuntimeException(response.message())
            }

            val result = response.body() ?: throw RuntimeException("body is null")
            auth.setAuth(result.id, result.token)
        } catch (e: Exception) {
            throw NetworkErrorException(e)
        }
    }

    override suspend fun registerUser(login: String, password: String, name: String, media: File) {
        try {
            val response = api.registerUser(
                MultipartBody.Part.createFormData("login", login),
                MultipartBody.Part.createFormData("password", password),
                MultipartBody.Part.createFormData("name", name),
                MultipartBody.Part.createFormData("file", media.name, media.asRequestBody())
            )

            if (!response.isSuccessful) {
                throw RuntimeException(response.message())
            }

            val result = response.body() ?: throw RuntimeException("body is null")
            auth.setAuth(result.id, result.token)
        } catch (e: Exception) {
            throw NetworkErrorException(e)
        }
    }
}