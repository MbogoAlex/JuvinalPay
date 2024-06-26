package com.juvinal.pay.ui.screens.inApp.transactions.transactionsHistory

import android.widget.Space
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juvinal.pay.R
import com.juvinal.pay.TransactionsHistoryMenuItem
import com.juvinal.pay.TransactionsHistoryScreen

@Composable
fun TransactionsHistoryScreenComposable(
    navigateToInAppNavigationScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler(onBack = navigateToInAppNavigationScreen)

    val transactionsHistoryMenuItems = listOf(
        TransactionsHistoryMenuItem(
            name = "Deposit",
            screen = TransactionsHistoryScreen.DEPOSIT,
            icon = R.drawable.up_right_arrow
        ),
        TransactionsHistoryMenuItem(
            name = "Loan",
            screen = TransactionsHistoryScreen.LOAN,
            icon = R.drawable.down_right_arrow
        ),
    )

    var currentTab by rememberSaveable {
        mutableStateOf(TransactionsHistoryScreen.DEPOSIT)
    }

    Box(
        modifier = Modifier
            .safeDrawingPadding()
    ) {
        TransactionsHistoryScreen(
            menuItems = transactionsHistoryMenuItems,
            currentTab = currentTab,
            onChangeTab = {
                currentTab = it
            }
        )
    }
}

@Composable
fun TransactionsHistoryScreen(
    menuItems: List<TransactionsHistoryMenuItem> = listOf(),
    currentTab: TransactionsHistoryScreen,
    onChangeTab: (tab: TransactionsHistoryScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                )
                .fillMaxSize()
        ) {
            Text(
                text = "Transactions history",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            when(currentTab) {
                TransactionsHistoryScreen.DEPOSIT -> {
                    DepositHistoryScreenComposable(
                        modifier = Modifier
                            .weight(1f)
                    )
                }
                TransactionsHistoryScreen.LOAN -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    ) {
                        Text(text = "Loans history")
                    }
                }
            }
            TransactionsHistoryScreenBottomBar(
                menuItems = menuItems,
                currentTab = currentTab,
                onChangeTab = onChangeTab
            )
        }
    }
}

@Composable
fun TransactionsHistoryScreenBottomBar(
    menuItems: List<TransactionsHistoryMenuItem>,
    currentTab: TransactionsHistoryScreen,
    onChangeTab: (tab: TransactionsHistoryScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        for(menuItem in menuItems) {
            NavigationBarItem(
                label = {
                    Text(text = menuItem.name)
                },
                selected = menuItem.screen == currentTab,
                onClick = { onChangeTab(menuItem.screen) },
                icon = {
                    Icon(
                        painter = painterResource(id = menuItem.icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
            )
        }
    }
}