package com.gitturami.bike.view.main.presenter

import com.gitturami.bike.base.BasePresenter
import com.gitturami.bike.base.BaseView

interface MainContact {
    interface View: BaseView {

    }
    interface Presenter: BasePresenter {
        fun takeView(view: View)
//        fun dropView()
    }
}