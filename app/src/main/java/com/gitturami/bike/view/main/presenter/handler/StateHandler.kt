package com.gitturami.bike.view.main.presenter.handler

import com.gitturami.bike.view.main.MainContact

interface StateHandler {
    fun onStateChanged(view: MainContact.View)
}