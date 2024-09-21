//Vek Histories

package com.example.veksoul.ui.soul

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.veksoul.data.SoulsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SoulDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val soulsRepository: SoulsRepository
) : ViewModel() {

    private val soulId: Int = checkNotNull(savedStateHandle[SoulDetailsDestination.soulIdArg])

    val uiState: StateFlow<SoulDetailsUiState> =
        soulsRepository.getSoulStream(soulId)
            .filterNotNull()
            .map {
                SoulDetailsUiState(outOfStock = it.quantity <= 0, soulDetails = it.toSoulDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = SoulDetailsUiState()
            )



    fun reduceQuantityByOne() {
        viewModelScope.launch {
            val currentSoul = uiState.value.soulDetails.toSoul()
            if (currentSoul.quantity > 0) {
                soulsRepository.updateSoul(currentSoul.copy(quantity = currentSoul.quantity - 1))
            }
        }
    }

    suspend fun deleteSoul() {
        soulsRepository.deleteSoul(uiState.value.soulDetails.toSoul())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class SoulDetailsUiState(
    val outOfStock: Boolean = true,
    val soulDetails: SoulDetails = SoulDetails()
)