package com.gitturami.bike.view.main.presenter

import android.widget.LinearLayout



interface MainContact {
    interface View {
    }
    interface Presenter {
        fun takeView(view: LinearLayout)


    }
}