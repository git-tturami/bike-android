package com.gitturami.bike.view.main

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.TextView
import com.gitturami.bike.R
import com.gitturami.bike.view.main.presenter.MainContact
import com.gitturami.bike.view.main.presenter.MainPresenter
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapView
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast


class MainActivity : AppCompatActivity(), MainContact.View {
    private lateinit var presenter: MainContact.Presenter
    private lateinit var tMapView: TMapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tb = findViewById<View>(R.id.app_toolbar) as Toolbar
        val st = findViewById<TextView>(R.id.startText)
        val et = findViewById<TextView>(R.id.endText)
        val it = findViewById<TextView>(R.id.infoText)

        st.setOnClickListener(View.OnClickListener {
            val toast = Toast.makeText(applicationContext, "start", Toast.LENGTH_LONG)
            toast.show ()
            // TextView 클릭될 시 할 코드작성
        })

        et.setOnClickListener(View.OnClickListener {
            val toast = Toast.makeText(applicationContext, "end", Toast.LENGTH_LONG)
            toast.show ()
            // TextView 클릭될 시 할 코드작성
        })

        it.setOnClickListener(View.OnClickListener {
            val toast = Toast.makeText(applicationContext, "info", Toast.LENGTH_LONG)
            toast.show ()
            // TextView 클릭될 시 할 코드작성
        })

        val linearLayoutTmap = linearLayoutTmap
        tMapView = TMapView(this)

        tMapView.setSKTMapApiKey("3826adf1-c99a-4e69-95c5-3763539465ea")
        linearLayoutTmap.addView(tMapView)
        tMapView.setCenterPoint(126.9573662, 37.5048935)

        presenter = MainPresenter()
        presenter.takeView(this)
        presenter.test()
    }

    override fun findPath(start:TMapPoint, end:TMapPoint) {
        try {
            val data = TMapData()
            Log.d("gess","gess")
            data.findPathData(start, end) { path ->
                runOnUiThread {
                    path.lineWidth = 5f
                    path.lineColor = Color.BLUE
                    tMapView.addTMapPath(path)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}