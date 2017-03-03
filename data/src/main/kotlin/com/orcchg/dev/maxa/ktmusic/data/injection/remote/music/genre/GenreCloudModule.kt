package com.orcchg.dev.maxa.ktmusic.data.injection.remote.music.genre

import com.orcchg.dev.maxa.ktmusic.data.injection.remote.CloudModule
import com.orcchg.dev.maxa.ktmusic.data.source.remote.music.genre.ServerGenreRestAdapter
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = arrayOf(CloudModule::class))
class GenreCloudModule {

    @Provides @Singleton
    fun provideServerGenreRestAdapter(retrofit: Retrofit.Builder): ServerGenreRestAdapter {
        return retrofit.baseUrl(ServerGenreRestAdapter.ENDPOINT).build()
                .create(ServerGenreRestAdapter::class.java)
    }
}
