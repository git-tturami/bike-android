package com.gitturami.bike.view.main.presenter

import android.widget.LinearLayout
import com.skt.Tmap.TMapView

class MainPresenter : MainContact.Presenter {
    private lateinit var tview: LinearLayout

    override fun takeView(view: LinearLayout) {
        this.tview = view
    }
}