//Vek Histories

package com.example.veksoul

import android.app.Application
import com.example.veksoul.data.AppContainer
import com.example.veksoul.data.AppDataContainer

class VekSoulApplication: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}