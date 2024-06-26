package com.juvinal.pay.ui.screens.inApp.dashboard

import android.app.DatePickerDialog
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.juvinal.pay.AppViewModelFactory
import com.juvinal.pay.R
import com.juvinal.pay.dateFormatter
import com.juvinal.pay.resusableFunctions.formatMoneyValue
import com.juvinal.pay.ui.screens.nav.AppNavigation
import com.juvinal.pay.ui.theme.JuvinalPayTheme
import java.time.LocalDate

object HomeScreenDestination: AppNavigation {
    override val route = "Home-screen"
    override val title = "Home screen"
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreenComposable(
    modifier: Modifier = Modifier
) {
    val viewModel: HomeScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    Box {
        HomeScreen(
            userName = uiState.userDetails.fname,
            accountSavings = uiState.accountSavings,
            loanBalance = uiState.loanBalance,
            guaranteedAmounts = uiState.guaranteedAmounts,
            netSavings = uiState.netSavings,
            accountShareCapital = uiState.accountShareCapital,
            loanAmountQualified = uiState.loanAmountQualified
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    userName: String,
    accountSavings: Double,
    loanBalance: Double,
    guaranteedAmounts: Double,
    netSavings: Double,
    accountShareCapital: Double,
    loanAmountQualified: Double,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            )
            .fillMaxSize()
    ) {
        Text(
            text = "Hello $userName!",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Here's what's happening with your account today.",
            style = TextStyle(
                color = Color(0xFFa0a2b0),
            ),
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        DateRangePicker()
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            AmountCard(
                title = "Account savings",
                amount = "0.00 %",
                amountColor = Color(0xFF21baa5),
                balance = formatMoneyValue(accountSavings),
                icon = R.drawable.account_balance,
                amountIcon = R.drawable.up_right_arrow,
                amountIconColor = Color(0xFF5cccbd),
                iconColor = Color(0xFF0ab39c),
                iconBackgroundColor = Color(0xFFd2f1ed)
            )
            Spacer(modifier = Modifier.height(20.dp))
            AmountCard(
                title = "Loan Balance",
                amount = "0.00 %",
                amountColor = Color(0xFFf28069),
                balance = formatMoneyValue(loanBalance),
                icon = R.drawable.credit_card,
                amountIcon = R.drawable.down_right_arrow,
                amountIconColor = Color(0xFFf28069),
                iconColor = Color(0XFF299cdb),
                iconBackgroundColor = Color(0xFFd8edf9)
            )
            Spacer(modifier = Modifier.height(20.dp))
            AmountCard(
                title = "Guaranteed Amounts",
                amount = "0.00 %",
                amountColor = Color(0xFF22baa6),
                balance = formatMoneyValue(guaranteedAmounts),
                icon = R.drawable.shield,
                amountIcon = R.drawable.up_right_arrow,
                amountIconColor = Color(0xFF22baa6),
                iconColor = Color(0xFFf7b84b),
                iconBackgroundColor = Color(0xFFfef2de)
            )
            Spacer(modifier = Modifier.height(20.dp))
            AmountCard(
                title = "Net Savings",
                amount = "0.00 %",
                amountColor = Color(0xFF9395a3),
                balance = formatMoneyValue(netSavings),
                icon = R.drawable.upward_chart,
                amountIcon = R.drawable.plus,
                amountIconColor = Color(0xFF9395a3),
                iconColor = Color(0xFF48588d),
                iconBackgroundColor = Color(0xFFdcdfea)
            )
            Spacer(modifier = Modifier.height(20.dp))
            AmountCard(
                title = "Account Share Capital",
                amount = "0.00 %",
                amountColor = Color(0xFF9395a3),
                balance = formatMoneyValue(accountShareCapital),
                icon = R.drawable.receipt,
                amountIcon = R.drawable.plus,
                amountIconColor = Color(0xFF9395a3),
                iconColor = Color(0xFF0ab39c),
                iconBackgroundColor = Color(0xFFd2f1ed)
            )
            Spacer(modifier = Modifier.height(20.dp))
            AmountCard(
                title = "Loan Amount Qualified",
                amount = "0.00 %",
                amountColor = Color(0xFF9395a3),
                balance = formatMoneyValue(loanAmountQualified),
                icon = R.drawable.loan,
                amountIcon = R.drawable.plus,
                amountIconColor = Color(0xFF9395a3),
                iconColor = Color(0XFF299cdb),
                iconBackgroundColor = Color(0xFFd8edf9)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateRangePicker() {
    val currentDate = LocalDate.now()
    val firstDayOfMonth = currentDate.withDayOfMonth(1)
    var startDate by remember { mutableStateOf(firstDayOfMonth) }
    var endDate by remember { mutableStateOf(currentDate) }
    val context = LocalContext.current

    @RequiresApi(Build.VERSION_CODES.O)
    fun showDatePicker(isStart: Boolean) {
        val initialDate = if (isStart) startDate else endDate
        val datePicker = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                if (isStart) {
                    if (selectedDate.isBefore(endDate) || selectedDate.isEqual(endDate)) {
                        startDate = selectedDate
                    } else {
                        // Handle case where start date is after end date
                        Toast.makeText(context, "Start date must be before end date", Toast.LENGTH_LONG).show()
                    }
                } else {
                    if (selectedDate.isAfter(startDate) || selectedDate.isEqual(startDate)) {
                        endDate = selectedDate
                    } else {
                        // Handle case where end date is before start date
                        Toast.makeText(context, "End date must be after start date", Toast.LENGTH_LONG).show()
                    }
                }
            },
            initialDate.year,
            initialDate.monthValue - 1,
            initialDate.dayOfMonth
        )
        datePicker.show()
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
        elevation = CardDefaults.elevatedCardElevation(10.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(onClick = { showDatePicker(true)  }) {
                Icon(
                    tint = Color(0xFF405189),
                    painter = painterResource(id = R.drawable.calendar),
                    contentDescription = null
                )
            }
            Text(text = dateFormatter.format(startDate))
            Text(text = "to")

            Text(text = dateFormatter.format(endDate))
            IconButton(onClick = { showDatePicker(false)  }) {
                Icon(
                    tint = Color(0xFF405189),
                    painter = painterResource(id = R.drawable.calendar),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun AmountCard(
    title: String,
    amount: String,
    amountColor: Color,
    balance: String,
    icon: Int,
    amountIcon: Int,
    amountIconColor: Color,
    iconColor: Color,
    iconBackgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = Modifier
            .fillMaxWidth()
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
        ) {
            Column {
                Text(
                    text = title.uppercase(),
                    color = Color(0xFFaaacb7),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = balance,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Icon(
//                        tint = amountIconColor,
//                        painter = painterResource(id = amountIcon),
//                        contentDescription = null,
//                        modifier = Modifier
//                            .size(20.dp)
//                    )
//                    Text(
//                        text = amount,
//                        color = amountColor
//                    )
//                }
//                Spacer(modifier = Modifier.height(20.dp))
                Icon(
                    tint = iconColor,
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier
                        .background(iconBackgroundColor)
                        .padding(20.dp)
                )

            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    JuvinalPayTheme {
        HomeScreen(
            userName = "David",
            accountSavings = 0.0,
            loanBalance = 0.0,
            guaranteedAmounts = 0.0,
            netSavings = 0.0,
            accountShareCapital = 0.0,
            loanAmountQualified = 0.0
        )
    }
}