package com.gitturami.bike.view.Login.presenter

import android.os.Handler
import com.gitturami.bike.base.BaseView

class LoginPresenter : LoginContact.Presenter{
    private  lateinit var  view: BaseView

    override fun takeView(view: LoginContact.View){
        this.view=view
    }

    override fun changeActivity() {
        Handler().postDelayed({
            view.start()
    },2000 )
    }

}