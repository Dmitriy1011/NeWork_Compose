package ru.testapp.myapp_compose.navigation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.launch
import ru.testapp.myapp_compose.auth.SignIn
import ru.testapp.myapp_compose.auth.SignUp
import ru.testapp.myapp_compose.composables_events.EventsFeedScreen
import ru.testapp.myapp_compose.composables_post.PostCreateEdit
import ru.testapp.myapp_compose.composables_post.PostsFeedScreen
import ru.testapp.myapp_compose.composables_post.SinglePostScreen
import ru.testapp.myapp_compose.destinations.Destinations
import ru.testapp.myapp_compose.destinations.EventCreateEditDest
import ru.testapp.myapp_compose.destinations.EventsFeedDest
import ru.testapp.myapp_compose.destinations.PostCreateEditDest
import ru.testapp.myapp_compose.destinations.PostsFeedDest
import ru.testapp.myapp_compose.destinations.SignInDest
import ru.testapp.myapp_compose.destinations.SignUpDest
import ru.testapp.myapp_compose.destinations.SinglePostDest
import ru.testapp.myapp_compose.destinations.screens
import ru.testapp.myapp_compose.viewmodels.AuthViewModel
import ru.testapp.myapp_compose.viewmodels.ViewModelPosts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreensNavigation(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel(),
    postViewModel: ViewModelPosts = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentScreen = screens.find { it.route == currentDestination?.route } ?: PostsFeedDest

    val authState = authViewModel.isAuthenticated
    val postIdArg = postViewModel.postId.collectAsStateWithLifecycle().value
    val postContent = postViewModel.postContent.collectAsStateWithLifecycle().value

    val postsList = postViewModel.data.collectAsLazyPagingItems().itemSnapshotList.items

    val scope = rememberCoroutineScope()

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            TopAppBar(
                modifier = modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background),
                title = {
                    when (currentScreen) {
                        is PostsFeedDest -> {
                            Text(text = "Posts")
                        }

                        is PostCreateEditDest -> {
                            Text(text = "Create or edit post")
                        }

                        is EventsFeedDest -> {
                            Text(text = "Events")
                        }
                    }
                },
                actions = {
                    when (currentScreen) {
                        is PostsFeedDest -> {
                            IconButton(onClick = {}) {
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = null
                                )
                            }
                        }

                        is EventsFeedDest -> {
                            IconButton(onClick = {}) {
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    when (currentScreen) {
                        is PostsFeedDest -> {
                            Log.d("ID", "$authState")
                            if (authState) {
                                navController.navigateToEditWithArgs("Введите свой текст здесь")
                            } else {
                                scope.launch {
                                    val result = snackBarHostState
                                        .showSnackbar(
                                            message = "Вы не авторизированы",
                                            actionLabel = "Авторизироваться",
                                            duration = SnackbarDuration.Long
                                        )
                                    when (result) {
                                        SnackbarResult.ActionPerformed -> navController.navigateSingleTopTo(
                                            SignUpDest.route
                                        )

                                        SnackbarResult.Dismissed -> return@launch
                                    }
                                }
                            }
                        }

                        is EventsFeedDest -> {
                            navController.navigateSingleTopTo(EventCreateEditDest.route)
                        }
                    }
                },
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null
                )
            }
        },
        bottomBar = {
            BottomBar(
                screens = screens,
                onTabSelected = { newScreen ->
                    navController.navigateSingleTopTo(newScreen.route)
                },
                currentScreen = currentScreen
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = PostsFeedDest.route,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = PostsFeedDest.route) {
                PostsFeedScreen(
                    onPostClick = { postId ->
                        navController.navigateToDetails(postId)
                    },
                    onItemEditClick = { content ->
                        navController.navigateToEditWithArgs(content)
                    },
                )
            }
            composable(route = EventsFeedDest.route) {
                EventsFeedScreen()
            }

            composable(
                route = PostCreateEditDest.route + "/{${postContent}}",
                arguments = listOf(
                    navArgument(postContent) {
                        type = NavType.StringType
                    }
                )
            ) { entry ->
                val content = entry.arguments?.getString(postContent)
                content?.let { text ->
                    PostCreateEdit(content = text, navController = navController)
                }
            }

            composable(route = SignInDest.route) {
                SignIn(navController)
            }

            composable(route = SignUpDest.route) {
                SignUp(navController)
            }

            composable(
                route = "${SinglePostDest.route}/{${postIdArg}}",
                arguments = listOf(
                    navArgument("$postIdArg") {
                        type = NavType.StringType
                    }
                )
            ) { navBackStackEntry ->
                val postId = navBackStackEntry.arguments?.getString("$postIdArg")
                postId?.let {
                    SinglePostScreen(postsList, it)
                }
            }
        }
    }
}


@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    screens: List<Destinations>,
    onTabSelected: (Destinations) -> Unit,
    currentScreen: Destinations
) {
    NavigationBar(
        modifier = modifier.fillMaxWidth()
    ) {
        screens.forEach { screen ->
            NavigationBarItem(
                selected = currentScreen == screen,
                onClick = { onTabSelected(screen) },
                icon = {
                    Icon(imageVector = screen.icon, contentDescription = null)
                },
                label = {
                    Text(screen.label)
                }
            )
        }
    }
}

fun NavController.navigateSingleTopTo(route: String) {
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavHostController.navigateToDetails(postId: String) {
    this.navigateSingleTopTo("${SinglePostDest.route}/${postId}")
}


fun NavHostController.navigateToEditWithArgs(content: String) {
    this.navigateSingleTopTo("${PostCreateEditDest.route}/$content")
}