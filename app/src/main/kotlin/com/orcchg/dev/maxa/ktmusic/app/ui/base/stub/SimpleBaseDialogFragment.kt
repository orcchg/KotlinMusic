package com.orcchg.dev.maxa.ktmusic.app.ui.base.stub

import com.orcchg.dev.maxa.ktmusic.app.ui.base.BaseDialogFragment
import com.orcchg.dev.maxa.ktmusic.app.ui.base.BasePresenter

class SimpleBaseDialogFragment : BaseDialogFragment<StubMvpView, BasePresenter<StubMvpView>>() {

    override fun createPresenter(): BasePresenter<StubMvpView> {
        return SimpleBasePresenter()
    }

    override fun injectDependencies() {
        // empty
    }
}