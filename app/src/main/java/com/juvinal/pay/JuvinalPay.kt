package com.juvinal.pay

import android.app.Application
import com.juvinal.pay.container.AppContainer
import com.juvinal.pay.container.AppContainerImpl

class JuvinalPay: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
    }

}