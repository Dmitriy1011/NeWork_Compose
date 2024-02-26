package ru.testapp.myapp_compose.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.testapp.myapp_compose.repo.RepoEvents
import ru.testapp.myapp_compose.repo.RepoPosts
import ru.testapp.myapp_compose.repo.RepoAuth
import ru.testapp.myapp_compose.repoImpl.RepoImplEvents
import ru.testapp.myapp_compose.repoImpl.RepoImplPosts
import ru.testapp.myapp_compose.repoImpl.RepoImplAuth
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
interface ModuleRepo {
    @Singleton
    @Binds
    fun provideRepo(repo: RepoImplPosts): RepoPosts

    @Singleton
    @Binds
    fun provideRepoEvents(repo: RepoImplEvents): RepoEvents

    @Singleton
    @Binds
    fun provideRepoAuth(repo: RepoImplAuth): RepoAuth

}