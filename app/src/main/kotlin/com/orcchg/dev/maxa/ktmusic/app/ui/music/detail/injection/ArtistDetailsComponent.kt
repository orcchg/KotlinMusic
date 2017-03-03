package com.orcchg.dev.maxa.ktmusic.app.ui.music.detail.injection

import com.orcchg.dev.maxa.ktmusic.app.injection.PerActivity
import com.orcchg.dev.maxa.ktmusic.app.injection.application.ApplicationComponent
import com.orcchg.dev.maxa.ktmusic.app.ui.music.detail.ArtistDetailsPresenter
import com.orcchg.dev.maxa.ktmusic.data.injection.repository.music.artist.ArtistRepositoryComponent
import dagger.Component

@PerActivity
@Component(modules = arrayOf(ArtistDetailsModule::class), dependencies = arrayOf(ApplicationComponent::class, ArtistRepositoryComponent::class))
interface ArtistDetailsComponent {

    /**
     * Template for dagger-generated factory method to provide
     * an instance of [ArtistDetailsPresenter] class for where this
     * [ArtistDetailsComponent] injects to.
     */
    fun presenter(): ArtistDetailsPresenter
}
