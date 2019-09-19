package com.gitturami.bike.view.main.sheet.waypoint

import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import com.gitturami.bike.adapter.WayPointAdapter
import com.gitturami.bike.adapter.contact.WayPointAdapterContact
import com.gitturami.bike.data.RecyclerItem
import com.gitturami.bike.logger.Logger
import com.gitturami.bike.model.common.pojo.DefaultItem
import com.gitturami.bike.view.main.MainActivity
import com.gitturami.bike.view.main.sheet.waypoint.listener.CategorySheetListener
import com.gitturami.bike.view.main.sheet.waypoint.listener.DetailWayPointSheetListener
import com.gitturami.bike.view.main.state.State
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.layout_detail_waypoint_bottom_sheet.*

class DetailWayPointSheetManager(activity: MainActivity, listener: (State) -> Unit) {
    companion object {
        private val TAG = "DetailWayPointSheetManager"
    }

    private val detailWayPointSheet: CoordinatorLayout = activity.detail_waypoint_bottom_sheet
    private val sheetBehavior: BottomSheetBehavior<CoordinatorLayout>
    private val wayPointModel: WayPointAdapterContact.Model
    private val wayPointView: WayPointAdapterContact.View
    //private val categorySheetListener: CategorySheetListener
    private val sheetListener: DetailWayPointSheetListener

    private val recyclerView: RecyclerView

    init {
        sheetBehavior = BottomSheetBehavior.from(detailWayPointSheet)
        // categorySheetListener = CategorySheetListener(sheetBehavior, listener)
        sheetListener = DetailWayPointSheetListener(sheetBehavior, activity.getPresenter())

        sheetBehavior.bottomSheetCallback = sheetListener

        detailWayPointSheet.setOnClickListener {
            sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        recyclerView = activity.category_items
        val wayPointAdapter = WayPointAdapter(activity)
        recyclerView.adapter = wayPointAdapter
        wayPointModel = wayPointAdapter
        wayPointView = wayPointAdapter
        wayPointView.onClickFunc = { position -> onClickListener(position) }
    }

    fun halfWayPointSheet() {
        sheetBehavior.isHideable = false
        sheetBehavior.peekHeight = 200
        sheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
    }

    fun collapseWayPointSheet() {
        sheetBehavior.isHideable = false
        sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    fun hiddenWayPointSheet() {
        sheetBehavior.isHideable = true
        sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    } 

    fun expandWayPointSheet() {
        sheetBehavior.isHideable = false
        sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    // TODO: When context is defined, this function is called at Model.
    fun setItems(itemList: Array<DefaultItem>) {
        val list = arrayListOf(
                RecyclerItem(itemList[0].getName(), itemList[0].getTel()),
                RecyclerItem(itemList[1].getName(), itemList[1].getTel()),
                RecyclerItem(itemList[2].getName(), itemList[2].getTel()),
                RecyclerItem(itemList[3].getName(), itemList[3].getTel()),
                RecyclerItem(itemList[4].getName(), itemList[4].getTel())
        )
        wayPointModel.clearItem()
        wayPointModel.addItems(list)
        wayPointView.notifyAdapter()
    }

    fun addItem(item: DefaultItem) {
        if (wayPointModel.getSize() < 10) {
            Logger.i(TAG, "addItem: ${item.getName()}, ${item.getTel()}")
            wayPointModel.addItem(
                    RecyclerItem(item.getName(), item.getTel())
            )
            wayPointView.notifyAdapter()
        }
    }

    fun clearItem() {
        wayPointModel.clearItem()
    }

    // TODO: add selected location of item in Path.
    private fun onClickListener(position: Int) {
        Logger.i(TAG, wayPointModel.getItem(position).title)
    }
}