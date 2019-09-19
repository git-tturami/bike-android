package com.gitturami.bike.view.main.presenter.handler

import com.gitturami.bike.logger.Logger
import com.gitturami.bike.view.main.MainContact

class PostSelectHandler: StateHandler {
    val TAG = "PostSelectHandler"
    override fun onStateChanged(view: MainContact.View) {
        Logger.i(TAG, "onStateChanged() : Set Post Select")
        view.hideWayPointSheet()
        view.hideCategorySheet()
    }
}