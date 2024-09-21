//Vek Histories

@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.veksoul

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.veksoul.ui.navigation.VekSoulAppNavHost
import com.example.veksoul.R.string
import com.example.veksoul.ui.theme.darkBlack
import com.example.veksoul.ui.theme.glitterGold

@Composable
fun VekSoulApp(navController: NavHostController = rememberNavController()) {
    VekSoulAppNavHost(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(string.back_button)
                    )
                }
            }
        },
        colors = TopAppBarColors(
            containerColor = darkBlack,
            titleContentColor = glitterGold,
            navigationIconContentColor = glitterGold,
            actionIconContentColor = glitterGold,
            scrolledContainerColor = glitterGold
        )
    )
}