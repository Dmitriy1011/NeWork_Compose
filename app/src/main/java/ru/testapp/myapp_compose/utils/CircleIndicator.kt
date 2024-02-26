package ru.testapp.myapp_compose.utils

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CircleIndicator(state: Boolean, modifier: Modifier = Modifier) {
    if(!state) {
        return
    }

    CircularProgressIndicator(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}