package com.gitturami.bike.view.main.presenter

import com.skt.Tmap.TMapPoint

interface MainContact {
    interface View {
        fun findPath(start: TMapPoint, end: TMapPoint)
    }
    interface Presenter {
        fun takeView(view: MainContact.View)
        fun test()
    }
}