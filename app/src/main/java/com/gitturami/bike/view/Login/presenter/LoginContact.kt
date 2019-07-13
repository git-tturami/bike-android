package com.gitturami.bike.view.Login.presenter

import com.gitturami.bike.base.BasePresenter
import com.gitturami.bike.base.BaseView

public interface LoginContact {
    interface View: BaseView{

    }
    interface Presenter: BasePresenter{
        fun takeView(view: View)
        fun changeActivity()
    }
}
