package com.gitturami.bike.view.main.presenter.handler

import com.gitturami.bike.logger.Logger
import com.gitturami.bike.view.main.MainContact

class WayPointHandler: StateHandler {
    val TAG = "WayPointHandler"
    override fun onStateChanged(view: MainContact.View) {
        Logger.i(TAG, "onStateChanged()")
        view.hideCategorySheet()
        view.halfWayPointSheet()
    }
}