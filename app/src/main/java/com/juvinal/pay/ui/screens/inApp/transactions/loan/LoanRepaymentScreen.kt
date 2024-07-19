package com.juvinal.pay.ui.screens.inApp.transactions.loan

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.juvinal.pay.AppViewModelFactory
import com.juvinal.pay.LoadingStatus
import com.juvinal.pay.resusableFunctions.formatMoneyValue
import com.juvinal.pay.ui.screens.inApp.transactions.deposit.DepositMoneyScreenViewModel
import com.juvinal.pay.ui.screens.nav.AppNavigation
import com.juvinal.pay.ui.theme.JuvinalPayTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object LoanRepaymentScreenDestination: AppNavigation {
    override val title: String = "Loan repayment screen"
    override val route: String = "loan-repayment-screen"
    val loanId: String = "loanId"
    val memNo: String = "memNo"
    val schedulePayDate: String = "schedulePayDate"
    val scheduleTotal: String = "scheduleTotal"
    val scheduleTotalPaid: String = "scheduleTotalPaid"
    val scheduleTotalBalance: String = "scheduleTotalBalance"
    val routeWithArgs: String = "$route/{$loanId}/{$memNo}/{$schedulePayDate}/{$scheduleTotal}/{$scheduleTotalPaid}/{$scheduleTotalBalance}"
}
@Composable
fun LoanRepaymentScreenComposable(
    navigateToInAppNavigationScreen: () -> Unit,
    navigateToInAppNavigationScreenWithArgs: (childScreen: String) -> Unit,
    navigateToPreviousScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler(onBack = navigateToPreviousScreen)
    val context = LocalContext.current
    val viewModel: LoanRepaymentScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    val isConnected by viewModel.isConnected.observeAsState(false)

    LaunchedEffect(Unit) {
        viewModel.checkIfFieldsAreValid()
        viewModel.checkConnectivity(context)
        var i = 60;
        while(i > 0) {
            viewModel.checkConnectivity(context)
            delay(10000)
            i--
            Log.i("I_VALUE", "$i")
        }
    }


    var showPaymentDialog by remember { mutableStateOf(false) }

    var countdownOn by remember { mutableStateOf(false) }

    var countdown by remember { mutableIntStateOf(60) }

    var confirmationCountdown by rememberSaveable {
        mutableIntStateOf(5)
    }

    var confirmationCountdownOn by rememberSaveable {
        mutableStateOf(false)
    }

    var paymentStatusChecked by rememberSaveable {
        mutableStateOf(false)
    }


    val scope = rememberCoroutineScope()

    if(uiState.loadingStatus == LoadingStatus.SUCCESS) {
        countdownOn = false
        viewModel.toggleDepositSuccessDialog(true)
        countdown = 60
        viewModel.resetLoadingStatus()
    } else if(uiState.loadingStatus == LoadingStatus.FAIL) {
        countdownOn = false
        Toast.makeText(context, "Loan payment failed", Toast.LENGTH_SHORT).show()
        countdown = 60
        viewModel.resetLoadingStatus()
    }

    if(countdown == 0 && !paymentStatusChecked) {
        viewModel.checkPaymentStatus()
        paymentStatusChecked = true
    }

    if(showPaymentDialog) {
        PaymentDialog(
            title = "Loan repayment",
            amount = formatMoneyValue(uiState.amount.toDouble()),
            onDismiss = {
                showPaymentDialog = !showPaymentDialog
            },
            onConfirm = {
                viewModel.initiatePayment()
                showPaymentDialog = !showPaymentDialog
                countdownOn = true
                scope.launch {
                    while(countdown > 0 && countdownOn) {
                        delay(1000)
                        countdown--
                    }
                    countdownOn = false
                }
            }
        )
    }

    if(uiState.showSuccessDialog) {
        PaymentSuccessDialog(
            amount = formatMoneyValue(uiState.amountPaid),
            onDismiss = {
                navigateToInAppNavigationScreenWithArgs("unpaid_loan_screen")
                viewModel.toggleDepositSuccessDialog(false)
            }
        )
    }

    if(uiState.amount.isNotEmpty() && uiState.scheduleTotalBalance.isNotEmpty()) {
        if(uiState.amount.toDouble() > uiState.scheduleTotalBalance.toDouble()) {
            Toast.makeText(context, "Loan amount exceeds schedule balance", Toast.LENGTH_SHORT).show()
        }
    }


    Box(
        modifier = Modifier
            .safeDrawingPadding()
    ) {
        LoanRepaymentScreen(
            payByDate = uiState.schedulePayDate,
            scheduleTotal = uiState.scheduleTotal,
            scheduleTotalPaid = uiState.scheduleTotalPaid,
            scheduleTotalBalance = uiState.scheduleTotalBalance,
            isConnected = isConnected,
            countdown = countdown,
            confirmationCountdownOn = confirmationCountdownOn,
            confirmationCountdown = confirmationCountdown,
            amount = uiState.amount,
            phoneNumber = uiState.phoneNumber,
            onAmountChange = {newValue ->
                val filteredValue = newValue.filter { it.isDigit() }
                viewModel.updateAmount(filteredValue)
                viewModel.checkIfFieldsAreValid()
            },
            onPhoneNumberChange = {
                viewModel.updatePhoneNumber(it)
                viewModel.checkIfFieldsAreValid()
            },
            buttonEnabled = uiState.paymentButtonEnabled,
            loadingStatus = uiState.loadingStatus,
            onDeposit = {showPaymentDialog = !showPaymentDialog},
            onCheckPayment = {
                confirmationCountdown = 5

                confirmationCountdownOn = true
                scope.launch {
                    while (confirmationCountdown > 0 && confirmationCountdownOn) {
                        delay(1000)
                        confirmationCountdown--
                    }
                    viewModel.checkPaymentStatus()
                    confirmationCountdownOn = false
                }
            },
            statusCheckMessage = uiState.statusCheckMessage,
            navigateToPreviousScreen = navigateToPreviousScreen
        )
    }
}

