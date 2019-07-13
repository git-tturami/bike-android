package com.gitturami.bike.view.main.presenter

import android.graphics.PointF
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import com.gitturami.bike.adapter.contact.TitleAdapterContact
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.skt.Tmap.TMapMarkerItem
import com.skt.Tmap.TMapPOIItem
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import com.skt.Tmap.TMapView.*

class MainPresenter : MainContact.Presenter, OnClickListenerCallback{

    override lateinit var  view: MainContact.View
    lateinit var  tmapView :TMapView
    var mpoint: TMapPoint? = null
    private lateinit var bottomSheetBehavior : BottomSheetBehavior<LinearLayout>

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

        val tMapPointStart = TMapPoint(37.5048935, 126.9573662) // 중앙대학교
        val tMapPointEnd = TMapPoint(37.5099724, 126.9949061) // 반포한강공원
        this.view.findPath(tMapPointStart, tMapPointEnd)
    }

    override fun onPressUpEvent(p0: java.util.ArrayList<TMapMarkerItem>?, p1: java.util.ArrayList<TMapPOIItem>?, p2: TMapPoint?, p3: PointF?): Boolean {
        mpoint = p2
        Log.d("tag",mpoint.toString())
        val tItem = TMapMarkerItem()
        tItem.tMapPoint = TMapPoint( mpoint?.latitude!!.toDouble() , mpoint?.longitude!!.toDouble())
        tItem.visible = TMapMarkerItem.VISIBLE
        tmapView.addMarkerItem(tItem.id, tItem)

        isCollapse(bottomSheetBehavior)

        return true
    }

    override fun onPressEvent(p0: java.util.ArrayList<TMapMarkerItem>?, p1: java.util.ArrayList<TMapPOIItem>?, p2: TMapPoint?, p3: PointF?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
    private fun onClickListener(position: Int,textview:TextView) {
//        titleAdapterModel.getItem(position).let {
//            view.showToast(it.title)
//        }
    view.showToast(textview.text.toString())
    }

    }
