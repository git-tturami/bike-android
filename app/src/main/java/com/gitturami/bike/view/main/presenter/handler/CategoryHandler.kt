package com.gitturami.bike.view.main.presenter.handler

import com.gitturami.bike.logger.Logger
import com.gitturami.bike.view.main.MainContact

class CategoryHandler: StateHandler {
    val TAG = "CategoryHandler"
    override fun onStateChanged(view: MainContact.View) {
        Logger.i(TAG, "onStateChanged() : Set Select Category")
        view.hideAllMarkers()
        view.hideCategoryMarkers()
        view.hideWayPointSheet()
        view.hideItemSheet()
        view.collapseCategorySheet()
    }
}