//Vek Histories

package com.example.veksoul.data

import android.content.Context

interface AppContainer {
    val soulsRepository: SoulsRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val soulsRepository: SoulsRepository by lazy {
        OfflineSoulRepository(DBase.getDatabase(context).soulDao())
    }
}