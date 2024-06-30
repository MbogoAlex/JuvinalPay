package com.juvinal.pay.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.juvinal.pay.AppViewModelFactory
import com.juvinal.pay.R
import com.juvinal.pay.ui.screens.nav.AppNavigation
import com.juvinal.pay.ui.theme.JuvinalPayTheme
import kotlinx.coroutines.delay

object SplashScreenDestination: AppNavigation{
    override val title: String = "Splash screen"
    override val route: String = "splash-screen"
}
@Composable
fun SplashScreenComposable(
    navigateToWelcomeScreen: () -> Unit,
    navigateToInAppNavScreen: () -> Unit,
    navigateToRegistrationScreen: () -> Unit,
    navigateToMembershipFeePaymentScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: SplashScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        delay(2000)
        if(!uiState.navigated) {
            if(uiState.appLaunched) {
                if(uiState.userDetails.id == null) {
                    navigateToRegistrationScreen()
                } else if(!uiState.userDetails.mem_registered) {
                    navigateToMembershipFeePaymentScreen()
                } else {
                    navigateToInAppNavScreen()
                }
            } else {
                navigateToWelcomeScreen()
            }

            viewModel.changeNavigationStatus()
        }
    }

    Box {
        SplashScreen()
    }
}

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        Image(painter = painterResource(id = R.drawable.juvinalpay_logo), contentDescription = null)
    }

}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    JuvinalPayTheme {
        SplashScreen()
    }
}