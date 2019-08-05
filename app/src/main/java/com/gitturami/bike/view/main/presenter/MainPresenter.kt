package com.gitturami.bike.view.main.presenter

import android.graphics.PointF
import android.util.Log
import android.widget.LinearLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPOIItem
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView


class MainPresenter : MainContact.Presenter {
    private lateinit var view: MainContact.View

    var mpoint: TMapPoint? = null
    private lateinit var bottomSheetBehavior : BottomSheetBehavior<LinearLayout>


    override fun takeView(view: MainContact.View) {
        this.view = view
    }

    override fun test() {
        // TODO: implementing to get point(latitude, longitude). In some case, we need to implement Point class.

        val tMapPointStart = TMapPoint(37.5048935, 126.9573662) // 중앙대학교
        val tMapPointEnd = TMapPoint(37.5099724, 126.9949061) // 반포한강공원
        this.view.findPath(tMapPointStart, tMapPointEnd)
    }

    override fun setpoint(tMapView: TMapView) {

        tMapView.setOnClickListenerCallBack(object : TMapView.OnClickListenerCallback {
            override fun onPressUpEvent(p0: ArrayList<TMapMarkerItem>?, p1: ArrayList<TMapPOIItem>?, p2: TMapPoint?, p3: PointF?): Boolean {
                mpoint = p2
                Log.d("tag",mpoint.toString())


                val tItem = TMapMarkerItem()
                tItem.tMapPoint = TMapPoint( mpoint?.latitude!!.toDouble() , mpoint?.longitude!!.toDouble())
                tItem.visible = TMapMarkerItem.VISIBLE
                tMapView.addMarkerItem(tItem.id, tItem)

                isCollapse(bottomSheetBehavior)

                return true
            }

            override fun onPressEvent(p0: ArrayList<TMapMarkerItem>?, p1: ArrayList<TMapPOIItem>?, p2: TMapPoint?, p3: PointF?): Boolean {

                return true
            }
        })
    }


    override fun isCollapse(bottomSheetBehavior: BottomSheetBehavior<LinearLayout>) {
        if(bottomSheetBehavior.peekHeight!=150) {
            bottomSheetBehavior.setPeekHeight(150)
        }
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
    }


    override fun setBottomSheetBehavior(bottomSheetBehavior : BottomSheetBehavior<LinearLayout>) {
    this.bottomSheetBehavior=bottomSheetBehavior

    }


    }
