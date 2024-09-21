//Vek Histories

package com.example.veksoul.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.veksoul.VekSoulApplication
import com.example.veksoul.ui.home.HomeViewModel
import com.example.veksoul.ui.soul.SoulDetailsViewModel
import com.example.veksoul.ui.soul.SoulEditViewModel
import com.example.veksoul.ui.soul.SoulEntryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            SoulEditViewModel(
                this.createSavedStateHandle(),
                crudApplication().container.soulsRepository
            )
        }

        initializer {
            SoulEntryViewModel(crudApplication().container.soulsRepository)
        }

        initializer {
            SoulDetailsViewModel(
                this.createSavedStateHandle(),
                crudApplication().container.soulsRepository
            )
        }

        initializer {
            HomeViewModel(crudApplication().container.soulsRepository)
        }
    }
}

fun CreationExtras.crudApplication(): VekSoulApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as VekSoulApplication)