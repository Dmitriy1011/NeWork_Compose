package ru.testapp.myapp_compose.viewmodels

import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.testapp.myapp_compose.auth.Auth
import ru.testapp.myapp_compose.dto.Post
import ru.testapp.myapp_compose.repo.RepoPosts
import ru.testapp.myapp_compose.states.StatePosts
import javax.inject.Inject

private val emptyPost = Post(
    id = 0,
    authorId = 0,
    author = "",
    authorAvatar = null,
    authorJob = null,
    content = "",
    published = "",
    coords = null,
    link = null,
    likeOwnerIds = null,
    mentionIds = null,
    mentionedMe = false,
    likedByMe = false,
    attachment = null,
    ownedByMe = false,
    users = null
)

@HiltViewModel
class ViewModelPosts @Inject constructor(
    private val repo: RepoPosts,
    auth: Auth
) : ViewModel() {
    private val cached = repo.data.cachedIn(viewModelScope)

    @OptIn(ExperimentalCoroutinesApi::class)
    val data = auth.authState.flatMapLatest {(myId, _) ->
        cached.map { pagingData ->
            pagingData.map { post ->
                if (post !is Post) post else post.copy(ownedByMe = post.authorId.toLong() == myId)
            }
        }
    }

    val postId = MutableStateFlow(0L)
    val postContent = MutableStateFlow("Введите текст поста")

    private val _createdPostState = MutableStateFlow(emptyPost)

    private val _postsState = MutableStateFlow(StatePosts())
    val postsState: StateFlow<StatePosts> = _postsState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun savePost() {
        _createdPostState.value.let {
            viewModelScope.launch {
                _postsState.update { it.copy(loading = true) }
                try {
                    repo.save(it)
                } catch (e: Exception) {
                    _postsState.update { it.copy(error = true) }
                }

            }
        }
        _createdPostState.value = emptyPost
    }

    fun changeContent(value: String) {
        val text = value.trim()
        if (_createdPostState.value.content == text) {
            return
        } else {
            _createdPostState.update { post ->
                post.copy(content = text)
            }
        }
    }

    fun deletePost(id: Long) {
        viewModelScope.launch {
            try {
                repo.delete(id)
            } catch (e: Exception) {
                _postsState.update { it.copy(error = true) }
            }
        }
    }
}