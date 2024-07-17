package com.juvinal.pay.ui.screens.inApp.transactions.loan

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.juvinal.pay.AppViewModelFactory
import com.juvinal.pay.loanHistory
import com.juvinal.pay.model.LoanHistoryDt
import com.juvinal.pay.resusableFunctions.formatDate
import com.juvinal.pay.resusableFunctions.formatMoneyValue
import com.juvinal.pay.ui.theme.JuvinalPayTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UnpaidLoansScreenComposable(
    navigateToInAppNavigationScreen: () -> Unit,
    navigateToLoanScheduleScreen: (loanId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler(onBack = navigateToInAppNavigationScreen)
    val viewModel: UnpaidLoansScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()
    Box(modifier = modifier) {
        UnpaidLoansScreen(
            unpaidLoans = uiState.unpaidLoans,
            navigateToLoanScheduleScreen = navigateToLoanScheduleScreen
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UnpaidLoansScreen(
    unpaidLoans: List<LoanHistoryDt>,
    navigateToLoanScheduleScreen: (loanId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Loan repayment",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(
                    start = 20.dp
                )
        )
        Spacer(modifier = Modifier.height(20.dp))
        if(unpaidLoans.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "No unpaid loan",
                )
            }
        } else {
            LazyColumn {
                items(unpaidLoans) {
                    LoanHistoryCell(
                        loanHistoryDt = it,
                        navigateToLoanScheduleScreen = navigateToLoanScheduleScreen
                    )
                }
            }
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun LoanHistoryCell(
    loanHistoryDt: LoanHistoryDt,
    navigateToLoanScheduleScreen: (loanId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var expandItem by remember {
        mutableStateOf(true)
    }
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = modifier
            .padding(
                start = 20.dp,
                end = 20.dp,
                bottom = 10.dp
            )
            .fillMaxWidth()
            .clickable {
                expandItem = !expandItem
            }
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
                .padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column {
                    Row {
                        Text(
                            text = "${loanHistoryDt.loan_status},",
                            color = Color(0xFFaaacb7),
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "REF: ",
                            color = Color(0xFFaaacb7),
                        )
                        Text(
                            text = loanHistoryDt.loan_ref,
                            color = Color(0xFFaaacb7),
//                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "UNPAID",
//                            color = Color(0xFFaaacb7),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = loanHistoryDt.loanStatusCode.desc,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row {
                        Text(
                            text = "Requested:",
                            color = Color(0xFFaaacb7),
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = formatMoneyValue(loanHistoryDt.loan_req_amount.toDouble()),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row {
                        Text(
                            text = "Approved:",
                            color = Color(0xFFaaacb7),
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = formatMoneyValue(loanHistoryDt.loan_approved_amount.toDouble()),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }


            }
            if(expandItem) {
//                Spacer(modifier = Modifier.height(10.dp))


                if(loanHistoryDt.loan_outstanding_bal.toDouble() != loanHistoryDt.loan_approved_amount.toDouble() && !loanHistoryDt.loan_disbursed_date.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(20.dp))
//                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = loanHistoryDt.loanStatusCode.desc,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Disbursed on: ${formatDate(loanHistoryDt.loan_disbursed_date)}",
                        fontWeight = FontWeight.ExtraLight,
                        style = TextStyle(
                            fontStyle = FontStyle.Italic,
                        ),
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row {
                        Text(
                            text = "Outstanding balance:",
                            color = Color(0xFFaaacb7),
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = formatMoneyValue(loanHistoryDt.loan_outstanding_bal.toDouble()),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row {
                        Text(
                            text = "Loan interest:",
                            color = Color(0xFFaaacb7),
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = formatMoneyValue(loanHistoryDt.loan_interest.toDouble()),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row {
                        Text(
                            text = "Loan outstanding interest:",
                            color = Color(0xFFaaacb7),
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = formatMoneyValue(loanHistoryDt.loan_outstanding_int.toDouble()),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row {
                        Text(
                            text = "Loan total principal:",
                            color = Color(0xFFaaacb7),
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = formatMoneyValue(loanHistoryDt.loan_total_principal.toDouble()),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedButton(
                        onClick = { navigateToLoanScheduleScreen(loanHistoryDt.id) },
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(text = "Loan schedule")
                    }

                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun LoanPaymentScreenPreview(
    modifier: Modifier = Modifier
) {
    JuvinalPayTheme {
        UnpaidLoansScreen(
            unpaidLoans = loanHistory,
            navigateToLoanScheduleScreen = {}
        )
    }
}