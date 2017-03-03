package com.orcchg.dev.maxa.ktmusic.data.injection.remote.music.artist

import com.orcchg.dev.maxa.ktmusic.data.injection.remote.CloudComponent
import com.orcchg.dev.maxa.ktmusic.data.source.remote.music.artist.ServerArtistRestAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ArtistCloudModule::class))
interface ArtistCloudComponent : CloudComponent {

    fun restAdapter(): ServerArtistRestAdapter
}
