package com.orcchg.dev.maxa.ktmusic.data.injection.remote.music.genre

import com.orcchg.dev.maxa.ktmusic.data.injection.remote.CloudComponent
import com.orcchg.dev.maxa.ktmusic.data.source.remote.music.genre.ServerGenreRestAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(GenreCloudModule::class))
interface GenreCloudComponent : CloudComponent {

    fun restAdapter(): ServerGenreRestAdapter
}
