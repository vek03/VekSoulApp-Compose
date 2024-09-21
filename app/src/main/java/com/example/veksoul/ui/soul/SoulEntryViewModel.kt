//Vek Histories

package com.example.veksoul.ui.soul

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.veksoul.data.Soul
import com.example.veksoul.data.SoulsRepository
import java.text.NumberFormat

class SoulEntryViewModel(private val soulsRepository: SoulsRepository) : ViewModel() {

    var soulUiState by mutableStateOf(SoulUiState())
        private set


    fun updateUiState(soulDetails: SoulDetails) {
        soulUiState =
            SoulUiState(soulDetails = soulDetails, isEntryValid = validateInput(soulDetails))
    }

    private fun validateInput(uiState: SoulDetails = soulUiState.soulDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank()
        }
    }

    suspend fun saveSoul() {
        if (validateInput()) {
            soulsRepository.insertSoul(soulUiState.soulDetails.toSoul())
        }
    }
}

data class SoulUiState(
    val soulDetails: SoulDetails = SoulDetails(),
    val isEntryValid: Boolean = false
)

data class SoulDetails(
    val id: Int = 0,
    val name: String = "",
    val price: String = "",
    val quantity: String = "",
)


fun SoulDetails.toSoul(): Soul = Soul(
    id = id,
    name = name,
    price = price.toDoubleOrNull() ?: 0.0,
    quantity = quantity.toIntOrNull() ?: 0
)

fun Soul.formatedPrice(): String {
    return NumberFormat.getCurrencyInstance().format(price)
}

fun Soul.toSoulUiState(isEntryValid: Boolean = false): SoulUiState = SoulUiState(
    soulDetails = this.toSoulDetails(),
    isEntryValid = isEntryValid
)

fun Soul.toSoulDetails(): SoulDetails = SoulDetails(
    id = id,
    name = name,
    price = price.toString(),
    quantity = quantity.toString()
)
