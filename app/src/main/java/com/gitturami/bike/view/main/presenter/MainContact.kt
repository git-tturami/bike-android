package com.gitturami.bike.view.main.presenter

import android.widget.LinearLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView

interface MainContact {
    interface View {
        fun findPath(start: TMapPoint, end: TMapPoint)
    }
    interface Presenter {
        fun takeView(view: MainContact.View)
        fun test()
        fun setpoint(tMapView: TMapView)
        fun isCollapse(bottomSheetBehavior: BottomSheetBehavior<LinearLayout>)
        fun setBottomSheetBehavior(bottomSheetBehavior : BottomSheetBehavior<LinearLayout>)

    }
}