package com.juvinal.pay.ui.screens.inApp

import android.app.Activity
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juvinal.pay.DashboardMenuItem
import com.juvinal.pay.HomeScreenSideBarMenuScreen
import com.juvinal.pay.R
import com.juvinal.pay.ui.screens.inApp.dashboard.HomeScreenComposable
import com.juvinal.pay.ui.screens.inApp.dashboard.profile.ProfileScreenComposable
import com.juvinal.pay.ui.screens.nav.AppNavigation
import com.juvinal.pay.ui.theme.JuvinalPayTheme
import kotlinx.coroutines.launch

object InAppNavScreenDestination: AppNavigation {
    override val title: String = "InAppNavScreen screen"
    override val route = "in-app-nav-screen"
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InAppNavScreenComposable(
    navigateToPersonalDetailsScreen: () -> Unit,
    navigateToChangePasswordScreen: () -> Unit,
    navigateToPrivacyPolicyScreen: () -> Unit,
    navigateToInAppNavigationScreen: () -> Unit,
    navigateToLoginScreenWithArgs: (documentNo: String, password: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val activity = (LocalContext.current as? Activity)
    BackHandler(onBack = {activity?.finish()})

    val dashboardMenuItems = listOf(
        DashboardMenuItem(
            name = "Home",
            screen = HomeScreenSideBarMenuScreen.HOME
        ),
        DashboardMenuItem(
            name = "Profile",
            screen = HomeScreenSideBarMenuScreen.PROFILE
        ),
    )

    var currentScreen by rememberSaveable {
        mutableStateOf(HomeScreenSideBarMenuScreen.HOME)
    }

    Box(modifier = Modifier
        .safeDrawingPadding()
    ) {
        InAppNavScreen(
            dashboardMenuItems = dashboardMenuItems,
            currentScreen = currentScreen,
            onChangeScreen = {
                currentScreen = it
            },
            navigateToPersonalDetailsScreen = navigateToPersonalDetailsScreen,
            navigateToChangePasswordScreen = navigateToChangePasswordScreen,
            navigateToInAppNavigationScreen = navigateToInAppNavigationScreen,
            navigateToPrivacyPolicyScreen = navigateToPrivacyPolicyScreen,
            navigateToLoginScreenWithArgs = navigateToLoginScreenWithArgs
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InAppNavScreen(
    dashboardMenuItems: List<DashboardMenuItem>,
    currentScreen: HomeScreenSideBarMenuScreen,
    onChangeScreen: (screen: HomeScreenSideBarMenuScreen) -> Unit,
    navigateToPersonalDetailsScreen: () -> Unit,
    navigateToChangePasswordScreen: () -> Unit,
    navigateToPrivacyPolicyScreen: () -> Unit,
    navigateToInAppNavigationScreen: () -> Unit,
    navigateToLoginScreenWithArgs: (documentNo: String, password: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()



    var showLoansItems by remember {
        mutableStateOf(false)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(20.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.juvinal_pay_logo_light),
                            contentDescription = null,
                            modifier = Modifier
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.dashboard),
                            contentDescription = "Dashboard"
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "Dashboard",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    for(menuItem in dashboardMenuItems) {
                        NavigationDrawerItem(
                            label = {
                                Row {
                                    Text(text = "-")
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Text(text = menuItem.name)
                                }
                            },
                            selected = menuItem.screen == currentScreen,
                            onClick = {
                                scope.launch {
                                    drawerState.close()
                                    onChangeScreen(menuItem.screen)
                                }
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.receipt),
                            contentDescription = "Transactions",
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "Transactions",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.loan),
                            contentDescription = "Loans"
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "Loans",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }

            }
        },
    ) {
        Column(
            modifier = Modifier
//                .safeDrawingPadding()
//                .padding(
//                    top = 16.dp
//                )
//                .fillMaxSize()
        ) {
//            Spacer(modifier = Modifier.height(30.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = {
                    scope.launch {
                        if(drawerState.isClosed) drawerState.open() else drawerState.close()
                    }
                }) {
                    Icon(
                        tint = Color.Gray,
                        painter = painterResource(id = R.drawable.menu),
                        contentDescription = "Menu"
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        tint = Color.Gray,
                        painter = painterResource(id = R.drawable.user_account),
                        contentDescription = "Account"
                    )
                }
            }
            when(currentScreen) {
                HomeScreenSideBarMenuScreen.HOME -> {
                    HomeScreenComposable()
                }
                HomeScreenSideBarMenuScreen.PROFILE -> {
                    ProfileScreenComposable(
                        navigateToPersonalDetailsScreen = navigateToPersonalDetailsScreen,
                        navigateToChangePasswordScreen = navigateToChangePasswordScreen,
                        navigateToPrivacyPolicyScreen = navigateToPrivacyPolicyScreen,
                        navigateToInAppNavigationScreen = navigateToInAppNavigationScreen,
                        navigateToLoginScreenWithArgs = navigateToLoginScreenWithArgs
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun NavScreenPreview() {
    val dashboardMenuItems = listOf(
        DashboardMenuItem(
            name = "Home",
            screen = HomeScreenSideBarMenuScreen.HOME
        ),
        DashboardMenuItem(
            name = "Profile",
            screen = HomeScreenSideBarMenuScreen.PROFILE
        ),
    )
    JuvinalPayTheme {
        InAppNavScreen(
            dashboardMenuItems = dashboardMenuItems,
            currentScreen = HomeScreenSideBarMenuScreen.HOME,
            onChangeScreen = {},
            navigateToInAppNavigationScreen = {},
            navigateToPersonalDetailsScreen = {},
            navigateToChangePasswordScreen = {},
            navigateToPrivacyPolicyScreen = {},
            navigateToLoginScreenWithArgs = {documentNo, password ->  }
        )
    }
}