package com.orcchg.dev.maxa.ktmusic.data.injection.repository.music.artist

import com.orcchg.dev.maxa.ktmusic.data.injection.remote.music.artist.ArtistCloudModule
import com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.mapper.ArtistToDboMapper
import com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.mapper.SmallArtistToDboMapper
import com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.populator.ArtistToDboPopulator
import com.orcchg.dev.maxa.ktmusic.data.model.entity.music.mapper.ArtistMapper
import com.orcchg.dev.maxa.ktmusic.data.model.entity.music.mapper.SmallArtistMapper
import com.orcchg.dev.maxa.ktmusic.data.model.entity.music.mapper.TotalValueMapper
import com.orcchg.dev.maxa.ktmusic.data.source.local.music.artist.ArtistDatabase
import com.orcchg.dev.maxa.ktmusic.data.source.remote.music.artist.ArtistCloud
import com.orcchg.dev.maxa.ktmusic.data.source.remote.music.artist.ServerArtistRestAdapter
import com.orcchg.dev.maxa.ktmusic.data.source.repository.music.artist.ArtistDataSource
import com.orcchg.dev.maxa.ktmusic.data.source.repository.music.artist.ArtistRepositoryImpl
import com.orcchg.dev.maxa.ktmusic.domain.repository.music.ArtistRepository
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = arrayOf(ArtistCloudModule::class))
class ArtistRepositoryModule {

    @Named("artistCloud") @Provides @Singleton
    internal fun provideArtistCloudSource(restAdapter: ServerArtistRestAdapter,
            artistMapper: ArtistMapper, smallArtistMapper: SmallArtistMapper,
            totalValueMapper: TotalValueMapper)
        : ArtistDataSource {
        return ArtistCloud(restAdapter, artistMapper, smallArtistMapper, totalValueMapper)
    }

    @Named("artistDatabase") @Provides @Singleton
    internal fun provideArtistLocalSource(artistMapper: ArtistToDboMapper,
            smallArtistMapper: SmallArtistToDboMapper, populator: ArtistToDboPopulator)
        : ArtistDataSource {
        return ArtistDatabase(artistMapper, smallArtistMapper, populator)
    }

    @Provides @Singleton
    internal fun provideArtistRepository(repository: ArtistRepositoryImpl): ArtistRepository {
        return repository
    }
}
