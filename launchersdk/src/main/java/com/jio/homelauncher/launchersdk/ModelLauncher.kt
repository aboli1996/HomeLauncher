package com.jio.homelauncher.launchersdk

import android.graphics.Bitmap
import android.graphics.drawable.Drawable

data class ModelLauncher(
    var appName : String,
    var packageName : String,
    var icon : Drawable,
    var activityClassName : String,
    var versionCode : Int,
    var versionName : String
)
