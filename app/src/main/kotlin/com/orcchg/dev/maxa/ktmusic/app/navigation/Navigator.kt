package com.orcchg.dev.maxa.ktmusic.app.navigation

import android.content.Context
import com.orcchg.dev.maxa.ktmusic.app.injection.PerActivity
import com.orcchg.dev.maxa.ktmusic.app.ui.music.detail.ArtistDetailsActivity
import com.orcchg.dev.maxa.ktmusic.app.ui.music.list.ArtistListActivity
import javax.inject.Inject

@PerActivity
class Navigator @Inject constructor() {

    fun openListScreen(context: Context) {
        val intent = ArtistListActivity.getCallingIntent(context)
        context.startActivity(intent)
    }

    fun openDetailsScreen(context: Context, artistId: Long) {
        val intent = ArtistDetailsActivity.getCallingIntent(context, artistId)
        context.startActivity(intent)
    }
}
