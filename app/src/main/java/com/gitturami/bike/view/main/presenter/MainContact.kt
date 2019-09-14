package com.gitturami.bike.view.main.presenter

import com.skt.Tmap.TMapGpsManager
import android.widget.LinearLayout
import com.gitturami.bike.adapter.RecommendAdapter
import com.gitturami.bike.data.RecyclerItem
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView

interface MainContact {
    interface View {
        fun findPath(start: TMapPoint, end: TMapPoint)
        fun showToast(title: String)
        fun setMarker(x: Double, y: Double, stationName: String)
    }
    interface Presenter : TMapView.OnClickListenerCallback {
        fun takeView(view: View)
        fun takeTMapView(tMapView: TMapView)
        fun takeRecyclerAdapter(recommendAdapter: RecommendAdapter)
        fun setGps(tMapGps: TMapGpsManager)
        fun setBottomSheetBehaviorStateCollapse(bottomSheetBehavior: BottomSheetBehavior<LinearLayout>)
        fun checkPeekHeightAndSetHeight(bottomSheetBehavior: BottomSheetBehavior<LinearLayout>)
        fun setBottomSheetBehavior(bottomSheetBehavior : BottomSheetBehavior<LinearLayout>)
        fun loadItems(locationList: ArrayList<RecyclerItem>)
        fun updateMarker()
    }
}