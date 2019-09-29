package com.gitturami.bike.utils

import android.graphics.Bitmap
import android.os.Environment
import com.gitturami.bike.logger.Logger
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object ScreenShotUtil {
    val TAG = "ScreenShotUtil"

    fun saveBitmapToGallay(bitmap: Bitmap) {
        val root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val dir = File("$root/bike")
        dir.mkdir()
        val currentTime = System.currentTimeMillis()
        val fileName = "Image-$currentTime.jpg"
        val file = File(dir, fileName)
        if (file.exists()) {
            file.delete()
        }
        try {
            val fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
        } catch (e: IOException) {
            Logger.e(TAG, "$e")
        }
    }
}