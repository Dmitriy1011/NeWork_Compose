package ru.testapp.myapp_compose.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.testapp.myapp_compose.auth.Auth
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(): ViewModel() {
    val data = Auth.getAuthInstance().authState
    val isAuthenticated: Boolean
        get() =  data.value.id != 0L
}