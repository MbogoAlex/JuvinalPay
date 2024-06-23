package com.juvinal.pay.ui.screens.inApp.transactions

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun RequestLoanScreenComposable(
    navigateToInAppNavigationScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler(onBack = navigateToInAppNavigationScreen)
   Box(
       modifier = Modifier
           .safeDrawingPadding()
   ) {
       RequestLoanScreen()
   }
}

@Composable
fun RequestLoanScreen(
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = "Request loan")
    }
}