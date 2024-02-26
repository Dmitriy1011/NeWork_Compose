package ru.testapp.myapp_compose.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.testapp.myapp_compose.dto.Event
import ru.testapp.myapp_compose.repo.RepoEvents
import ru.testapp.myapp_compose.states.StateEvents
import javax.inject.Inject

private val emptyEvent = Event(
    id = 0,
    authorId = 0,
    author = "",
    authorAvatar = null,
    authorJob = null,
    content = "",
    dateTime = "",
    published = "",
    coords = null,
    type = "",
    likeOwnerIds = null,
    likedByMe = false,
    speakerIds = null,
    participantsIds = null,
    participatedByMe = false,
    attachment = null,
    link = null,
    ownedByMe = false,
    users = null
)

@HiltViewModel
class ViewModelEvents @Inject constructor(
    private val repo: RepoEvents
) : ViewModel() {
    private val cached = repo.data.cachedIn(viewModelScope)
    val events = cached.flowOn(Dispatchers.Default)

    private val _createdEventState = MutableStateFlow(emptyEvent)

    private val _eventState = MutableStateFlow(StateEvents())

    fun save() {
        _createdEventState.value.let {
            viewModelScope.launch {
                _eventState.update { it.copy(loading = true) }
                try {
                    repo.save(it)
                } catch (e: Exception) {
                    _eventState.update { it.copy(error = true) }
                }
            }
        }
        _createdEventState.value = emptyEvent
    }

    fun changeContent(value: String) {
        val text = value.trim()
        if (_createdEventState.value.content == text) {
            return
        } else {
            _createdEventState.update {
                it.copy(content = text)
            }
        }
    }
}