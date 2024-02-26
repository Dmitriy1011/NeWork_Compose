package ru.testapp.myapp_compose.states

data class StateEvents (
    val refreshing: Boolean = false,
    val loading: Boolean = false,
    val error: Boolean = false
)