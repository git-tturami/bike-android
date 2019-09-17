package com.gitturami.bike.view.main.sheet.waypoint

import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import com.gitturami.bike.adapter.WayPointAdapter
import com.gitturami.bike.adapter.contact.WayPointAdapterContact
import com.gitturami.bike.data.RecyclerItem
import com.gitturami.bike.logger.Logger
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

        recyclerView = activity.recycler_view
        val wayPointAdapter = WayPointAdapter(activity)
        recyclerView.adapter = wayPointAdapter
        wayPointModel = wayPointAdapter
        wayPointView = wayPointAdapter
        loadItems()
        wayPointView.onClickFunc = { position -> onClickListener(position) }
    }

    fun halfWayPointSheet() {
        sheetBehavior.peekHeight = 400
        sheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
    }

    fun collapseWayPointSheet() {
        sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    fun hiddenWayPointSheet() {
        sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    fun expandWayPointSheet() {
        sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    // TODO: When context is defined, this function is called at Model.
    private fun loadItems() {
        val testList = arrayListOf(
                RecyclerItem("test1", "test1"),
                RecyclerItem("test2", "test2"),
                RecyclerItem("test3", "test3")
        )
        wayPointModel.addItems(testList)
        wayPointView.notifyAdapter()
    }

    // TODO: add selected location of item in Path.
    private fun onClickListener(position: Int) {
        Logger.i(TAG, wayPointModel.getItem(position).title)
    }
}