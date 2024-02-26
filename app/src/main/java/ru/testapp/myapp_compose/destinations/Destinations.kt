package ru.testapp.myapp_compose.destinations

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

interface Destinations {
    val route: String
    val icon: ImageVector
    val label: String
}

object PostsFeedDest : Destinations {
    override val route: String = "PostsFeed"
    override val icon: ImageVector = Icons.Filled.Home
    override val label: String = "Posts"
}

object PostCreateEditDest : Destinations {
    override val route: String = "PostCreateAndEdit"
    override val icon: ImageVector = Icons.Default.AddCircle
    override val label: String = "PostCreateAndEdit"
}

object EventsFeedDest : Destinations {
    override val route: String = "EventsFeed"
    override val icon: ImageVector = Icons.Filled.DateRange
    override val label: String = "Events"
}

object EventCreateEditDest : Destinations {
    override val route: String = "EventCreateEdit"
    override val icon: ImageVector = Icons.Filled.Call
    override val label: String = "EventCreateAndEdit"
}

object SignInDest : Destinations {
    override val route: String = "SignInDest"
    override val icon: ImageVector = Icons.Filled.AccountCircle
    override val label: String = "SignInDest"
}

object SignUpDest : Destinations {
    override val route: String = "SignUpDest"
    override val icon: ImageVector = Icons.Filled.AccountCircle
    override val label: String = "SignUpDest"
}

object SinglePostDest : Destinations {
    override val route: String = "SinglePost"
    override val icon: ImageVector = Icons.Default.Info
    override val label: String = "SinglePost"
}

val screens = listOf(
    PostsFeedDest,
    EventsFeedDest,

)
