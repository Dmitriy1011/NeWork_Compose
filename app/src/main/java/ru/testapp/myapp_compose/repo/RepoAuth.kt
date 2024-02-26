package ru.testapp.myapp_compose.repo

import java.io.File

interface RepoAuth {
    suspend fun verifyUser(login: String, password: String)
    suspend fun registerUser(login: String, password: String, name: String, media: File)
}