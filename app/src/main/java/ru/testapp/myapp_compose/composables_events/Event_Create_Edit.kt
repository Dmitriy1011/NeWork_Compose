package ru.testapp.myapp_compose.composables_events

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ru.testapp.myapp_compose.viewmodels.ViewModelEvents

@Composable
fun EventCreateEdit(
    viewModelEvents: ViewModelEvents = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    var eventText by rememberSaveable {
        mutableStateOf("")
    }
    TextField(
        value = eventText,
        onValueChange = { eventText = it },
        modifier = modifier
            .fillMaxSize()
    )
}