package com.gitturami.bike.view.intro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gitturami.bike.R
import com.gitturami.bike.view.intro.presenter.IntroContact
import com.gitturami.bike.view.intro.presenter.IntroPresenter
import com.gitturami.bike.view.main.MainActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior


class IntroActivity : AppCompatActivity(), IntroContact.View{

    private lateinit var presenter: IntroContact.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        presenter = IntroPresenter()
        presenter.takeView(this)
        presenter.changeActivity()
    }

    override fun start() {
        val nextIntent = Intent(this, MainActivity::class.java)
        startActivity(nextIntent)
    }
}
