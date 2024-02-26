package ru.testapp.myapp_compose.auth

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Auth @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    private val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    private val idKey = "ID_KEY"
    private val tokenKey = "TOKEN_KEY"

    data class AuthState(
        val id: Long = 0,
        val token: String? = null
    )

    private val _authStateFlow: MutableStateFlow<AuthState>

    init {
        val token = prefs.getString(tokenKey, null)
        val id = prefs.getLong(idKey, 0L)

        if (id == 0L || token == null) {
            _authStateFlow = MutableStateFlow(AuthState())
            with(prefs.edit()) {
                clearAuth()
                apply()
            }
        } else {
            _authStateFlow = MutableStateFlow(AuthState(id, token))
        }
    }

    val authState: StateFlow<AuthState> = _authStateFlow.asStateFlow()

    @Synchronized
    fun setAuth(id: Long, token: String) {
        _authStateFlow.value = AuthState(id, token)
        with(prefs.edit()) {
            putLong(idKey, id)
            putString(tokenKey, token)
            apply()
        }
    }

    @Synchronized
    fun clearAuth() {
        _authStateFlow.value = AuthState()
        with(prefs.edit()) {
            clear()
            apply()
        }
    }

    companion object {
        @Volatile
        private var AUTH_INSTANCE: Auth? = null

        private fun buildAuth(context: Context): Auth = Auth(context)

        fun initInApp(context: Context): Auth = AUTH_INSTANCE ?: synchronized(this) {
            AUTH_INSTANCE ?: buildAuth(context).also { AUTH_INSTANCE = it }
        }

        fun getAuthInstance(): Auth = synchronized(this) {
            AUTH_INSTANCE ?: throw IllegalStateException("AppAuth is not initialized, you must call AppAuth.initializeApp(Context context) first.")
        }
    }
}