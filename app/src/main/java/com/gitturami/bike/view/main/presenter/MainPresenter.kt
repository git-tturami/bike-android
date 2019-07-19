package com.gitturami.bike.view.main.presenter

class MainPresenter : MainContact.Presenter {
    private lateinit var view: MainContact.View

    override fun takeView(view: MainContact.View) {
        this.view = view
    }
}