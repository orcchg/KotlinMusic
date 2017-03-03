package com.orcchg.dev.maxa.ktmusic.app.ui.base.stub

import android.support.v7.widget.LinearLayoutManager
import com.orcchg.dev.maxa.ktmusic.app.ui.base.BaseListFragment
import com.orcchg.dev.maxa.ktmusic.app.ui.base.BasePresenter

class SimpleBaseListFragment : BaseListFragment<StubMvpView, BasePresenter<StubMvpView>>(), PassiveView {

    override fun createPresenter(): BasePresenter<StubMvpView> {
        return SimpleBasePresenter()
    }

    override fun injectDependencies() {
        // empty
    }

    override fun createLayoutManager(): LinearLayoutManager {
        return LinearLayoutManager(getActivity())
    }

    override fun onScroll(itemsLeftToEnd: Int) {
        // override in subclass
    }

    override fun onScrollTop() {
        // override in subclass
    }
}
