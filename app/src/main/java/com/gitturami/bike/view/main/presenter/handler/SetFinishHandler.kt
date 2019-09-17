package com.gitturami.bike.view.main.presenter.handler

import com.gitturami.bike.logger.Logger
import com.gitturami.bike.view.main.MainContact

class SetFinishHandler: StateHandler {
    val TAG = "SetFinishHandler"
    override fun onStateChanged(view: MainContact.View) {
        Logger.i(TAG, "onStateChanged()")
        view.setMarkers()
        view.hideCategorySheet()
        view.hidePath()
    }
}