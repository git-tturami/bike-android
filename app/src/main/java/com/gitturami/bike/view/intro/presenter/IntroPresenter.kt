package com.gitturami.bike.view.intro.presenter

import android.os.Handler
import com.gitturami.bike.base.BaseView

class IntroPresenter : IntroContact.Presenter {
    private lateinit var view: BaseView

    override fun takeView(view: IntroContact.View) {
        this.view = view
    }
    override fun changeActivity() {
        Handler().postDelayed({
            view.start()
        }, 2000)
    }
//    override fun dropView() {
//        this.view = NullActivity()
//    }
}