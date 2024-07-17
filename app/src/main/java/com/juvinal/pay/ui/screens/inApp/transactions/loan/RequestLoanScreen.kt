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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
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
import com.juvinal.pay.DocumentType
import com.juvinal.pay.LoadingStatus
import com.juvinal.pay.loanTypeDt
import com.juvinal.pay.loanTypes
import com.juvinal.pay.model.LoanTypeDt
import com.juvinal.pay.resusableFunctions.formatMoneyValue
import com.juvinal.pay.reusableComposables.DocumentTypeSelection
import com.juvinal.pay.reusableComposables.LoanTypeSelection
import com.juvinal.pay.ui.screens.inApp.transactions.deposit.DepositMoneyScreenViewModel
import com.juvinal.pay.ui.theme.JuvinalPayTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



@Composable
fun RequestLoanScreenComposable(
    navigateToInAppNavigationScreen: () -> Unit,
    navigateToInAppNavigationScreenWithArgs: (childScreen: String) -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler(onBack = navigateToInAppNavigationScreen)
    val context = LocalContext.current
    val viewModel: RequestLoanScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    val isConnected by viewModel.isConnected.observeAsState(false)


    LaunchedEffect(Unit) {
        viewModel.checkConnectivity(context)
        var i = 60;
        while(i > 0) {
            viewModel.checkConnectivity(context)
            delay(10000)
            i--
            Log.i("I_VALUE", "$i")
        }
    }


    var showLoanRequestDialog by remember { mutableStateOf(false) }

    var loanSelectionExpanded by remember {
        mutableStateOf(false)
    }

    var successDialog by remember {
        mutableStateOf(false)
    }


    val scope = rememberCoroutineScope()

    if(uiState.loadingStatus == LoadingStatus.SUCCESS) {
        successDialog = !successDialog
        viewModel.resetLoadingStatus()
    } else if(uiState.loadingStatus == LoadingStatus.FAIL) {
        Toast.makeText(context, uiState.requestResponseMessage, Toast.LENGTH_LONG).show()
        viewModel.resetLoadingStatus()
    }


    if(showLoanRequestDialog) {
        LoanRequestDialog(
            title = "Loan request",
            amount = formatMoneyValue(uiState.amount.toDouble()),
            onDismiss = {
                showLoanRequestDialog = !showLoanRequestDialog
            },
            onConfirm = {
                showLoanRequestDialog = !showLoanRequestDialog
                scope.launch {
                    viewModel.requestLoan()
                }
            }
        )
    }

    if(successDialog) {
        RequestSuccessDialog(
            amount = formatMoneyValue(uiState.requestedAmount.toDouble()),
            onDismiss = {
                navigateToInAppNavigationScreenWithArgs("loan-request-screen")
                successDialog = !successDialog
            }
        )
    }

    Box(
        modifier = Modifier
            .safeDrawingPadding()
    ) {
        RequestLoanScreen(
            loanType = uiState.type,
            onExpand = {
                loanSelectionExpanded = !loanSelectionExpanded
            },
            loanTypes = uiState.loanTypes,
            loanSelectionExpanded = loanSelectionExpanded,
            onSelectLoanType = {
                viewModel.updateLoanType(it)
                loanSelectionExpanded = !loanSelectionExpanded
            },
            isConnected = isConnected,
            loanAmountQualified = uiState.loanAmountQualified,
            amount = uiState.amount,
            loanReason = uiState.loanPurpose,
            onAmountChange = {newValue->
                val filteredValue = newValue.filter { it.isDigit() }
                viewModel.updateAmount(filteredValue)
                viewModel.checkIfRequiredFieldsAreFilled()
            },
            onLoanReasonChange = {
                viewModel.updateLoanPurpose(it)
                viewModel.checkIfRequiredFieldsAreFilled()
            },
            buttonEnabled = uiState.requestButtonEnabled,
            loadingStatus = uiState.loadingStatus,
            onDeposit = {showLoanRequestDialog = !showLoanRequestDialog},
        )
    }
}

@Composable
fun RequestLoanScreen(
    loanType: LoanTypeDt,
    loanTypes: List<LoanTypeDt>,
    onSelectLoanType: (loanTye: LoanTypeDt) -> Unit,
    onExpand: () -> Unit,
    loanSelectionExpanded: Boolean,
    isConnected: Boolean,
    loanAmountQualified: Double,
    amount: String,
    loanReason: String,
    onAmountChange: (String) -> Unit,
    onLoanReasonChange: (String) -> Unit,
    buttonEnabled: Boolean,
    loadingStatus: LoadingStatus,
    onDeposit: () -> Unit
) {
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
            text = "Request a loan",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(40.dp))
        LoanTypeSelection(
            loanType = loanType,
            loanTypes = loanTypes,
            onSelectLoanType = onSelectLoanType,
            expanded = loanSelectionExpanded,
            onExpand = onExpand,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
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
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number
            ),
            onValueChange = onAmountChange,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = loanReason,
            placeholder = {
                Text(text = "Reason for the loan")
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            onValueChange = onLoanReasonChange,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Loan limit")
            Spacer(modifier = Modifier.weight(1f))
            Text(text = formatMoneyValue(loanAmountQualified))
        }
        Spacer(modifier = Modifier.weight(1f))
        if(!isConnected) {
            Text(
                text = "Connect to the internet",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
        Button(
            enabled = buttonEnabled && loadingStatus != LoadingStatus.LOADING && isConnected,
            onClick = onDeposit,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if(loadingStatus == LoadingStatus.LOADING) {
                Text(text = "Loading...")
            } else {
                Text(text = "Submit a request")
            }

        }
    }
}

@Composable
fun LoanRequestDialog(
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
            Text(text = "Confirm loan request of $amount")
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
fun RequestSuccessDialog(
    amount: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        title = {
            Text(text = "Loan application of $amount successful.")
        },
        text = {
            Text(text = "You will receive a confirmation email shortly.")
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
fun RequestLoanScreenPreview() {
    JuvinalPayTheme {
        RequestLoanScreen(
            loanType = loanTypeDt,
            onExpand = {},
            loanTypes = loanTypes,
            loanSelectionExpanded = true,
            onSelectLoanType = {},
            isConnected = false,
            loanAmountQualified = 0.0,
            amount = "",
            loanReason = "",
            onAmountChange = {},
            onLoanReasonChange = {},
            buttonEnabled = false,
            loadingStatus = LoadingStatus.INITIAL,
            onDeposit = {},
        )
    }
}
