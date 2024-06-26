package com.juvinal.pay.ui.screens.inApp.transactions.transactionsHistory

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.juvinal.pay.model.TransactionHistoryData
import com.juvinal.pay.resusableFunctions.formatMoneyValue
import com.juvinal.pay.ui.theme.JuvinalPayTheme

@Composable
fun DepositHistoryScreenComposable(
    modifier: Modifier = Modifier
) {
    val viewModel: DepositHistoryScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()
    Box(
        modifier = modifier
            .safeDrawingPadding()
    ) {
        DepositHistoryScreen(transactions = uiState.transactions)
    }
}

@Composable
fun DepositHistoryScreen(
    transactions: List<TransactionHistoryData>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn {
            items(transactions) {
                TransactionItemCard(transaction = it)
            }
        }
    }
}

@Composable
fun TransactionItemCard(
    transaction: TransactionHistoryData,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = Modifier
            .padding(
                start = 20.dp,
                end = 20.dp,
                bottom = 10.dp
            )
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            Row {
                Text(
                    text = transaction.tran_status,
                    color = Color(0xFFaaacb7),
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = formatMoneyValue(transaction.tran_amt.toDouble()),
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Account: ${transaction.acct_no}",
                color = Color(0xFFaaacb7),
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text =transaction.tranTypeEntry.desc,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = transaction.share_month,
                fontWeight = FontWeight.ExtraLight,
                style = TextStyle(
                    fontStyle = FontStyle.Italic,
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DepositHistoryScreenPreview() {
    val transactions = listOf(
        TransactionHistoryItem(
            tran_status = "SUCCESS",
            tran_amt = "20.0",
            payment_mode = "MPESA",
            share_month = "2024-06-25",
            desc = "Credit-Deposit to Account"
        ),
        TransactionHistoryItem(
            tran_status = "SUCCESS",
            tran_amt = "20.0",
            payment_mode = "MPESA",
            share_month = "2024-06-25",
            desc = "Credit-Deposit to Account"
        ),
        TransactionHistoryItem(
            tran_status = "SUCCESS",
            tran_amt = "20.0",
            payment_mode = "MPESA",
            share_month = "2024-06-25",
            desc = "Credit-Deposit to Account"
        ),
    )
    JuvinalPayTheme {
//        DepositHistoryScreen(
//            transactions = transactions
//        )
    }
}