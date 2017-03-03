package com.orcchg.dev.maxa.ktmusic.app.ui.music.list.injection

import com.orcchg.dev.maxa.ktmusic.app.injection.PerActivity
import com.orcchg.dev.maxa.ktmusic.app.injection.application.ApplicationComponent
import com.orcchg.dev.maxa.ktmusic.app.ui.music.list.ArtistListPresenter
import com.orcchg.dev.maxa.ktmusic.data.injection.repository.music.artist.ArtistRepositoryComponent
import dagger.Component

@PerActivity
@Component(modules = arrayOf(ArtistListModule::class), dependencies = arrayOf(ApplicationComponent::class, ArtistRepositoryComponent::class))
interface ArtistListComponent {

    fun presenter(): ArtistListPresenter
}
