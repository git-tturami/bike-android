package com.gitturami.bike.view.main.presenter.handler

import com.gitturami.bike.view.main.MainContact

class ScreenshotHandler: StateHandler {
    override fun onStateChanged(view: MainContact.View) {
        view.hideItemSheet()
        view.showScreenshotDialog()
    }
}