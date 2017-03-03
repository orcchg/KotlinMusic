package com.orcchg.dev.maxa.ktmusic.app.injection.application

import android.content.Context
import com.orcchg.dev.maxa.ktmusic.data.injection.repository.music.artist.ArtistRepositoryModule
import com.orcchg.dev.maxa.ktmusic.data.injection.repository.music.genre.GenreRepositoryModule
import com.orcchg.dev.maxa.ktmusic.domain.repository.music.ArtistRepository
import com.orcchg.dev.maxa.ktmusic.domain.repository.music.GenreRepository
import com.orcchg.dev.maxa.ktmusic.library.domain.executor.PostExecuteScheduler
import com.orcchg.dev.maxa.ktmusic.library.domain.executor.ThreadExecutor
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, ArtistRepositoryModule::class, GenreRepositoryModule::class))
interface ApplicationComponent {

    fun context(): Context
    fun threadExecutor(): ThreadExecutor
    fun postExecuteScheduler(): PostExecuteScheduler

    fun artistRepository(): ArtistRepository
    fun genreRepository(): GenreRepository
}
