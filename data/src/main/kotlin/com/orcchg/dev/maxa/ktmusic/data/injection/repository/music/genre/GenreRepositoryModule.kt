package com.orcchg.dev.maxa.ktmusic.data.injection.repository.music.genre

import com.orcchg.dev.maxa.ktmusic.data.injection.remote.music.genre.GenreCloudModule
import com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.mapper.GenreToDboMapper
import com.orcchg.dev.maxa.ktmusic.data.model.dbo.music.populator.GenreToDboPopulator
import com.orcchg.dev.maxa.ktmusic.data.model.entity.music.mapper.GenreMapper
import com.orcchg.dev.maxa.ktmusic.data.model.entity.music.mapper.TotalValueMapper
import com.orcchg.dev.maxa.ktmusic.data.source.local.music.genre.GenreDatabase
import com.orcchg.dev.maxa.ktmusic.data.source.remote.music.genre.GenreCloud
import com.orcchg.dev.maxa.ktmusic.data.source.remote.music.genre.ServerGenreRestAdapter
import com.orcchg.dev.maxa.ktmusic.data.source.repository.music.genre.GenreDataSource
import com.orcchg.dev.maxa.ktmusic.data.source.repository.music.genre.GenreRepositoryImpl
import com.orcchg.dev.maxa.ktmusic.domain.repository.music.GenreRepository
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = arrayOf(GenreCloudModule::class))
class GenreRepositoryModule {

    @Named("genreSource") @Provides @Singleton
    internal fun provideGenreCloudSource(restAdapter: ServerGenreRestAdapter, genreMapper: GenreMapper,
            totalValueMapper: TotalValueMapper)
        : GenreDataSource {
        return GenreCloud(restAdapter, genreMapper, totalValueMapper)
    }

    @Named("genreDatabase") @Provides @Singleton
    internal fun provideGenreLocalSource(mapper: GenreToDboMapper, populator: GenreToDboPopulator)
        : GenreDataSource {
        return GenreDatabase(mapper, populator)
    }

    @Provides @Singleton
    internal fun provideGenreRepository(repository: GenreRepositoryImpl): GenreRepository {
        return repository
    }
}
