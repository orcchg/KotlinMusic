package com.orcchg.dev.maxa.ktmusic.data.injection.remote.music.artist

import com.orcchg.dev.maxa.ktmusic.data.injection.remote.CloudModule
import com.orcchg.dev.maxa.ktmusic.data.source.remote.music.artist.ServerArtistRestAdapter
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = arrayOf(CloudModule::class))
class ArtistCloudModule {

    @Provides @Singleton
    fun provideServerArtistRestAdapter(retrofit: Retrofit.Builder): ServerArtistRestAdapter {
        return retrofit.baseUrl(ServerArtistRestAdapter.ENDPOINT).build()
                .create(ServerArtistRestAdapter::class.java)
    }
}
