package com.gitturami.bike.view.main.presenter

import android.widget.LinearLayout
import com.skt.Tmap.TMapView

interface MainContact {
    interface View {
    }
    interface Presenter {
        fun takeView(view: LinearLayout)
    }
}