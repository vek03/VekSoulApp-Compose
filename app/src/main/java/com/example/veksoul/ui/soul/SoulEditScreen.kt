//Vek Histories

package com.example.veksoul.ui.soul

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.veksoul.R
import com.example.veksoul.TopAppBar
import com.example.veksoul.ui.AppViewModelProvider
import com.example.veksoul.ui.navigation.Destination
import com.example.veksoul.ui.theme.darkBlack
import com.example.veksoul.ui.theme.glitterGold
import kotlinx.coroutines.launch

object SoulEditDestination : Destination {
    override val route = "soul_edit"
    override val titleRes = R.string.edit_item_title
    const val soulIdArg = "soulId"
    val routeWithArgs = "$route/{$soulIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoulEditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SoulEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(SoulEditDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        contentColor = glitterGold,
        containerColor = darkBlack,
        modifier = modifier
    ) { innerPadding ->
        SoulEntryBody(
            soulUiState = viewModel.soulUiState,
            onSoulValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateItem()
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