@Composable
fun LoanRepaymentScreen(
    payByDate: String,
    scheduleTotal: String,
    scheduleTotalPaid: String,
    scheduleTotalBalance: String,
    isConnected: Boolean,
    countdown: Int,
    confirmationCountdown: Int,
    confirmationCountdownOn: Boolean,
    amount: String,
    phoneNumber: String,
    onAmountChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    buttonEnabled: Boolean,
    loadingStatus: LoadingStatus,
    statusCheckMessage: String,
    onCheckPayment: () -> Unit,
    onDeposit: () -> Unit,
    navigateToPreviousScreen: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(
                top = 10.dp
            )
            .fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(
                    start = 10.dp
                )
        ) {
            IconButton(onClick = navigateToPreviousScreen) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Previous screen"
                )
            }
            Text(
                text = "Loan repayment",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 20.dp
                )
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = "Loan schedule",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Pay by: $payByDate",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = amount,
                placeholder = {
                    Text(text = "Enter amount")
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                onValueChange = onAmountChange,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Payment method",
                lineHeight = 23.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(
                        top = 8.dp,
                        bottom = 8.dp
                    )
            )
            Row(
                modifier = Modifier
                    .padding(
                        vertical = 8.dp
                    )
            ) {
                Card {
                    Text(
                        text = "Mpesa",
                        modifier = Modifier
                            .padding(
                                horizontal = 16.dp,
                                vertical = 8.dp
                            )
                    )
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Phone number",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = phoneNumber,
                placeholder = {
                    Text(text = "Enter phone number")
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                onValueChange = onPhoneNumberChange,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Schedule total")
                Spacer(modifier = Modifier.weight(1f))
                Text(text = formatMoneyValue(scheduleTotal.toDouble()))
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Paid")
                Spacer(modifier = Modifier.weight(1f))
                Text(text = formatMoneyValue(scheduleTotalPaid.toDouble()))
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Balance")
                Spacer(modifier = Modifier.weight(1f))
                Text(text = formatMoneyValue(scheduleTotalBalance.toDouble()))
            }
            Spacer(modifier = Modifier.weight(1f))
            if (!isConnected) {
                Text(
                    text = "Connect to the internet",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            }
            Button(
                enabled = buttonEnabled && loadingStatus != LoadingStatus.LOADING && isConnected && (amount.isNotEmpty() && (amount.toDouble() != 0.0 && !(amount.toDouble() > scheduleTotalBalance.toDouble()))),
                onClick = onDeposit,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (loadingStatus == LoadingStatus.LOADING) {
                    Text(text = "Processing in $countdown seconds")
                } else {
                    Text(text = "Pay now")
                }
            }

            if (statusCheckMessage == "Payment not successful" && isConnected) {
                if(confirmationCountdownOn) {
                    Text(
                        text = "Confirm again in $confirmationCountdown seconds",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 16.dp
                            )
                    ) {
                        Text(text = "I have already paid")
                        TextButton(onClick = onCheckPayment) {
                            Text(text = "Confirm")
                        }

                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(10.dp)
                    ){
                        Text(
                            text = "NOTE:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "If you clicked on the 'Pay now' button, wait for the STK push for a few more seconds before retrying and click on 'Confirm' after paying and you have received the Mpesa message. If you have already paid, don't retry but click on 'Confirm' till success dialog appears.",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentDialog(
    title: String,
    amount: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        title = {
            Text(text = title)
        },
        text = {
            Text(text = "Confirm loan payment of $amount")
        },
        onDismissRequest =  onDismiss,
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    )
}

@Composable
fun PaymentSuccessDialog(
    amount: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        title = {
            Text(text = "Payment successful")
        },
        text = {
            Text(text = "You have paid loan amount $amount")
        },
        onDismissRequest =  onDismiss,
        confirmButton = {
            Button(onClick = onDismiss) {
                Text(text = "OK")
            }
        },

        )
}

@Preview(showBackground = true)
@Composable
fun LoanRepaymentScreenPreview() {
    JuvinalPayTheme {
        LoanRepaymentScreen(
            payByDate = "31-Jul-2024",
            scheduleTotal = "28928.00",
            scheduleTotalPaid = "3.00",
            scheduleTotalBalance = "28925.00",
            isConnected = false,
            amount = "",
            phoneNumber = "0987678900",
            onAmountChange = {},
            onPhoneNumberChange = {},
            buttonEnabled = false,
            loadingStatus = LoadingStatus.INITIAL,
            statusCheckMessage = "",
            onCheckPayment = { /*TODO*/ },
            onDeposit = {},
            countdown = 60,
            confirmationCountdownOn = false,
            confirmationCountdown = 5,
            navigateToPreviousScreen = {}
        )
    }
}