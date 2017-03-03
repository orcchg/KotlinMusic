package com.orcchg.dev.maxa.ktmusic.app.injection.navigation

import com.orcchg.dev.maxa.ktmusic.app.injection.PerActivity
import com.orcchg.dev.maxa.ktmusic.app.navigation.Navigator
import com.orcchg.dev.maxa.ktmusic.app.navigation.NavigatorHolder
import dagger.Component

@PerActivity
@Component(modules = arrayOf(NavigationModule::class))
interface NavigationComponent {

    fun inject(holder: NavigatorHolder)

    fun navigator(): Navigator
}
