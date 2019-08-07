package com.gitturami.bike.view.main.presenter

//import android.content.Context
//import com.gitturami.bike.adapter.contract.RecyclerAdapterContract
//import com.gitturami.bike.data.RecyclerData
import com.skt.Tmap.TMapPoint

interface MainContact {
    interface View {
        fun findPath(start: TMapPoint, end: TMapPoint)
//        fun showToast(text: String)
    }
    interface Presenter {
//        var recyclerData: RecyclerData
//        var adapterModel: RecyclerAdapterContract.Model
//        var adapterView: RecyclerAdapterContract.View?

        fun takeView(view: MainContact.View)
        fun test()
    }
}