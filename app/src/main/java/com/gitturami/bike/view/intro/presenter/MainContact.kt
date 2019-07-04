package com.gitturami.bike.view.intro.presenter

import com.gitturami.bike.view.intro.base.BasePresenter
import com.gitturami.bike.view.intro.base.BaseView

interface MainContact {
    interface View: BaseView {

    }
    interface Presenter: BasePresenter {
        fun takeView(view: View)
//        fun dropView()
    }
}