package com.gitturami.bike.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.gitturami.bike.R
import com.gitturami.bike.logger.Logger
import com.gitturami.bike.model.station.pojo.Station
import com.gitturami.bike.view.main.presenter.MainContact
import com.gitturami.bike.view.main.state.State
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottom_sheet.view.*

class BottomSheetDialog(val presenter: MainContact.Presenter): BottomSheetDialogFragment() {

    companion object {
        val TAG = "BottomSheetDialog"
    }

    lateinit var station: Station

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.i(TAG, "onCreate()")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Logger.i(TAG, "onCreateView()")
        val view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
        val parsedTitle = station.stationName.split(".")[1].trim()
        view.stationTitle.text = parsedTitle
        view.stationEnable.text = "· 대여가능 : ${station.parkingBikeTotCnt}"
        view.stationTotal.text = "· 총 자전거 : ${station.rackTotCnt}"

        if (presenter.getState() == State.PREPARE) {
            view.setButton.text = "출발지 선택"
        } else if (presenter.getState() == State.SET_START) {
            view.setButton.text = "도착지 선택"
        }

        view.setButton.setOnClickListener {
            presenter.setSearchView(parsedTitle)
            dismiss()
        }
        setStarView(view.stationStar)
        return view
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
        val star = ImageView(context)
        star.background = context?.getDrawable(res)
        star.layoutParams = ViewGroup.LayoutParams(24, 24)
        return star
    }
}