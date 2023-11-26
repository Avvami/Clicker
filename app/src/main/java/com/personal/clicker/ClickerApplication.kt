package com.personal.clicker

import android.app.Application
import com.personal.clicker.di.AppModule
import com.personal.clicker.di.AppModuleImpl

class ClickerApplication: Application() {

    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }
}