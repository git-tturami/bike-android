package com.gitturami.bike.view.intro

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gitturami.bike.R
import com.gitturami.bike.view.intro.presenter.MainContact
import com.gitturami.bike.view.intro.presenter.MainPresenter

class MainActivity : AppCompatActivity(), MainContact.View {
    private lateinit var presenter: MainContact.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter()
        presenter.takeView(this)
    }

    override fun start() {}

    override fun onDestroy() {
        super.onDestroy()
//        presenter.dropView()
    }
}