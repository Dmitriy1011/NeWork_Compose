package ru.testapp.myapp_compose.dto

import javax.annotation.Nullable

@Nullable
data class User(
    val id: Long,
    val login: String,
    val name: String,
    val avatar: String?
)
