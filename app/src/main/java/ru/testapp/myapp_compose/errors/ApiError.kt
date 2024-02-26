package ru.testapp.myapp_compose.errors

class ApiError(
    code: Int,
    message: String
) : RuntimeException(message) {
}