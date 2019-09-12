package com.gitturami.bike.logger

import android.util.Log

object Logger {
    fun d(tag: String, msg: String) {
        Log.d("BIKE@$tag", msg)
    }

    fun i(tag: String, msg: String) {
        Log.i("BIKE@$tag", msg)
    }

    fun v(tag: String, msg: String) {
        Log.v("BIKE@$tag", msg)
    }

    fun e(tag: String, msg: String) {
        Log.e("BIKE@$tag", msg)
    }
}