package com.juvinal.pay.ui.screens.nav

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.juvinal.pay.ui.screens.SplashScreenComposable
import com.juvinal.pay.ui.screens.SplashScreenDestination
import com.juvinal.pay.ui.screens.authentication.LoginScreenComposable
import com.juvinal.pay.ui.screens.authentication.LoginScreenDestination
import com.juvinal.pay.ui.screens.authentication.MembershipFeeScreenComposable
import com.juvinal.pay.ui.screens.authentication.MembershipFeeScreenDestination
import com.juvinal.pay.ui.screens.authentication.RegistrationScreenComposable
import com.juvinal.pay.ui.screens.authentication.RegistrationScreenDestination
import com.juvinal.pay.ui.screens.inApp.InAppNavScreenComposable
import com.juvinal.pay.ui.screens.inApp.InAppNavScreenDestination
import com.juvinal.pay.ui.screens.inApp.dashboard.HomeScreenComposable
import com.juvinal.pay.ui.screens.inApp.dashboard.HomeScreenDestination

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = SplashScreenDestination.route
    ) {
        // SplashScreen
        composable(SplashScreenDestination.route) {
            SplashScreenComposable(
                navigateToInAppNavScreen = {
                    navController.navigate(InAppNavScreenDestination.route)
                },
                navigateToRegistrationScreen = {
                    navController.navigate(RegistrationScreenDestination.route)
                },
                navigateToMembershipFeePaymentScreen = { 
                    navController.navigate(MembershipFeeScreenDestination.route)
                }
            )
        }
        // HomeScreen
        composable(InAppNavScreenDestination.route) {
            InAppNavScreenComposable()
        }
        // RegistrationScreen
        composable(RegistrationScreenDestination.route) {
            RegistrationScreenComposable(
                navigateToLoginScreen = { 
                    navController.navigate(LoginScreenDestination.route)
                },
                navigateToLoginScreenWithArgs = {documentNo, password ->
                    navController.navigate("${LoginScreenDestination.route}/${documentNo}/${password}")
                }
            )
        }
        // MembershipFeeScreen
        composable(MembershipFeeScreenDestination.route) {
            MembershipFeeScreenComposable(
                navigateToHomeScreen = {
                    navController.navigate(HomeScreenDestination.route)
                }
            )
        }
        // LoginScreen
        composable(LoginScreenDestination.route) {
            LoginScreenComposable(
                navigateToRegistrationScreen = {
                    navController.navigate(RegistrationScreenDestination.route)
                },
                navigateToMembershipFeePaymentScreen = {
                    navController.navigate(MembershipFeeScreenDestination.route)
                },
                navigateToHomeScreen = {
                    navController.navigate(HomeScreenDestination.route)
                }
            )
        }
        // LoginScreen with args
        composable(
            LoginScreenDestination.routeWithArgs,
            arguments = listOf(
                navArgument(LoginScreenDestination.documentNo) {
                    type = NavType.StringType
                },
                navArgument(LoginScreenDestination.password) {
                    type = NavType.StringType
                }
            )
        ) {
            LoginScreenComposable(
                navigateToRegistrationScreen = {
                    navController.navigate(RegistrationScreenDestination.route)
                },
                navigateToMembershipFeePaymentScreen = {
                    navController.navigate(MembershipFeeScreenDestination.route)
                },
                navigateToHomeScreen = {
                    navController.navigate(HomeScreenDestination.route)
                }
            )
        }
    }
}