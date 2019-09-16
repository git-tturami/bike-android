package com.gitturami.bike.view.main.sheet.waypoint

import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import com.gitturami.bike.adapter.WayPointAdapter
import com.gitturami.bike.adapter.contact.WayPointAdapterContact
import com.gitturami.bike.data.RecyclerItem
import com.gitturami.bike.logger.Logger
import com.gitturami.bike.view.main.MainActivity
import com.gitturami.bike.view.main.listener.BottomSheetListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.layout_waypoint_bottom_sheet.*
import kotlinx.android.synthetic.main.layout_bottom_sheet.*

class WayPointSheetManager(activity: MainActivity) {
    companion object {
        private val TAG = "WayPointSheetManager"
    }

    private val wayPointSheet: CoordinatorLayout = activity.waypoint_bottom_sheet
    private val sheetBehavior: BottomSheetBehavior<CoordinatorLayout>
    private val wayPointModel: WayPointAdapterContact.Model
    private val wayPointView: WayPointAdapterContact.View
    private val recyclerView: RecyclerView = activity.recycler_view

    init {
        sheetBehavior = BottomSheetBehavior.from(wayPointSheet)
        sheetBehavior.bottomSheetCallback = BottomSheetListener(sheetBehavior)
        val wayPointAdapter = WayPointAdapter(activity)
        recyclerView.adapter = wayPointAdapter
        wayPointModel = wayPointAdapter
        wayPointView = wayPointAdapter
        wayPointView.onClickFunc = { position -> onClickListener(position) }
    }

    fun collapseWayPointSheet() {
        checkHeightAndSet()
        sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    fun hiddenWayPointSheet() {
        sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun checkHeightAndSet() {
        if (sheetBehavior.peekHeight != 250) {
            sheetBehavior.peekHeight = 250
        }
    }

    // TODO: When context is defined, this function is called at Model.
    private fun loadItems(locationList: List<RecyclerItem>) {
        wayPointModel.addItems(locationList)
        wayPointView.notifyAdapter()
    }

    // TODO: add selected location of item in Path.
    private fun onClickListener(position: Int) {
        Logger.i(TAG, wayPointModel.getItem(position).title)
    }
}