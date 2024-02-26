package ru.testapp.myapp_compose.composables_events

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.paging.compose.collectAsLazyPagingItems
import ru.testapp.myapp_compose.composables_post.PostsList
import ru.testapp.myapp_compose.destinations.PostsFeedDest
import ru.testapp.myapp_compose.destinations.screens
import ru.testapp.myapp_compose.viewmodels.AuthViewModel
import ru.testapp.myapp_compose.viewmodels.ViewModelEvents
import ru.testapp.myapp_compose.viewmodels.ViewModelPosts

@Composable
fun EventsFeedScreen(
    modifier: Modifier = Modifier,
    viewModelEvents: ViewModelEvents = hiltViewModel(),
) {
    val data = viewModelEvents.events
    EventsList(data)
}