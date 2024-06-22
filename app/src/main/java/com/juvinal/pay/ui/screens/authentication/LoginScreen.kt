package com.juvinal.pay.ui.screens.authentication

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.juvinal.pay.AppViewModelFactory
import com.juvinal.pay.LoadingStatus
import com.juvinal.pay.R
import com.juvinal.pay.reusableComposables.AuthInputField
import com.juvinal.pay.reusableComposables.PasswordInputField
import com.juvinal.pay.ui.screens.nav.AppNavigation
import com.juvinal.pay.ui.theme.JuvinalPayTheme

object LoginScreenDestination: AppNavigation {
    override val title: String = "Login screen"
    override val route: String = "login-screen"
    val documentNo: String = "documentNo"
    val password: String = "password"
    val routeWithArgs: String = "$route/{$documentNo}/{$password}"
}
@Composable
fun LoginScreenComposable(
    navigateToRegistrationScreen: () -> Unit,
    navigateToMembershipFeePaymentScreen: () -> Unit,
    navigateToInAppNavigationScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    BackHandler(onBack = navigateToRegistrationScreen)
    val viewModel: LoginScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    var checkIfRequiredFieldsAreFilled by remember {
        mutableStateOf(false)
    }

    if(!checkIfRequiredFieldsAreFilled) {
        viewModel.checkIfAllFieldsAreFilled()
        checkIfRequiredFieldsAreFilled = true
    }

    if(uiState.loadingStatus == LoadingStatus.SUCCESS) {
        if(uiState.userRegistered) {
            Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
            navigateToInAppNavigationScreen()
        } else {
            Toast.makeText(context, "Login successful. Pay Ksh 100 to be a full member of JuvinalPay", Toast.LENGTH_SHORT).show()
            navigateToMembershipFeePaymentScreen()
        }
        viewModel.resetLoadingStatus()
    } else if(uiState.loadingStatus == LoadingStatus.FAIL) {
        Toast.makeText(context, uiState.loginMessage, Toast.LENGTH_SHORT).show()
        viewModel.resetLoadingStatus()
    }

    Box(
        modifier = Modifier
            .safeDrawingPadding()
    ) {
        LoginScreen(
            documentNo = uiState.document_no,
            onDocumentNoChange = {
                viewModel.updateDocumentNo(it)
                viewModel.checkIfAllFieldsAreFilled()
            },
            password = uiState.password,
            onPasswordChange = {
                viewModel.updatePassword(it)
                viewModel.checkIfAllFieldsAreFilled()
            },
            onLogin = {
                viewModel.login()
            },
            buttonEnabled = uiState.loginButtonEnabled,
            loadingStatus = uiState.loadingStatus,
            navigateToRegistrationScreen = navigateToRegistrationScreen,
        )
    }
}

@Composable
fun LoginScreen(
    documentNo: String,
    password: String,
    onDocumentNoChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLogin: () -> Unit,
    buttonEnabled: Boolean,
    loadingStatus: LoadingStatus,
    navigateToRegistrationScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                shape = RoundedCornerShape(
                    bottomStart = 20.dp,
                    bottomEnd = 20.dp
                ),
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .height(160.dp)
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.juvinal_pay_logo_light),
                        contentDescription = null,
                        modifier = Modifier
                            .size(140.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Dont't have an account?")
                TextButton(onClick = navigateToRegistrationScreen) {
                    Text(
                        text = "Signup",
                        color = Color(0xFF405189)
                    )
                }
            }
            Spacer(modifier = Modifier.height(70.dp))
        }

        Box(
            modifier = Modifier
//                .align(Alignment.CenterStart)
                .padding(
                    top = 140.dp,
                    bottom = 140.dp,
                    start = 20.dp,
                    end = 20.dp
                )
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                ) {
                    LoginDetailsInputField(
                        documentNo = documentNo,
                        onDocumentNoChange = onDocumentNoChange,
                        onPasswordChange = onPasswordChange,
                        password = password,
                        buttonEnabled = buttonEnabled,
                        loadingStatus = loadingStatus,
                        onLogin = onLogin
                    )
                }
            }
        }

    }
}

@Composable
fun LoginDetailsInputField(
    documentNo: String,
    password: String,
    onDocumentNoChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLogin: () -> Unit,
    buttonEnabled: Boolean,
    loadingStatus: LoadingStatus,
    modifier: Modifier = Modifier
) {
    var passwordVisibility by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Welcome Back!",
            style = TextStyle(
                color = Color(0xFF62709e)
            ),
            fontSize = 24.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Sign in to continue to JuvinalPay",
            style = TextStyle(
                color = Color(0xFFacaeb8)
            ),
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
            )
        Spacer(modifier = Modifier.height(20.dp))

        AuthInputField(
            heading = "Username (Document No)",
            value = documentNo,
            placeHolder = "Document No - ID/Passport",
            onValueChange = onDocumentNoChange,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
        )
        Spacer(modifier = Modifier.height(10.dp))
        PasswordInputField(
            heading = "Password",
            value = password,
            trailingIcon = R.drawable.visibility_on,
            placeHolder = "Enter password",
            onValueChange = onPasswordChange,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            visibility = passwordVisibility,
            onChangeVisibility = {
                passwordVisibility = !passwordVisibility
            }
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            enabled = buttonEnabled && loadingStatus != LoadingStatus.LOADING,
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0ab39c),
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .widthIn(250.dp),
            onClick = onLogin
        ) {
            if(loadingStatus == LoadingStatus.LOADING) {
                Text(text = "Loading...")
            } else {
                Text(
                    text = "Sign in",
                    color = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Sign in with",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.fb),
                contentDescription = "Sign in with facebook",
                modifier = Modifier
                    .size(40.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Image(
                painter = painterResource(id = R.drawable.googl_2),
                contentDescription = "Sign in with google",
                modifier = Modifier
                    .size(40.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Image(
                painter = painterResource(id = R.drawable.x),
                contentDescription = "Sign in with twitter",
                modifier = Modifier
                    .size(40.dp)
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    JuvinalPayTheme {
        LoginScreen(
            documentNo = "",
            password = "",
            onDocumentNoChange = {},
            onPasswordChange = {},
            onLogin = { /*TODO*/ },
            buttonEnabled = false,
            loadingStatus = LoadingStatus.INITIAL,
            navigateToRegistrationScreen = {}
        )
    }
}