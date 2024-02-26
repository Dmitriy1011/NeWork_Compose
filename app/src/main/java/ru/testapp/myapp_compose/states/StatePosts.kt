package ru.testapp.myapp_compose.states

data class StatePosts(
    val refreshing: Boolean = false,
    val loading: Boolean = false,
    val error: Boolean = false
)