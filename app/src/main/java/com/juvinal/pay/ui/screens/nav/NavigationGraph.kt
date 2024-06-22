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
import com.juvinal.pay.ui.screens.inApp.dashboard.profile.ChangePasswordScreenComposable
import com.juvinal.pay.ui.screens.inApp.dashboard.profile.ChangePasswordScreenDestination
import com.juvinal.pay.ui.screens.inApp.dashboard.profile.PersonalDetailsScreenComposable
import com.juvinal.pay.ui.screens.inApp.dashboard.profile.PersonalDetailsScreenDestination
import com.juvinal.pay.ui.screens.inApp.dashboard.profile.PrivacyPolicyScreenComposable
import com.juvinal.pay.ui.screens.inApp.dashboard.profile.PrivacyPolicyScreenDestination

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
        // InAppNavScreen with args
        composable(InAppNavScreenDestination.route) {
            InAppNavScreenComposable(
                navigateToPersonalDetailsScreen = {
                    navController.navigate(PersonalDetailsScreenDestination.route)
                },
                navigateToChangePasswordScreen = {
                    navController.navigate(ChangePasswordScreenDestination.route)
                },
                navigateToPrivacyPolicyScreen = {
                    navController.navigate(PrivacyPolicyScreenDestination.route)
                },
                navigateToInAppNavigationScreen = {
                    navController.navigate(InAppNavScreenDestination.route)
                },
                navigateToLoginScreenWithArgs = {documentNo, password ->
                    navController.navigate("${LoginScreenDestination.route}/${documentNo}/${password}")
                },
                navigateToInAppNavigationScreenWithArgs = {
                    navController.navigate("${InAppNavScreenDestination.route}/${it}")
                }
            )
        }
        // InAppNavScreen with args
        composable(
            InAppNavScreenDestination.routeWithArgs,
            arguments = listOf(
                navArgument(InAppNavScreenDestination.childScreen) {
                    type = NavType.StringType
                }
            )
        ) {
            InAppNavScreenComposable(
                navigateToPersonalDetailsScreen = {
                    navController.navigate(PersonalDetailsScreenDestination.route)
                },
                navigateToChangePasswordScreen = {
                    navController.navigate(ChangePasswordScreenDestination.route)
                },
                navigateToPrivacyPolicyScreen = {
                    navController.navigate(PrivacyPolicyScreenDestination.route)
                },
                navigateToInAppNavigationScreen = {
                    navController.navigate(InAppNavScreenDestination.route)
                },
                navigateToLoginScreenWithArgs = {documentNo, password ->
                    navController.navigate("${LoginScreenDestination.route}/${documentNo}/${password}")
                },
                navigateToInAppNavigationScreenWithArgs = {
                    navController.navigate("${InAppNavScreenDestination.route}/${it}")
                }
            )
        }
        // RegistrationScreen
        composable(RegistrationScreenDestination.route) {
            RegistrationScreenComposable(
                navigateToLoginScreen = { 
                    navController.navigate(LoginScreenDestination.route)
                },
                navigateToLoginScreenWithArgs = {documentNo, password ->
                    navController.navigate("${LoginScreenDestination.route}/${documentNo}/${password}")
                },
                navigateToMembershipFeeScreen = {
                    navController.navigate(MembershipFeeScreenDestination.route)
                }
            )
        }
        // MembershipFeeScreen
        composable(MembershipFeeScreenDestination.route) {
            MembershipFeeScreenComposable(
                navigateToInAppNavigationScreen = {
                    navController.navigate(InAppNavScreenDestination.route)
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
                navigateToInAppNavigationScreen = {
                    navController.navigate(InAppNavScreenDestination.route)
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
                navigateToInAppNavigationScreen = {
                    navController.navigate(InAppNavScreenDestination.route)
                }
            )
        }
        // PersonalDetailsScreen
        composable(PersonalDetailsScreenDestination.route) {
            PersonalDetailsScreenComposable(
                navigateToPreviousScreen = {
                    navController.navigateUp()
                }
            )
        }
        // ChangePasswordScreen
        composable(ChangePasswordScreenDestination.route) {
            ChangePasswordScreenComposable(
                navigateToPreviousScreen = {
                    navController.navigateUp()
                }
            )
        }
        // PrivacyPolicyScreen
        composable(PrivacyPolicyScreenDestination.route) {
            PrivacyPolicyScreenComposable(
                navigateToPreviousScreen = {
                    navController.navigateUp()
                }
            )
        }
    }
}