package com.gitturami.bike.view.main.presenter.handler

import com.gitturami.bike.logger.Logger
import com.gitturami.bike.view.main.MainContact

class PrepareHandler: StateHandler {
    val TAG = "PrepareHandler"
    override fun onStateChanged(view: MainContact.View) {
        Logger.i(TAG, "onStateChanged()")
        view.setStartSearchView("")
        view.setFinishSearchView("")
        view.hidePath()
        view.setMarkers()
        view.hideCategorySheet()
        view.hideWayPointSheet()
    }
}