package com.gitturami.bike.view.main.presenter

interface MainContact {
    interface View {

    }
    interface Presenter {
        fun takeView(view: View)
    }
}