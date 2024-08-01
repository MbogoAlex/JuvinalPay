package com.juvinal.pay.ui.screens.inApp.transactions.loan

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.juvinal.pay.AppViewModelFactory
import com.juvinal.pay.loanScheduleList
import com.juvinal.pay.model.LoanScheduleDT
import com.juvinal.pay.resusableFunctions.formatMoneyValue
import com.juvinal.pay.ui.screens.nav.AppNavigation
import com.juvinal.pay.ui.theme.JuvinalPayTheme

object LoanScheduleScreenDestination: AppNavigation {
    override val title: String = "Loan schedule screen"
    override val route: String = "loan-schedule-screen"
    val loanId: String = "loanId"
    val routeWithArgs = "$route/{$loanId}"

}
@Composable
fun LoanScheduleScreenComposable(
    navigateToPreviousScreen: () -> Unit,
    navigateToLoanPaymentScreen: (loanId: String, memNo: String, schedulePayDate: String, scheduleTotal: String, scheduleTotalPaid: String, scheduleTotalBalance: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: LoanScheduleScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier
            .safeDrawingPadding()
    ) {
        LoanScheduleScreen(
            loanScheduleList = uiState.loanScheduleList,
            unpaidScheduleList = uiState.unpaidSchedule,
            paidScheduleList = uiState.paidSchedule,
            navigateToPreviousScreen = navigateToPreviousScreen,
            navigateToLoanPaymentScreen = {schedulePayDate, scheduleTotal, scheduleTotalPaid, scheduleTotalBalance ->
                navigateToLoanPaymentScreen(uiState.loanId, uiState.userDetails.member.mem_no!!, schedulePayDate, scheduleTotal, scheduleTotalPaid, scheduleTotalBalance)
            }
        )

    }
}

@Composable
fun LoanScheduleScreen(
    loanScheduleList: List<LoanScheduleDT>,
    unpaidScheduleList: List<LoanScheduleDT>,
    paidScheduleList: List<LoanScheduleDT>,
    navigateToPreviousScreen: () -> Unit,
    navigateToLoanPaymentScreen: (schedulePayDate: String, scheduleTotal: String, scheduleTotalPaid: String, scheduleTotalBalance: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = navigateToPreviousScreen) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Previous screen"
                )
            }
            Text(
                text = "Loan schedule",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn {
            items(loanScheduleList) {
                LoanScheduleCell(
                    loanScheduleDT = it,
                    payButtonEnabled = it == unpaidScheduleList[0],
                    navigateToLoanPaymentScreen = navigateToLoanPaymentScreen
                )
            }
        }
    }
}

@Composable
fun LoanScheduleCell(
    loanScheduleDT: LoanScheduleDT,
    payButtonEnabled: Boolean,
    navigateToLoanPaymentScreen: (schedulePayDate: String, scheduleTotal: String, scheduleTotalPaid: String, scheduleTotalBalance: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column {
            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Row {
                    Text(
                        text = "Pay date:",
                        color = Color(0xFFaaacb7),
                        modifier = Modifier
                    )
                    Text(text = loanScheduleDT.schedule_pay_date)
                    Spacer(modifier = Modifier.weight(1f))
                    if(loanScheduleDT.schedule_total_balance.toDouble() != 0.0) {
                        Text(
                            text = "UNPAID",
                            fontWeight = FontWeight.Bold,
                        )
                    } else {
                        Text(
                            text = "PAID",
                            fontWeight = FontWeight.Bold,
                        )
                    }

                }
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Text(
                        text = "Total paid:",
                        color = Color(0xFFaaacb7),
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = formatMoneyValue(loanScheduleDT.schedule_total_paid.toDouble()),
                        color = Color(0xFFaaacb7),
                        modifier = Modifier
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Text(
                        text = "Total balance:",
                        color = Color(0xFFaaacb7),
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = formatMoneyValue(loanScheduleDT.schedule_total_balance.toDouble()),
                        color = Color(0xFFaaacb7),
                        modifier = Modifier
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Text(
                        text = "Schedule status:",
                        color = Color(0xFFaaacb7),
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = loanScheduleDT.schedule_status.toString(),
                        color = Color(0xFFaaacb7),
                        modifier = Modifier
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    enabled = payButtonEnabled,
                    onClick = {
                              navigateToLoanPaymentScreen(
                                  loanScheduleDT.schedule_pay_date,
                                  loanScheduleDT.schedule_total,
                                  loanScheduleDT.schedule_total_paid,
                                  loanScheduleDT.schedule_total_balance,
                              )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = "Pay")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoanScheduleScreenPreview() {
    JuvinalPayTheme {
        LoanScheduleScreen(
            navigateToPreviousScreen = {},
            loanScheduleList = loanScheduleList,
            unpaidScheduleList = loanScheduleList,
            paidScheduleList = loanScheduleList,
            navigateToLoanPaymentScreen = {schedulePayDate, scheduleTotal, scheduleTotalPaid, scheduleTotalBalance ->}
        )
    }
}