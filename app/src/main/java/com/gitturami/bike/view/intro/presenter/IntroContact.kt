package com.gitturami.bike.view.main.presenter


interface IntroContact {
    interface View  {
        fun start()
    }
    interface Presenter  {
        fun takeView(view: View)
        fun changeActivity()

    }
}