package com.gitturami.bike.view.intro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gitturami.bike.MapActivity
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

        var intent = Intent(this, MapActivity::class.java)
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }
}
