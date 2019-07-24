package com.gitturami.bike.view.intro.presenter


interface IntroContact {
    interface View  {
        fun start()
    }
    interface Presenter  {
        fun takeView(view: View)
        fun changeActivity()

    }
}