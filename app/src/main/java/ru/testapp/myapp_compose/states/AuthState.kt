package ru.testapp.myapp_compose.states

data class AuthState(
    var error: Boolean = false,
    var loading: Boolean = false
)
