package com.jio.homelauncher.app

import android.app.Application
import android.content.Context

class LauncherClass : Application() {


    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object{
        var context : Context? = null
        fun getAppContext() : Context {
            return context!!
        }
    }

}