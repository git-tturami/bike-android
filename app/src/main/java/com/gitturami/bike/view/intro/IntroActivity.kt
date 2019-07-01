package com.gitturami.bike.view.intro

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.gitturami.bike.R
import com.gitturami.bike.view.intro.presenter.IntroContact
import com.gitturami.bike.view.intro.presenter.IntroPresenter

class IntroActivity : AppCompatActivity() {

    private lateinit var presenter: IntroContact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        presenter = IntroPresenter()
        presenter.changeActivity()
    }
}
