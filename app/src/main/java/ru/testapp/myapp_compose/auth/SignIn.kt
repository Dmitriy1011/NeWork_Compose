package ru.testapp.myapp_compose.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import ru.testapp.myapp_compose.R
import ru.testapp.myapp_compose.destinations.PostsFeedDest
import ru.testapp.myapp_compose.destinations.SignUpDest
import ru.testapp.myapp_compose.navigation.navigateSingleTopTo
import ru.testapp.myapp_compose.utils.CircleIndicator
import ru.testapp.myapp_compose.viewmodels.AuthViewModel
import ru.testapp.myapp_compose.viewmodels.ViewModelSignInAndSignUp

@Composable
fun SignIn(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel(),
    signInViewModel: ViewModelSignInAndSignUp = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    val login = rememberSaveable {
        mutableStateOf("")
    }
    val password = rememberSaveable {
        mutableStateOf("")
    }

    val context = LocalContext.current

    val authData = authViewModel.data.collectAsStateWithLifecycle()
    val incorrectData = signInViewModel.wrongDataErrorState

    Surface(
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {
            Text(text = "Sign In", modifier = modifier.padding(bottom= 16.dp))

            OutlinedTextField(
                value = login.value,
                onValueChange = { login.value = it }
            )
            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                modifier = modifier.padding(top = 16.dp)
            )

            OutlinedButton(
                onClick = {

                    signInViewModel.verifyUser(login.value, password.value)

                    if (authData.value.token != null) {
                        navController.navigateSingleTopTo(PostsFeedDest.route)
                    }
                },
                modifier = modifier.padding(top = 16.dp)
            ) {
                Text(text = stringResource(R.string.enter))
            }

            TextButton(onClick = { navController.navigateSingleTopTo(SignUpDest.route) }) {
                Text(text = stringResource(R.string.not_registered_yet_register))
            }

            incorrectData.value?.loading?.let { CircleIndicator(state = it) }

            incorrectData.value?.let {
                when {
                    it.error -> Toast.makeText(
                        context,
                        "Incorrect user data",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}