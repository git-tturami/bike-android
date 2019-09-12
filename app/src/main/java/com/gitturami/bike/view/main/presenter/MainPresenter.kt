package com.gitturami.bike.view.main.presenter

import android.content.Context
import android.graphics.PointF
import android.view.View
import android.widget.LinearLayout
import com.gitturami.bike.di.model.station.DaggerStationDataManagerComponent
import com.gitturami.bike.di.model.station.StationDataManagerModule
import com.gitturami.bike.model.station.StationDataManager
import com.gitturami.bike.adapter.RecommendAdapter
import com.gitturami.bike.adapter.contact.RecommendAdapterContact
import com.gitturami.bike.data.RecyclerItem
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.skt.Tmap.*
import com.skt.Tmap.TMapGpsManager
import com.skt.Tmap.TMapPoint
import javax.inject.Inject
import kotlin.math.abs

class MainPresenter(context: Context) : MainContact.Presenter, BottomSheetBehavior.BottomSheetCallback(){
    private lateinit var view: MainContact.View
    private lateinit var bottomSheetBehavior : BottomSheetBehavior<LinearLayout>
    private lateinit var tMapView : TMapView
    private lateinit var currentClickedMapPoint: TMapPoint
    private lateinit var dragCheckPoint: PointF

    @Inject
    lateinit var stationDataManager: StationDataManager
    lateinit var recommendAdapterModel: RecommendAdapterContact.Model
    var recommendAdapterView: RecommendAdapterContact.View? = null
        set(value) {
            field = value
            field?.onClickFunc = { position -> onClickListener(position)}
        }

    init {
        injectDataManager(context)
    }

    fun injectDataManager(context: Context) {
        DaggerStationDataManagerComponent.builder()
                .stationDataManagerModule(StationDataManagerModule(context))
                .build()
                .inject(this)
    }

    override fun takeView(view: MainContact.View) {
        this.view = view
    }

    override fun takeTMapView(tMapView: TMapView) {
        this.tMapView = tMapView
    }

    override fun takeRecyclerAdapter(recommendAdapter: RecommendAdapter) {
        recommendAdapterModel = recommendAdapter
        recommendAdapterView = recommendAdapter
    }

    override fun onPressUpEvent(p0: java.util.ArrayList<TMapMarkerItem>?, p1: java.util.ArrayList<TMapPOIItem>?, p2: TMapPoint, p3: PointF): Boolean {
        if (!isDrag(dragCheckPoint, p3)) {
            currentClickedMapPoint = p2
            val tItem = TMapMarkerItem()
            tItem.tMapPoint = TMapPoint(currentClickedMapPoint.latitude, currentClickedMapPoint.longitude)
            tItem.visible = TMapMarkerItem.VISIBLE
            tMapView.addMarkerItem(tItem.id, tItem)
            setBottomSheetBehaviorStateCollapse(bottomSheetBehavior)
            recommendAdapterModel.clearItem()
        }
        return true
    }

    override fun onPressEvent(p0: java.util.ArrayList<TMapMarkerItem>?, p1: java.util.ArrayList<TMapPOIItem>?, p2: TMapPoint?, p3: PointF): Boolean {
        dragCheckPoint = p3
        return true
    }

    private fun isDrag(p1: PointF, p2: PointF): Boolean {
        if (abs(p1.x - p2.x) > 10.0 || abs(p1.y - p2.y) > 10.0) return true
        return false
    }

    override fun onSlide(bottomSheet: View, slideOffset: Float) {
        when (slideOffset) {
            0F -> bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
            1F -> bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
            else -> {}
        }
    }

    override fun onStateChanged(bottomSheet: View, newState: Int) {
        // TODO: not implemented
    }

    override fun setBottomSheetBehaviorStateCollapse(bottomSheetBehavior: BottomSheetBehavior<LinearLayout>) {
        checkPeekHeightAndSetHeight(bottomSheetBehavior)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun checkPeekHeightAndSetHeight(bottomSheetBehavior: BottomSheetBehavior<LinearLayout>){
        if (bottomSheetBehavior.peekHeight != 150) {
            bottomSheetBehavior.peekHeight = 150
        }
    }

    override fun setBottomSheetBehavior(bottomSheetBehavior: BottomSheetBehavior<LinearLayout>) {
        this.bottomSheetBehavior = bottomSheetBehavior
    }

    // TODO: When context is defined, this fuction is called at Model.
    override fun loadItems(locationList: ArrayList<RecyclerItem>) {
        recommendAdapterModel.addItems(locationList)
        recommendAdapterView?.notifyAdapter()
    }

    // TODO: add selected location of item in Path.
    private fun onClickListener(position: Int) {
        recommendAdapterModel.getItem(position).let {
            view.showToast(it.title)
        }
    }

    override fun setGps(tMapGps: TMapGpsManager) {
        tMapGps.minTime = 1000
        tMapGps.minDistance = 5f
        tMapGps.provider = TMapGpsManager.NETWORK_PROVIDER // 인터넷에 연결(실내에서 유용)
        tMapGps.OpenGps()
    }
}

