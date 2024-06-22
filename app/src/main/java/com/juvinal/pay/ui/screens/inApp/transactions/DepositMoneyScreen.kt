package com.juvinal.pay.ui.screens.inApp.transactions

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.juvinal.pay.AppViewModelFactory
import com.juvinal.pay.LoadingStatus
import com.juvinal.pay.resusableFunctions.formatMoneyValue
import com.juvinal.pay.ui.theme.JuvinalPayTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DepositMoneyScreenComposable(
    navigateToInAppNavigationScreen: () -> Unit,
    navigateToInAppNavigationScreenWithArgs: (childScreen: String) -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler(onBack = navigateToInAppNavigationScreen)
    val context = LocalContext.current
    val viewModel: DepositMoneyScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    viewModel.checkIfFieldsAreValid()

    var showDepositDialog by remember { mutableStateOf(false) }

    var countdownOn by remember { mutableStateOf(false) }

    var countdown by remember { mutableIntStateOf(40) }


    val scope = rememberCoroutineScope()

    if(uiState.loadingStatus == LoadingStatus.SUCCESS) {
        countdownOn = false
        viewModel.toggleDepositSuccessDialog(true)
        countdown = 40
        viewModel.resetLoadingStatus()
    } else if(uiState.loadingStatus == LoadingStatus.FAIL) {
        countdownOn = false
        Toast.makeText(context, "Deposit failed", Toast.LENGTH_SHORT).show()
        countdown = 40
        viewModel.resetLoadingStatus()
    }

    if(countdown == 0) {
        viewModel.checkPaymentStatus()
    }

    if(showDepositDialog) {
        PaymentDialog(
            title = "Deposit",
            amount = formatMoneyValue(uiState.amount.toDouble()),
            onDismiss = {
                showDepositDialog = !showDepositDialog
            },
            onConfirm = {
                showDepositDialog = !showDepositDialog
                countdownOn = true
                scope.launch {
                    viewModel.initiateDeposit()
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
        DepositSuccessDialog(
            amount = formatMoneyValue(uiState.amount.toDouble()),
            onDismiss = {
                navigateToInAppNavigationScreenWithArgs("deposit-screen")
                viewModel.toggleDepositSuccessDialog(false)
            }
        )
    }

    Box {
        DepositMoneyScreen(
            countdown = countdown,
            saccoBalance = 0.0,
            amount = uiState.amount,
            phoneNumber = uiState.phoneNumber,
            onAmountChange = {
                viewModel.updateAmount(it)
                viewModel.checkIfFieldsAreValid()
            },
            onPhoneNumberChange = {
                viewModel.updatePhoneNumber(it)
                viewModel.checkIfFieldsAreValid()
            },
            buttonEnabled = uiState.depositButtonEnabled,
            loadingStatus = uiState.loadingStatus,
            onDeposit = {showDepositDialog = !showDepositDialog},
            onCheckPayment = {
                viewModel.checkPaymentStatus()
            },
            statusCheckMessage = uiState.statusCheckMessage
        )
    }
}

@Composable
fun DepositMoneyScreen(
    countdown: Int,
    saccoBalance: Double,
    amount: String,
    phoneNumber: String,
    onAmountChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    buttonEnabled: Boolean,
    loadingStatus: LoadingStatus,
    statusCheckMessage: String,
    onCheckPayment: () -> Unit,
    onDeposit: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "Deposit to Sacco",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Deposit Amount",
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Amount",
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
            Text(text = "Sacco Balance")
            Spacer(modifier = Modifier.weight(1f))
            Text(text = formatMoneyValue(saccoBalance))
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            enabled = buttonEnabled && loadingStatus != LoadingStatus.LOADING,
            onClick = onDeposit,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if(loadingStatus == LoadingStatus.LOADING) {
                Text(text = "Processing in $countdown seconds")
            } else {
                Text(text = "Confirm Deposit")
            }

        }
        if(statusCheckMessage == "Payment not successful") {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 16.dp
                    )
            ) {
                Text(text = "I have already paid")
                TextButton(onClick = onCheckPayment) {
                    Text(text = "Confirm")
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
            Text(text = "Confirm deposit of $amount")
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
fun DepositSuccessDialog(
    amount: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        title = {
            Text(text = "Deposit successful")
        },
        text = {
               Text(text = "You have deposited $amount to JuvinalPay")
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
fun DepositMoneyScreenPreview() {
    JuvinalPayTheme {
        DepositMoneyScreen(
            saccoBalance = 0.0,
            amount = "",
            phoneNumber = "0987678900",
            onAmountChange = {},
            onPhoneNumberChange = {},
            buttonEnabled = false,
            loadingStatus = LoadingStatus.INITIAL,
            statusCheckMessage = "",
            onCheckPayment = { /*TODO*/ },
            onDeposit = {},
            countdown = 40
        )
    }
}