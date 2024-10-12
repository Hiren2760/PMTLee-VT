package com.pg.crm

import android.app.Application
import android.content.ContextWrapper
import com.pg.crm.utils.Prefs
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PGCRM: Application() {
    companion object {
        lateinit var instance: PGCRM
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()
    }
}