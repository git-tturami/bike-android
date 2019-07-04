package com.gitturami.bike.view.intro.presenter

import com.gitturami.bike.view.intro.base.BaseView

class MainPresenter : MainContact.Presenter {
    private lateinit var view: BaseView

    override fun takeView(view: MainContact.View) {
        this.view = view
    }

//    override fun dropView() {
//        this.view = NullActivity()
//    }
}