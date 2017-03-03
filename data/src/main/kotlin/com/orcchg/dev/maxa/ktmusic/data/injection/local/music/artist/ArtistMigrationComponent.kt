package com.orcchg.dev.maxa.ktmusic.data.injection.local.music.artist

import dagger.Component

@Component(modules = arrayOf(ArtistMigrationModule::class))
interface ArtistMigrationComponent
