package com.orcchg.dev.maxa.ktmusic.app.injection.navigation

import com.orcchg.dev.maxa.ktmusic.app.injection.PerActivity
import com.orcchg.dev.maxa.ktmusic.app.navigation.Navigator
import dagger.Module
import dagger.Provides

@Module
class NavigationModule {

    @Provides @PerActivity
    internal fun provideNavigator(): Navigator {
        return Navigator()
    }
}
