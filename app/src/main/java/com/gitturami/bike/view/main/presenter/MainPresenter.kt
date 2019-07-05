package com.gitturami.bike.view.main.presenter

import com.gitturami.bike.base.BaseView

class MainPresenter : MainContact.Presenter {
    private lateinit var view: BaseView

    override fun takeView(view: MainContact.View) {
        this.view = view
    }

//    override fun dropView() {
//        this.view = NullActivity()
//    }
}