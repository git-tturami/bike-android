package com.gitturami.bike.view.intro.presenter

import com.gitturami.bike.base.BasePresenter
import com.gitturami.bike.base.BaseView

interface IntroContact {
    interface View: BaseView {

    }
    interface Presenter: BasePresenter {
        fun takeView(view: View)
        fun changeActivity()
//        fun dropView()
    }
}