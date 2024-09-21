//Vek Histories

package com.example.veksoul.ui.soul

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.veksoul.data.SoulsRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SoulEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val soulsRepository: SoulsRepository
) : ViewModel() {
    var soulUiState by mutableStateOf(SoulUiState())
        private set

    private val itemId: Int = checkNotNull(savedStateHandle[SoulEditDestination.soulIdArg])

    init {
        viewModelScope.launch{
            soulUiState = soulsRepository.getSoulStream(itemId)
                .filterNotNull()
                .first()
                .toSoulUiState(true)
        }
    }


    private fun validateInput(uiState: SoulDetails = soulUiState.soulDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank()
        }
    }

    fun updateUiState(soulDetails: SoulDetails) {
        soulUiState =
            SoulUiState(soulDetails = soulDetails, isEntryValid = validateInput(soulDetails))
    }

    suspend fun updateItem() {
        if (validateInput(soulUiState.soulDetails)) {
            soulsRepository.updateSoul(soulUiState.soulDetails.toSoul())
        }
    }
}
