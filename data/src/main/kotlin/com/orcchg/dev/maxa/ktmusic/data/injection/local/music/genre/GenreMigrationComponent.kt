package com.orcchg.dev.maxa.ktmusic.data.injection.local.music.genre

import dagger.Component

@Component(modules = arrayOf(GenreMigrationModule::class))
interface GenreMigrationComponent
