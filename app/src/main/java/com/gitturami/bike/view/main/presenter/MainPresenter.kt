package com.gitturami.bike.view.main.presenter

import android.content.Context
import android.graphics.PointF
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.gitturami.bike.adapter.contact.TitleAdapterContact
import com.gitturami.bike.data.recyclerItem
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.skt.Tmap.*
import com.skt.Tmap.TMapGpsManager
import com.skt.Tmap.TMapPoint

class MainPresenter : MainContact.Presenter, BottomSheetBehavior.BottomSheetCallback(){
    override lateinit var  view: MainContact.View
    private lateinit var bottomSheetBehavior : BottomSheetBehavior<LinearLayout>
    lateinit var  tmapView : TMapView
    lateinit var mpoint: TMapPoint

    override lateinit var titleAdapterModel: TitleAdapterContact.Model
    override var titleAdapterView: TitleAdapterContact.View? = null
        set(value) {
            field = value
            field?.onClickFunc = { position, textView -> onClickListener(position,textView)}
        }

    override fun takeView(view: MainContact.View) {
        this.view = view
    }

    override fun test() {
        // TODO: implementing to get point(latitude, longitude). In some case, we need to implement Point class.
        val tMapPointStart = TMapPoint(37.5048935, 126.9573662) // 중앙대학교
        val tMapPointEnd = TMapPoint(37.5099724, 126.9949061) // 반포한강공원
        this.view.findPath(tMapPointStart, tMapPointEnd)
    }

    override fun onPressUpEvent(p0: java.util.ArrayList<TMapMarkerItem>?, p1: java.util.ArrayList<TMapPOIItem>?, p2: TMapPoint?, p3: PointF?): Boolean {
        mpoint = p2!!
        Log.d("tag",mpoint.toString())
        val tItem = TMapMarkerItem()
        tItem.tMapPoint = TMapPoint( mpoint?.latitude!!.toDouble() , mpoint?.longitude!!.toDouble())
        tItem.visible = TMapMarkerItem.VISIBLE
        tmapView.addMarkerItem(tItem.id, tItem)
        setBottomSheetBehaviorStateCollapse(bottomSheetBehavior)
        return true
    }

    override fun onPressEvent(p0: java.util.ArrayList<TMapMarkerItem>?, p1: java.util.ArrayList<TMapPOIItem>?, p2: TMapPoint?, p3: PointF?): Boolean {
        return true
    }

    override fun onSlide(bottomSheet: View, slideOffset: Float) {
        when (slideOffset) {
            0F -> bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
            1F -> bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
            else -> {
                }
        }
    }

    override fun onStateChanged(bottomSheet: View, newState: Int) {
        when (newState) {
            BottomSheetBehavior.STATE_DRAGGING ->view.changeState("DRAGGING")
            BottomSheetBehavior.STATE_SETTLING -> view.changeState("SETTLING")
            BottomSheetBehavior.STATE_EXPANDED -> view.changeState("EXPANDED")
            BottomSheetBehavior.STATE_COLLAPSED -> view.changeState("COLLAPSED")
            BottomSheetBehavior.STATE_HIDDEN -> view.changeState("HIDDEN")
            BottomSheetBehavior.STATE_HALF_EXPANDED -> view.changeState("EXPANDED")
            else -> {
            }
        }
    }

    override fun setBottomSheetBehaviorStateCollapse(bottomSheetBehavior: BottomSheetBehavior<LinearLayout>) {
        checkPeekHeightAndSetHeight(bottomSheetBehavior)
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
    }

    override fun checkPeekHeightAndSetHeight(bottomSheetBehavior: BottomSheetBehavior<LinearLayout>){
        if(bottomSheetBehavior.peekHeight!=150) {
            bottomSheetBehavior.setPeekHeight(150)
        }
    }

    override fun setBottomSheetBehavior(bottomSheetBehavior : BottomSheetBehavior<LinearLayout>) {
        this.bottomSheetBehavior=bottomSheetBehavior
    }

    override fun loadItems(context: Context, isClear: Boolean) {
        val tempList = arrayListOf<recyclerItem>(
                recyclerItem("나는","멍청이입니다."),
                recyclerItem("다시는", "술을 그렇게 먹지 않겠습니다 ."),
                recyclerItem("인생", "넘모 슬픕니다."),
                recyclerItem("제가 또 술을 그렇게 마시면", "사람이 아닙니다."),
                recyclerItem("하지만 유감스럽게도", "이미 사람은 아닙니다."),
                recyclerItem("왈왈", "크르르르르르르르르르릉"),
                recyclerItem("반성해서", "개과천선하겠습니다.")
        )
        titleAdapterModel.addItems(tempList)
        titleAdapterView?.notifyAdapter()
    }

    private fun onClickListener(position: Int,textview:TextView) {
        titleAdapterModel.getItem(position).let {
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

