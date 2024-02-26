package ru.testapp.myapp_compose.composables_post

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ru.testapp.myapp_compose.destinations.PostsFeedDest
import ru.testapp.myapp_compose.navigation.navigateSingleTopTo
import ru.testapp.myapp_compose.viewmodels.ViewModelPosts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCreateEdit(
    content: String,
    navController: NavHostController,
    viewModelPosts: ViewModelPosts = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    var postText by rememberSaveable {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Create or edit") },
                actions = {
                    IconButton(
                        onClick = {
                            viewModelPosts.changeContent(postText)
                            viewModelPosts.savePost()
                            navController.navigateSingleTopTo(PostsFeedDest.route)
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null)
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateSingleTopTo(PostsFeedDest.route)
                        }
                    )
                    {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        content = { innerPadding ->
            TextField(
                value = content,
                onValueChange = { postText = it },
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        }
    )
}
