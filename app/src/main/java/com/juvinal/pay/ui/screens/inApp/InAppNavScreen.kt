package com.juvinal.pay.ui.screens.inApp

import android.app.Activity
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.juvinal.pay.AppViewModelFactory
import com.juvinal.pay.DashboardMenuItem
import com.juvinal.pay.HomeScreenSideBarMenuScreen
import com.juvinal.pay.R
import com.juvinal.pay.resusableFunctions.formatMoneyValue
import com.juvinal.pay.reusableComposables.LogoutDialog
import com.juvinal.pay.ui.screens.inApp.dashboard.HomeScreenComposable
import com.juvinal.pay.ui.screens.inApp.dashboard.profile.ProfileScreenComposable
import com.juvinal.pay.ui.screens.inApp.transactions.deposit.DepositMoneyScreenComposable
import com.juvinal.pay.ui.screens.inApp.transactions.loan.RequestLoanScreenComposable
import com.juvinal.pay.ui.screens.inApp.transactions.loan.UnpaidLoansScreenComposable
import com.juvinal.pay.ui.screens.inApp.transactions.loan.LoanHistoryScreenComposable
import com.juvinal.pay.ui.screens.inApp.transactions.transactionsHistory.TransactionsHistoryScreenComposable
import com.juvinal.pay.ui.screens.nav.AppNavigation
import com.juvinal.pay.ui.theme.JuvinalPayTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object InAppNavScreenDestination: AppNavigation {
    override val title: String = "InAppNavScreen screen"
    override val route = "in-app-nav-screen"
    val childScreen: String = "child-screen"
    val routeWithArgs = "$route/{$childScreen}"
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InAppNavScreenComposable(
    navigateToPersonalDetailsScreen: () -> Unit,
    navigateToChangePasswordScreen: () -> Unit,
    navigateToPrivacyPolicyScreen: () -> Unit,
    navigateToInAppNavigationScreen: () -> Unit,
    navigateToLoginScreenWithArgs: (documentNo: String, password: String) -> Unit,
    navigateToInAppNavigationScreenWithArgs: (childScreen: String) -> Unit,
    navigateToLoanScheduleScreen: (loanId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activity = (LocalContext.current as? Activity)
    BackHandler(onBack = {activity?.finish()})

    val viewModel: InAppNavScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()


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

    val transactionsMenuItems = listOf(
        DashboardMenuItem(
            name = "Deposit",
            screen = HomeScreenSideBarMenuScreen.DEPOSIT
        ),
        DashboardMenuItem(
            name = "Transactions history",
            screen = HomeScreenSideBarMenuScreen.TRANSACTIONS_HISTORY
        ),
    )

    val loansMenuItems = listOf(
        DashboardMenuItem(
            name = "Request loan",
            screen = HomeScreenSideBarMenuScreen.LOAN
        ),
        DashboardMenuItem(
            name = "Loan repayment",
            screen = HomeScreenSideBarMenuScreen.UNPAID_LOAN
        ),
        DashboardMenuItem(
            name = "Loans history",
            screen = HomeScreenSideBarMenuScreen.LOAN_HISTORY
        ),
    )

    var showTopPopup by remember {
        mutableStateOf(false)
    }

    var currentScreen by rememberSaveable {
        mutableStateOf(HomeScreenSideBarMenuScreen.HOME)
    }

    val scope = rememberCoroutineScope()

    var loggingOut by rememberSaveable {
        mutableStateOf(false)
    }

    var showLogoutDialog by remember {
        mutableStateOf(false)
    }

    if(showLogoutDialog) {
        LogoutDialog(
            loggingOut = loggingOut,
            onConfirm = {
//                showLogoutDialog = !showLogoutDialog
                scope.launch {
                    loggingOut = true
                    delay(2000)
                    viewModel.logout()
                    navigateToLoginScreenWithArgs(uiState.userDetails.user!!.document_no, uiState.userDetails.user!!.password)
                    loggingOut = false
                    Toast.makeText(context, "You are logged out", Toast.LENGTH_SHORT).show()
                }
            },
            onDismiss = {
                if(!loggingOut) {
                    showLogoutDialog = !showLogoutDialog
                }

            }
        )
    }

    when (uiState.childScreen) {
        "deposit-screen" -> {
            currentScreen = HomeScreenSideBarMenuScreen.DEPOSIT
            viewModel.resetChildScreen()
        }
        "loan-request-screen" -> {
            currentScreen = HomeScreenSideBarMenuScreen.LOAN
            viewModel.resetChildScreen()
        }
        "unpaid_loan_screen" -> {
            currentScreen = HomeScreenSideBarMenuScreen.UNPAID_LOAN
            viewModel.resetChildScreen()
        }
    }

    Box(modifier = Modifier
        .safeDrawingPadding()
    ) {

        InAppNavScreen(
            username = uiState.userDetails.user!!.fname,
            accountSavings = uiState.accountSavings,
            showTopPopup = showTopPopup,
            dashboardMenuItems = dashboardMenuItems,
            transactionsMenuItems = transactionsMenuItems,
            loansMenuItems = loansMenuItems,
            currentScreen = currentScreen,
            onChangeScreen = {
                currentScreen = it
            },
            onDismissRequest = {
                showTopPopup = !showTopPopup
            },
            onShowProfilePopup = {
                showTopPopup = !showTopPopup
            },
            navigateToPersonalDetailsScreen = navigateToPersonalDetailsScreen,
            navigateToChangePasswordScreen = navigateToChangePasswordScreen,
            navigateToInAppNavigationScreen = navigateToInAppNavigationScreen,
            navigateToPrivacyPolicyScreen = navigateToPrivacyPolicyScreen,
            navigateToLoginScreenWithArgs = navigateToLoginScreenWithArgs,
            navigateToInAppNavigationScreenWithArgs = navigateToInAppNavigationScreenWithArgs,
            navigateToProfileScreen = {
                currentScreen = HomeScreenSideBarMenuScreen.PROFILE
                showTopPopup = !showTopPopup
            },
            onLogout = {
                showLogoutDialog = !showLogoutDialog
                showTopPopup = !showTopPopup
            },
            navigateToLoanScheduleScreen = navigateToLoanScheduleScreen,
            navigateToDepositScreen = {
                currentScreen = HomeScreenSideBarMenuScreen.DEPOSIT
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InAppNavScreen(
    username: String,
    accountSavings: Double,
    showTopPopup: Boolean,
    dashboardMenuItems: List<DashboardMenuItem>,
    transactionsMenuItems: List<DashboardMenuItem>,
    loansMenuItems: List<DashboardMenuItem>,
    currentScreen: HomeScreenSideBarMenuScreen,
    onChangeScreen: (screen: HomeScreenSideBarMenuScreen) -> Unit,
    onDismissRequest: () -> Unit,
    onShowProfilePopup: () -> Unit,
    navigateToPersonalDetailsScreen: () -> Unit,
    navigateToChangePasswordScreen: () -> Unit,
    navigateToPrivacyPolicyScreen: () -> Unit,
    navigateToInAppNavigationScreen: () -> Unit,
    navigateToLoginScreenWithArgs: (documentNo: String, password: String) -> Unit,
    navigateToInAppNavigationScreenWithArgs: (childScreen: String) -> Unit,
    navigateToProfileScreen: () -> Unit,
    navigateToLoanScheduleScreen: (loanId: Int) -> Unit,
    navigateToDepositScreen: () -> Unit,
    onLogout: () -> Unit,
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
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                    ) {
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
                                painter = painterResource(id = R.drawable.transactions_2),
                                contentDescription = "Transactions",
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "Transactions",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        for(menuItem in transactionsMenuItems) {
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
                                painter = painterResource(id = R.drawable.loan_2),
                                contentDescription = "Loans"
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "Loans",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        for(menuItem in loansMenuItems) {
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
                modifier = Modifier
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
                IconButton(onClick = onShowProfilePopup) {
                    Icon(
                        tint = Color.Gray,
                        painter = painterResource(id = R.drawable.user_account),
                        contentDescription = "Account"
                    )
                }
            }
            if(showTopPopup) {
                Card {
                    Popup(
                        alignment = Alignment.TopEnd,
                        properties = PopupProperties(
                            excludeFromSystemGesture = true
                        ),
                        onDismissRequest = onDismissRequest
                    ) {
                        Card(
//                            colors = CardDefaults.cardColors(
//                                containerColor = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary
//                            ),
                            shape = RoundedCornerShape(0.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(20.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Welcome $username",
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            navigateToProfileScreen()
                                        }
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.person),
                                        contentDescription = null,
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Text(
                                        text = "Profile",
                                        modifier = Modifier
                                            .padding(10.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Divider()
                                Spacer(modifier = Modifier.height(10.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
//                                        .clickable { }
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.wallet),
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Text(
                                        text = "Account Savings:",
                                    )
                                    Text(
                                        text = formatMoneyValue(accountSavings),
                                        modifier = Modifier
                                            .padding(10.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            onLogout()
                                        }
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.logout),
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Text(
                                        text = "Logout",
                                        modifier = Modifier
                                            .padding(10.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }


            when(currentScreen) {
                HomeScreenSideBarMenuScreen.HOME -> {
                    HomeScreenComposable(
                        navigateToDepositScreen = navigateToDepositScreen
                    )
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
                HomeScreenSideBarMenuScreen.DEPOSIT -> {
                    DepositMoneyScreenComposable(
                        navigateToInAppNavigationScreenWithArgs = navigateToInAppNavigationScreenWithArgs,
                        navigateToInAppNavigationScreen = navigateToInAppNavigationScreen
                    )
                }
                HomeScreenSideBarMenuScreen.LOAN -> {
                    RequestLoanScreenComposable(
                        navigateToInAppNavigationScreen = navigateToInAppNavigationScreen,
                        navigateToInAppNavigationScreenWithArgs = navigateToInAppNavigationScreenWithArgs
                    )
                }
                HomeScreenSideBarMenuScreen.UNPAID_LOAN -> {
                    UnpaidLoansScreenComposable(
                        navigateToInAppNavigationScreen = navigateToInAppNavigationScreen,
                        navigateToLoanScheduleScreen = navigateToLoanScheduleScreen
                    )
                }
                HomeScreenSideBarMenuScreen.TRANSACTIONS_HISTORY -> {
                    TransactionsHistoryScreenComposable(
                        navigateToInAppNavigationScreen = navigateToInAppNavigationScreen,
                        navigateToLoanScheduleScreen = navigateToLoanScheduleScreen
                    )
                }

                HomeScreenSideBarMenuScreen.LOAN_HISTORY -> {
                    LoanHistoryScreenComposable(
                        navigateToLoanScheduleScreen = navigateToLoanScheduleScreen,
                        navigateToInApNavScreen = navigateToInAppNavigationScreen
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
    val transactionsMenuItems = listOf(
        DashboardMenuItem(
            name = "Deposit",
            screen = HomeScreenSideBarMenuScreen.DEPOSIT
        ),
        DashboardMenuItem(
            name = "Request loan",
            screen = HomeScreenSideBarMenuScreen.LOAN
        ),
        DashboardMenuItem(
            name = "Transactions history",
            screen = HomeScreenSideBarMenuScreen.TRANSACTIONS_HISTORY
        ),
    )
    val loansMenuItems = listOf(
        DashboardMenuItem(
            name = "Request loan",
            screen = HomeScreenSideBarMenuScreen.LOAN
        ),
        DashboardMenuItem(
            name = "Loan repayment",
            screen = HomeScreenSideBarMenuScreen.UNPAID_LOAN
        ),
        DashboardMenuItem(
            name = "Loans history",
            screen = HomeScreenSideBarMenuScreen.LOAN_HISTORY
        ),
    )
    JuvinalPayTheme {
        JuvinalPayTheme {
            InAppNavScreen(
                username = "Alex",
                accountSavings = 0.0,
                showTopPopup = false,
                dashboardMenuItems = dashboardMenuItems,
                transactionsMenuItems = transactionsMenuItems,
                loansMenuItems = loansMenuItems,
                currentScreen = HomeScreenSideBarMenuScreen.HOME,
                onChangeScreen = {},
                onDismissRequest = {},
                onShowProfilePopup = {},
                navigateToInAppNavigationScreen = {},
                navigateToPersonalDetailsScreen = {},
                navigateToChangePasswordScreen = {},
                navigateToPrivacyPolicyScreen = {},
                navigateToLoginScreenWithArgs = { documentNo, password -> },
                navigateToInAppNavigationScreenWithArgs = {},
                navigateToProfileScreen = {},
                onLogout = {},
                navigateToLoanScheduleScreen = {},
                navigateToDepositScreen = {}
            )
        }
    }
}