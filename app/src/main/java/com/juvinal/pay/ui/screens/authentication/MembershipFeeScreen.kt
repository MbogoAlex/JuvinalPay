package com.juvinal.pay.ui.screens.authentication

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
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
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.juvinal.pay.MainActivity
import com.juvinal.pay.R
import com.juvinal.pay.ui.screens.nav.AppNavigation
import com.juvinal.pay.ui.theme.JuvinalPayTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object MembershipFeeScreenDestination: AppNavigation {
    override val route = "membership-fee"
    override val title = "Membership Fee"
}
@Composable
fun MembershipFeeScreenComposable(
    navigateToInAppNavigationScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activity = (LocalContext.current as? MainActivity)
    BackHandler(onBack = {activity?.finish()})
    val viewModel: MembershipFeeScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    val isConnected by viewModel.isConnected.observeAsState(false)


    LaunchedEffect(Unit) {
        viewModel.checkIfRequiredFieldsAreFilled()
        viewModel.checkConnectivity(context)
        var i = 60;
        while(i > 0) {
            viewModel.checkConnectivity(context)
            delay(10000)
            i--
            Log.i("I_VALUE", "$i")
        }
    }



    var checkIfRequiredFieldsAreFilled by remember {
        mutableStateOf(false)
    }

    if(!checkIfRequiredFieldsAreFilled) {
        viewModel.checkIfRequiredFieldsAreFilled()
        checkIfRequiredFieldsAreFilled = true
    }

    var paymentCountdown by rememberSaveable {
        mutableIntStateOf(60)
    }

    var countdownOn by rememberSaveable {
        mutableStateOf(false)
    }

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
        Toast.makeText(context, "Payment successful. You are now a full member of JuvinalPay.", Toast.LENGTH_LONG).show()
        paymentCountdown = 60
        navigateToInAppNavigationScreen()
        viewModel.resetLoadingStatus()
    } else if(uiState.loadingStatus == LoadingStatus.FAIL) {
        Toast.makeText(context, "Failed. Check your internet connection and ensure that you have adequate funds in your M-PESA wallet", Toast.LENGTH_LONG).show()
        countdownOn = false
        paymentCountdown = 60
        viewModel.resetLoadingStatus()

    }

    if(paymentCountdown == 0 && !paymentStatusChecked) {
        viewModel.checkPaymentStatus()
        paymentStatusChecked = true
    }

    Box(
        modifier = Modifier
            .safeDrawingPadding()
    ) {
        MembershipFeeScreen(
            isConnected = isConnected,
            paymentCountdown = paymentCountdown,
            confirmationCountdown = confirmationCountdown,
            confirmationCountdownOn = confirmationCountdownOn,
            phoneNumber = uiState.msisdn,
            buttonEnabled = uiState.paymentButtonEnabled,
            loadingStatus = uiState.loadingStatus,
            onPhoneNumberChange = {
                viewModel.updatePhoneNo(it)
            },
            onPay = {
                viewModel.payMembershipFee()
                scope.launch {
                    countdownOn = true
                    while (paymentCountdown > 0 && countdownOn) {
                        delay(1000)
                        paymentCountdown--
                    }

                }
            },
            showCheckPaymentOption = uiState.paymentReference != null && !countdownOn,
            onCheckPayment = {
                confirmationCountdown = 5
//                countdownOn = true
                confirmationCountdownOn = true

                scope.launch {
                    while (confirmationCountdown > 0 && confirmationCountdownOn) {
                        delay(1000)
                        confirmationCountdown--
                    }
                    viewModel.checkPaymentStatus()
                    confirmationCountdownOn = false
                }
            }
        )
    }
}

@Composable
fun MembershipFeeScreen(
    isConnected: Boolean,
    paymentCountdown: Int,
    confirmationCountdown: Int,
    confirmationCountdownOn: Boolean,
    phoneNumber: String,
    buttonEnabled: Boolean,
    loadingStatus: LoadingStatus,
    showCheckPaymentOption: Boolean,
    onPhoneNumberChange: (String) -> Unit,
    onPay: () -> Unit,
    onCheckPayment: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
//                top = 30.dp,
//                bottom = 40.dp
            )

    ) {
        Text(
            text = "Membership Fee",
//            color = Color(0xFF0D141C),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp
                )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(id = R.drawable.membership_fee_banner),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Text(
                text = "Become a full member of JuvinalPay",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                lineHeight = 30.sp,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = stringResource(id = R.string.membership_fee_text),
                textAlign = TextAlign.Center,
                lineHeight = 24.sp,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 4.dp,
                        end = 16.dp,
                        bottom = 12.dp
                    )
            )
            Text(
                text = "Amount",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 8.dp,
                        end = 16.dp,
                        bottom = 8.dp
                    )
            )
            Row(
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
            ) {
                Text(
                    text = "Total: ",
                    fontWeight = FontWeight.Bold
                )
                Text(text = "ksh100")
            }
            Text(
                text = "Payment method",
                lineHeight = 23.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 8.dp,
                        end = 16.dp,
                        bottom = 8.dp
                    )
            )
            Row(
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
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
            TextField(
                label = {
                    Text(text = "Phone number")
                },
                value = phoneNumber,
                onValueChange = onPhoneNumberChange,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
                    .fillMaxWidth()
            )

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
                enabled = phoneNumber.isNotEmpty() && loadingStatus != LoadingStatus.LOADING && isConnected,
                onClick = onPay,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 12.dp
                    )
            ) {
                if(loadingStatus == LoadingStatus.LOADING) {
                    Text(text = "Processing in $paymentCountdown seconds")
                } else {
                    Text(text = "Pay")
                }
            }
            if(showCheckPaymentOption && isConnected) {
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
                        .padding(
                            horizontal = 16.dp
                        )
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

@Preview(showBackground = true)
@Composable
fun MembershipFeeScreenPreview() {
    JuvinalPayTheme {
        MembershipFeeScreen(
            isConnected = false,
            paymentCountdown = 60,
            confirmationCountdown = 5,
            confirmationCountdownOn = false,
            phoneNumber = "",
            buttonEnabled = false,
            showCheckPaymentOption = false,
            loadingStatus = LoadingStatus.INITIAL,
            onPhoneNumberChange = {},
            onPay = { /*TODO*/ },
            onCheckPayment = {}
        )
    }
}