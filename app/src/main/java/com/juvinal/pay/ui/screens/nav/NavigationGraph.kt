package com.juvinal.pay.ui.screens.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.juvinal.pay.ui.screens.SplashScreenComposable
import com.juvinal.pay.ui.screens.SplashScreenDestination

@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = SplashScreenDestination.route
    ) {
        composable(SplashScreenDestination.route) {
            SplashScreenComposable()
        }
    }
}