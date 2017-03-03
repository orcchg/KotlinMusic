package com.orcchg.dev.maxa.ktmusic.app.ui.base.stub

import com.orcchg.dev.maxa.ktmusic.app.ui.base.BasePresenter

class SimpleBasePresenter : BasePresenter<StubMvpView>() {

    override fun freshStart() {
        // empty
    }

    override fun onRestoreState() {
        // empty
    }
}
