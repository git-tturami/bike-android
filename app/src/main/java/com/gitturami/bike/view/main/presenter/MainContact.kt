package com.gitturami.bike.view.main.presenter

import com.skt.Tmap.TMapGpsManager
import android.content.Context
import android.widget.LinearLayout
import com.gitturami.bike.adapter.contact.TitleAdapterContact
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView

interface MainContact {
    interface View {
        fun findPath(start: TMapPoint, end: TMapPoint)
        fun showToast(title: String)
        fun changeState(strings:String)
    }
    interface Presenter : TMapView.OnClickListenerCallback{
        var view: MainContact.View
        var titleAdapterModel: TitleAdapterContact.Model
        var titleAdapterView: TitleAdapterContact.View?

        fun takeView(view: MainContact.View)
        fun test()
        fun setGps(tMapGps: TMapGpsManager)
        fun setBottomSheetBehaviorStateCollapse(bottomSheetBehavior: BottomSheetBehavior<LinearLayout>)
        fun checkPeekHeightAndSetHeight(bottomSheetBehavior: BottomSheetBehavior<LinearLayout>)
        fun setBottomSheetBehavior(bottomSheetBehavior : BottomSheetBehavior<LinearLayout>)
        fun loadItems(context: Context, isClear: Boolean)

    }
}