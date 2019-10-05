package com.gitturami.bike.view.main.sheet.select

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.gitturami.bike.R
import com.gitturami.bike.logger.Logger
import com.gitturami.bike.model.station.pojo.Station
import com.gitturami.bike.view.main.MainActivity
import com.gitturami.bike.view.main.MainContact
import com.gitturami.bike.view.main.state.State
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.layout_bottom_sheet.*
import kotlinx.android.synthetic.main.layout_bottom_sheet.view.*

class StationSheet(val presenter: MainContact.Presenter, val activity: MainActivity) {

    companion object {
        val TAG = "StationSheet"
    }

    private lateinit var station: Station
    private var bottomSheet: LinearLayout
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    init {
        Logger.i(TAG, "initLocationBottomSheet()")
        bottomSheet = activity.bottom_sheet
        setStarView(bottomSheet.stationStar)
    }

    fun initStation(station: Station) {
        this.station = station
        if (station.stationName.contains(".")) {

        }
        val parsedTitle = when (station.stationName.contains(".")) {
            true -> station.stationName!!.split(".")[1].trim()
            false -> station.stationName!!.trim()
        }
        bottomSheet.stationTitle.text = parsedTitle
        bottomSheet.stationEnable.text = "· 대여가능 : ${station.parkingBikeTotCnt}"
        bottomSheet.stationTotal.text = "· 총 자전거 : ${station.rackTotCnt}"

        if (presenter.getState() == State.PREPARE) {
            bottomSheet.setButton.text = "+ 출발"
        } else if (presenter.getState() == State.SET_START) {
            bottomSheet.setButton.text = "+ 도착"
        }

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

        bottomSheet.setButton.setOnClickListener {
            presenter.setSearchView(parsedTitle)
            presenter.setLocation(station)
            hiddenSelectSheet()
        }
    }

    private fun setStarView(starLayout: LinearLayout) {
        starLayout.addView(newStarImageView())
        starLayout.addView(newStarImageView())
        starLayout.addView(newStarImageView())
        starLayout.addView(newStarImageView())
        starLayout.addView(newEmptyStarImageView())
    }

    private fun newStarImageView(): ImageView {
        return createStarImageView(R.drawable.ic_star)
    }

    private fun newEmptyStarImageView(): ImageView {
        return createStarImageView(R.drawable.ic_star_empty)
    }

    private fun createStarImageView(res: Int): ImageView {
        val star = ImageView(activity)
        star.background = activity.getDrawable(res)
        star.layoutParams = ViewGroup.LayoutParams(24, 24)
        return star
    }

    fun collapseSelectSheet() {
        if (bottomSheetBehavior.peekHeight != 250) {
            bottomSheetBehavior.peekHeight = 250
        }
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        when(activity.getState()){
            State.SHOW_START -> activity.setButton.text = "+ 출발"
            State.SHOW_FINISH -> activity.setButton.text = "+ 도착"
        }
    }

    fun hiddenSelectSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }
}