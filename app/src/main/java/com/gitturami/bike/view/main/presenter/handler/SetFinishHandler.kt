package com.gitturami.bike.view.main.presenter.handler

import com.gitturami.bike.logger.Logger
import com.gitturami.bike.view.main.MainContact

class SetFinishHandler: StateHandler {
    val TAG = "SetFinishHandler"
    override fun onStateChanged(view: MainContact.View) {
        Logger.i(TAG, "onStateChanged() : Set Finish")
        view.setStationMarkers()
        view.hideCategorySheet()
        view.hidePath()
    }
}