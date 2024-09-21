//Vek Histories

package com.example.veksoul.ui.soul

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.veksoul.R
import com.example.veksoul.TopAppBar
import com.example.veksoul.data.Soul
import com.example.veksoul.ui.AppViewModelProvider
import com.example.veksoul.ui.navigation.Destination
import com.example.veksoul.ui.theme.darkBlack
import com.example.veksoul.ui.theme.darkLightTheme
import com.example.veksoul.ui.theme.darkTheme
import com.example.veksoul.ui.theme.glitterGold
import kotlinx.coroutines.launch

object SoulDetailsDestination : Destination {
    override val route = "soul_details"
    override val titleRes = R.string.item_detail_title
    const val soulIdArg = "soulId"
    val routeWithArgs = "$route/{$soulIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoulDetailsScreen(
    navigateToEditSoul: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SoulDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(SoulDetailsDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        contentColor = glitterGold,
        containerColor = darkBlack,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToEditSoul(uiState.value.soulDetails.id) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
                contentColor = glitterGold,
                containerColor = darkLightTheme
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_item_title),
                    tint = glitterGold
                )
            }
        }, modifier = modifier
    ) { innerPadding ->
        SoulDetailsBody(
            soulDetailsUiState = uiState.value,
            onSellSoul = { viewModel.reduceQuantityByOne() },
            onDelete = {
                coroutineScope.launch {
                    viewModel.deleteSoul()
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
        )
    }
}

@Composable
private fun SoulDetailsBody(
    soulDetailsUiState: SoulDetailsUiState,
    onSellSoul: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

        SoulDetails(
            soul = soulDetailsUiState.soulDetails.toSoul(),
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSellSoul,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            enabled = true,
            colors = ButtonColors(
                containerColor = darkTheme,
                contentColor = glitterGold,
                disabledContentColor = glitterGold,
                disabledContainerColor = darkTheme
            )
        ) {
            Text(text = stringResource(R.string.sell), color = glitterGold)
        }
        OutlinedButton(
            onClick = { deleteConfirmationRequired = true },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(width = 1.dp, color = glitterGold),
            colors = ButtonColors(
                containerColor = darkTheme,
                contentColor = glitterGold,
                disabledContentColor = glitterGold,
                disabledContainerColor = darkTheme
            )
        ) {
            Text(text = stringResource(R.string.delete), color = glitterGold)
        }
        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDelete()
                },
                onDeleteCancel = { deleteConfirmationRequired = false },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}

@Composable
fun SoulDetails(
    soul: Soul, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = darkTheme,
            contentColor = glitterGold
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.padding_medium)
            )
        ) {
            SoulDetailsRow(
                labelResID = R.string.item,
                soulDetail = soul.name,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            SoulDetailsRow(
                labelResID = R.string.quantity_in_stock,
                soulDetail = soul.quantity.toString(),
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            SoulDetailsRow(
                labelResID = R.string.price,
                soulDetail = soul.formatedPrice(),
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
        }
    }
}

@Composable
private fun SoulDetailsRow(
    @StringRes labelResID: Int, soulDetail: String, modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(stringResource(labelResID))
        Spacer(modifier = Modifier.weight(1f))
        Text(text = soulDetail, fontWeight = FontWeight.Bold, color = glitterGold)
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.delete_question)) },
        modifier = modifier,
        containerColor = darkTheme,
        iconContentColor = glitterGold,
        titleContentColor = glitterGold,
        textContentColor = glitterGold,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = stringResource(R.string.no), color = glitterGold)
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = stringResource(R.string.yes), color = glitterGold)
            }
        })
}