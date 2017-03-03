package com.orcchg.dev.maxa.ktmusic.app.ui.base.stub

import com.orcchg.dev.maxa.ktmusic.app.ui.base.BaseActivity
import com.orcchg.dev.maxa.ktmusic.app.ui.base.BasePresenter

abstract class SimpleBaseActivity : BaseActivity<StubMvpView, BasePresenter<StubMvpView>>(), PassiveView {

    override fun createPresenter(): BasePresenter<StubMvpView> {
        return SimpleBasePresenter()
    }

    override fun injectDependencies() {
        // empty
    }
}
