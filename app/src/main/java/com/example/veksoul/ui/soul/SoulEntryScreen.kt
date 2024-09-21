//Vek Histories

package com.example.veksoul.ui.soul

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.veksoul.R
import com.example.veksoul.TopAppBar
import com.example.veksoul.ui.AppViewModelProvider
import com.example.veksoul.ui.navigation.Destination
import com.example.veksoul.ui.theme.darkBlack
import com.example.veksoul.ui.theme.darkLightTheme
import com.example.veksoul.ui.theme.glitterGold
import kotlinx.coroutines.launch
import java.util.Currency
import java.util.Locale

object SoulEntryDestination : Destination {
    override val route = "soul_entry"
    override val titleRes = R.string.item_entry_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoulEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: SoulEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(SoulEntryDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        },
        contentColor = glitterGold,
        containerColor = darkBlack,
    ) { innerPadding ->
        SoulEntryBody(
            soulUiState = viewModel.soulUiState,
            onSoulValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveSoul()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun SoulEntryBody(
    soulUiState: SoulUiState,
    onSoulValueChange: (SoulDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        SoulInputForm(
            soulDetails = soulUiState.soulDetails,
            onValueChange = onSoulValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = soulUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonColors(
                containerColor = darkLightTheme,
                contentColor = glitterGold,
                disabledContentColor = glitterGold,
                disabledContainerColor = darkLightTheme
            )
        ) {
            Text(text = stringResource(R.string.save_action), color = glitterGold)
        }
    }
}

@Composable
fun SoulInputForm(
    soulDetails: SoulDetails,
    modifier: Modifier = Modifier,
    onValueChange: (SoulDetails) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            value = soulDetails.name,
            onValueChange = { onValueChange(soulDetails.copy(name = it)) },
            label = { Text(stringResource(R.string.item_name_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = darkLightTheme,
                unfocusedContainerColor = darkLightTheme,
                disabledContainerColor = darkLightTheme,
                focusedTextColor = glitterGold,
                focusedLabelColor = glitterGold,
                focusedBorderColor = glitterGold,
                focusedPlaceholderColor = glitterGold,
                unfocusedTextColor = glitterGold,
                unfocusedPlaceholderColor = glitterGold,
                unfocusedLabelColor = glitterGold,
                unfocusedBorderColor = glitterGold,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
        )
        OutlinedTextField(
            value = soulDetails.price,
            onValueChange = { onValueChange(soulDetails.copy(price = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = { Text(stringResource(R.string.item_price_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = darkLightTheme,
                unfocusedContainerColor = darkLightTheme,
                disabledContainerColor = darkLightTheme,
                focusedTextColor = glitterGold,
                focusedLabelColor = glitterGold,
                focusedBorderColor = glitterGold,
                focusedPlaceholderColor = glitterGold,
                unfocusedTextColor = glitterGold,
                unfocusedPlaceholderColor = glitterGold,
                unfocusedLabelColor = glitterGold,
                unfocusedBorderColor = glitterGold,
            ),
            leadingIcon = { Text(text = Currency.getInstance(Locale.getDefault()).symbol, color = glitterGold) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = soulDetails.quantity,
            onValueChange = { onValueChange(soulDetails.copy(quantity = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(stringResource(R.string.quantity_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = darkLightTheme,
                unfocusedContainerColor = darkLightTheme,
                disabledContainerColor = darkLightTheme,
                focusedTextColor = glitterGold,
                focusedLabelColor = glitterGold,
                focusedBorderColor = glitterGold,
                focusedPlaceholderColor = glitterGold,
                unfocusedTextColor = glitterGold,
                unfocusedPlaceholderColor = glitterGold,
                unfocusedLabelColor = glitterGold,
                unfocusedBorderColor = glitterGold,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        if (enabled) {
            Text(
                text = stringResource(R.string.required_fields),
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium)),
                color = glitterGold
            )
        }
    }
}