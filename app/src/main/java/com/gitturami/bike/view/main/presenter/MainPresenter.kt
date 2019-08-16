package com.gitturami.bike.view.main.presenter

import com.skt.Tmap.TMapGpsManager
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.widget.Button
import com.gitturami.bike.R
import com.gitturami.bike.SettingActivity
import com.skt.Tmap.TMapPoint
import kotlinx.android.synthetic.main.activity_main.view.*


class MainPresenter : MainContact.Presenter {
    private lateinit var view: MainContact.View

    override fun takeView(view: MainContact.View) {
        this.view = view
    }

    override fun test() {
        // TODO: implementing to get point(latitude, longitude). In some case, we need to implement Point class.

        val tMapPointStart = TMapPoint(37.5048935, 126.9573662) // 중앙대학교
        val tMapPointEnd = TMapPoint(37.5099724, 126.9949061) // 반포한강공원
        this.view.findPath(tMapPointStart, tMapPointEnd)
    }

    override fun setGps(tMapGps: TMapGpsManager) {
        tMapGps.minTime = 1000
        tMapGps.minDistance = 5f
        tMapGps.provider = TMapGpsManager.NETWORK_PROVIDER // 인터넷에 연결(실내에서 유용)
        //tMapGps.provider = TMapGpsManager.GPS_PROVIDER
        tMapGps.OpenGps()
    }

}