package com.gitturami.bike.view.main.presenter

import com.skt.Tmap.TMapPoint

class MainPresenter : MainContact.Presenter {
    private lateinit var view: MainContact.View
//    lateinit var view: MainContact.View
//    lateinit override var recyclerData: RecyclerData
//    lateinit override var adapterModel: RecyclerAdapterContract.Model
//    override var adapterView: RecyclerAdapterContract.View? = null
//        set(value) {
//            field = value
//            field?.onClickFunc = { onClickListener(it)}
//        }

    override fun takeView(view: MainContact.View) {
        this.view = view
    }

    override fun test() {
        // TODO: implementing to get point(latitude, longitude). In some case, we need to implement Point class.

        val tMapPointStart = TMapPoint(37.5048935, 126.9573662) // 중앙대학교
        val tMapPointEnd = TMapPoint(37.5099724, 126.9949061) // 반포한강공원
        this.view.findPath(tMapPointStart, tMapPointEnd)
    }

//    override fun loadItems(context: Context, isClear: Boolean) {
//
//        val tempList = arrayListOf<recyclerItem>(
//
//                recyclerItem("나는","멍청이입니다."),
//                recyclerItem("다시는", "술을 그렇게 먹지 않겠습니다."),
//                recyclerItem("인생", "넘모 슬픕니다."),
//                recyclerItem("제가 또 술을 그렇게 마시면", "사람이 아닙니다."),
//                recyclerItem("하지만 유감스럽게도", "이미 사람은 아닙니다."),
//                recyclerItem("왈왈", "크르르르르르르르르르릉"),
//                recyclerItem("반성해서", "개과천선하겠습니다.")
//
//        )
//        adapterModel.addItems(tempList)
//        adapterView?.notifyAdapter()
//    }
//
//    private fun onClickListener(position: Int){
//        view.showToast("왈왈왈!")
//    }
}