package ru.testapp.myapp_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.testapp.myapp_compose.navigation.MainScreensNavigation
import ru.testapp.myapp_compose.ui.theme.AppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    tonalElevation = 5.dp
                ) {
                    SocialMediaApp()
                }
            }
        }
    }
}

@Composable
fun SocialMediaApp() {
    MaterialTheme {
        val navController = rememberNavController()
        MainScreensNavigation(navController = navController)
    }
}
