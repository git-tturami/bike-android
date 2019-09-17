package com.gitturami.bike.view.main.presenter.handler

import com.gitturami.bike.logger.Logger
import com.gitturami.bike.view.main.MainContact

class SetStartHandler: StateHandler {
    val TAG = "SetStartHandler"
    override fun onStateChanged(view: MainContact.View) {
        Logger.i(TAG, "onStateChanged()")
        view.setFinishSearchView("")
    }
}