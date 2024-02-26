package ru.testapp.myapp_compose.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.testapp.myapp_compose.dto.MediaForRegistration
import ru.testapp.myapp_compose.repo.RepoAuth
import ru.testapp.myapp_compose.states.AuthState
import ru.testapp.myapp_compose.states.StateRegistration
import ru.testapp.myapp_compose.utils.SingleLiveEvent
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ViewModelSignInAndSignUp @Inject constructor(
    private val repo: RepoAuth
) : ViewModel() {

    private val _mediaForRegistrationState = MutableStateFlow<MediaForRegistration?>(null)
    val mediaForRegistration: StateFlow<MediaForRegistration?>
        get() = _mediaForRegistrationState.asStateFlow()

    private val _wrongDataErrorState = SingleLiveEvent<AuthState>()
    val wrongDataErrorState: SingleLiveEvent<AuthState> = _wrongDataErrorState

    private val _registrationState = SingleLiveEvent<StateRegistration>()
    val registrationState: SingleLiveEvent<StateRegistration> = _registrationState

    fun verifyUser(login: String, password: String) {
        viewModelScope.launch {
            _wrongDataErrorState.value = AuthState(loading = true)
            try {
                repo.verifyUser(login, password)
            } catch (e: Exception) {
                _wrongDataErrorState.value = AuthState(error = true)
            }
        }
    }

    fun register(login: String, password: String, name: String, media: File) {
        viewModelScope.launch {
            _registrationState.value = StateRegistration(loading = true)
            try {
                repo.registerUser(login, password, name, media)
            } catch (e: Exception) {
                _registrationState.value = StateRegistration(error = true)
            }
        }
    }
}