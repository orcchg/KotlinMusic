package com.orcchg.dev.maxa.ktmusic.app.ui.base.mediator

/**
 * ViewMediator provides connection between two (or more) [MvpPresenter] instances.
 * Concrete subclasses must be isolated within corresponding packages.
 */
abstract class BaseMediator<FirstClient : MediatorReceiver, SecondClient : MediatorReceiver> {

    protected var clientFirst: FirstClient? = null
    protected var clientSecond: SecondClient? = null

    fun attachFirst(clientFirst: FirstClient) {
        if (this.clientFirst != null && this.clientFirst !== clientFirst) {
            val message = "Attempt to re-attach first client replacing an already existing one!"
            //            throw new MediatorReAttachException(message);  // TODO: enable and fix floating crash
        }
        this.clientFirst = clientFirst
    }

    fun attachSecond(clientSecond: SecondClient) {
        if (this.clientSecond != null && this.clientSecond !== clientSecond) {
            val message = "Attempt to re-attach second client replacing an already existing one!"
            //            throw new MediatorReAttachException(message);  // TODO: enable and fix floating crash
        }
        this.clientSecond = clientSecond
    }

    fun detachFirst() {
        clientFirst = null
    }

    fun detachSecond() {
        clientSecond = null
    }
}
