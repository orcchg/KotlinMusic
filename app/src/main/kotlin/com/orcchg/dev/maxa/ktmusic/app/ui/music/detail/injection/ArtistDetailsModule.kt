package com.orcchg.dev.maxa.ktmusic.app.ui.music.detail.injection

import com.orcchg.dev.maxa.ktmusic.app.injection.PerActivity
import com.orcchg.dev.maxa.ktmusic.domain.interactor.music.GetArtistDetails
import com.orcchg.dev.maxa.ktmusic.domain.repository.music.ArtistRepository
import com.orcchg.dev.maxa.ktmusic.library.domain.executor.PostExecuteScheduler
import com.orcchg.dev.maxa.ktmusic.library.domain.executor.ThreadExecutor
import dagger.Module
import dagger.Provides

/**
 * Constructor to configure and then instantiate the [DetailsModule] class.
 * It will be invoked when a dagger-generated implementation of [DetailsComponent]
 * will have been built.

 * Sets a [ArtistDetailsModule.artistId] to create specific [GetArtistDetails] on demand.
 */
@Module
class ArtistDetailsModule(private val artistId: Long) {

    /**
     * Specifies how to actually construct an injectable instance of [GetArtistDetails] class.

     * Despite [GetArtistDetails] class has an inject-constructor, we need to call it
     * to instantiate the class with [GetArtistDetails.artistId] specified, because the object
     * is configurable.

     * @return an instance to inject
     */
    @Provides @PerActivity
    internal fun provideGetArtistDetails(artistRepository: ArtistRepository,
            threadExecutor: ThreadExecutor, postExecuteScheduler: PostExecuteScheduler): GetArtistDetails {
        return GetArtistDetails(artistId, artistRepository, threadExecutor, postExecuteScheduler)
    }
}
