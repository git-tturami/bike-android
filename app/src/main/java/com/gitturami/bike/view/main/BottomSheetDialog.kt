package com.gitturami.bike.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.gitturami.bike.R
import com.gitturami.bike.logger.Logger
import com.gitturami.bike.model.station.pojo.Station
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottom_sheet.view.*

class BottomSheetDialog: BottomSheetDialogFragment() {

    companion object {
        val TAG = "BottomSheetDialog"

        fun newInstance(): BottomSheetDialog =
                BottomSheetDialog().apply {  }
    }

    private lateinit var stationTitleView: TextView
    lateinit var station: Station

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.i(TAG, "onCreate()")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Logger.i(TAG, "onCreateView()")
        val view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
        stationTitleView = view.station_title
        stationTitleView.text = station.stationName
        return view
    }

}