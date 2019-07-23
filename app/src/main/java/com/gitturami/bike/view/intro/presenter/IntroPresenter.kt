package com.gitturami.bike.view.intro.presenter

import android.os.Handler

class IntroPresenter : IntroContact.Presenter {
    private lateinit var view: IntroContact.View

    override fun takeView(view: IntroContact.View) {
        this.view = view
    }
    override fun changeActivity() {
        Handler().postDelayed({
            view.start()
        }, 2000)
    }
}