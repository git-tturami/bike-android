package com.gitturami.bike.view.main.presenter

import android.widget.LinearLayout
import com.gitturami.bike.adapter.contact.TitleAdapterContact
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView

interface MainContact {
    interface View {
        fun findPath(start: TMapPoint, end: TMapPoint)
        fun  showToast(title: String)
    }
    interface Presenter {

        var  view: MainContact.View
        var titleAdapterModel: TitleAdapterContact.Model
        var titleAdapterView: TitleAdapterContact.View?


        fun takeView(view: MainContact.View)
        fun test()
        fun isCollapse(bottomSheetBehavior: BottomSheetBehavior<LinearLayout>)
        fun setBottomSheetBehavior(bottomSheetBehavior : BottomSheetBehavior<LinearLayout>)
    }
}