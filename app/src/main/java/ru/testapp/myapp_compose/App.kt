package ru.testapp.myapp_compose

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import ru.testapp.myapp_compose.auth.Auth

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        Auth.initInApp(this)
    }
}