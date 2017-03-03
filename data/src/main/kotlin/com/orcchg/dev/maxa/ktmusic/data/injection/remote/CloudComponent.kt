package com.orcchg.dev.maxa.ktmusic.data.injection.remote

import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(CloudModule::class))
interface CloudComponent {

    fun retrofitBuilder(): Retrofit.Builder
}
