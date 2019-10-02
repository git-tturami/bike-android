package com.gitturami.bike.view.main.presenter.handler

import com.gitturami.bike.logger.Logger
import com.gitturami.bike.view.main.MainContact

class ClickedWayPointHandler: StateHandler {
    val TAG = "CategoryHandler"
    override fun onStateChanged(view: MainContact.View) {
        Logger.i(TAG, "onStateChanged() : Set Clicked WayPoint")
        view.hideItemSheet()
        view.hideCategoryMarkers()
        view.expandResultSheet()
    }
}