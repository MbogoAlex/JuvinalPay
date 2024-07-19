package com.juvinal.pay.ui.screens.authentication

import android.app.Activity
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
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
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
import com.juvinal.pay.R
import com.juvinal.pay.resusableFunctions.isValidEmail
import com.juvinal.pay.reusableComposables.AuthInputField
import com.juvinal.pay.reusableComposables.DocumentTypeSelection
import com.juvinal.pay.reusableComposables.PasswordInputField
import com.juvinal.pay.ui.screens.nav.AppNavigation
import com.juvinal.pay.ui.theme.JuvinalPayTheme

object RegistrationScreenDestination: AppNavigation {
    override val title: String = "Registration screen"
    override val route: String = "registration-screen"
}
@Composable
fun RegistrationScreenComposable(
    navigateToLoginScreen: () -> Unit,
    navigateToLoginScreenWithArgs: (documentNo: String, password: String) -> Unit,
    navigateToMembershipFeeScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activity = (LocalContext.current as? Activity)
    val context = LocalContext.current
    BackHandler(onBack = {activity?.finish()})

    val viewModel: RegistrationScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    var showDocumentTypeSelection by remember { mutableStateOf(false) }

    if(uiState.loadingStatus == LoadingStatus.SUCCESS) {
        Toast.makeText(context, "Registration successful. Pay Ksh 100 to be a full member of JuvinalPay", Toast.LENGTH_SHORT).show()
        navigateToMembershipFeeScreen()
        viewModel.resetLoadingStatus()
    } else if(uiState.loadingStatus == LoadingStatus.FAIL) {
        Toast.makeText(context, uiState.registrationFailureMessage, Toast.LENGTH_LONG).show()
        viewModel.resetLoadingStatus()
    }

    Box(
        modifier = Modifier
            .safeDrawingPadding()
    ) {
        RegistrationScreen(
            surname = uiState.surname,
            onChangeSurname = {
                viewModel.updateSurname(it)
                viewModel.checkIfAllFieldsAreFilled()
            },
            firstName = uiState.fName,
            onChangeFirstName = {
                viewModel.updateFName(it)
                viewModel.checkIfAllFieldsAreFilled()
            },
            lastName = uiState.lName,
            onChangeLastName = {
                viewModel.updateLName(it)
                viewModel.checkIfAllFieldsAreFilled()
            },
            documentNo = uiState.documentNo,
            onChangeDocumentNo = {
                viewModel.updateDocumentNo(it)
                viewModel.checkIfAllFieldsAreFilled()
            },
            documentType = uiState.documentType,
            onChangeDocumentType = {
                viewModel.updateDocumentType(it)
            },
            email = uiState.email,
            onChangeEmail = {
                viewModel.updateEmail(it)
                viewModel.checkIfAllFieldsAreFilled()
            },
            phoneNo = uiState.phoneNo,
            onChangePhoneNo = {
                viewModel.updatePhoneNo(it)
                viewModel.checkIfAllFieldsAreFilled()
            },
            password = uiState.password,
            onChangePassword = {
                viewModel.updatePassword(it)
                viewModel.checkIfAllFieldsAreFilled()
            },
            passwordConfirm = uiState.passwordConfirmation,
            onChangePasswordConfirm = {
                viewModel.updatePasswordConfirmation(it)
                viewModel.checkIfAllFieldsAreFilled()
            },
            onExpand = {
                showDocumentTypeSelection = !showDocumentTypeSelection
            },
            expanded = showDocumentTypeSelection,
            onRegister = {
                if(isValidEmail(uiState.email)) {
                    if(uiState.phoneNo.length > 10 || uiState.phoneNo.length < 10) {
                        Toast.makeText(context, "Phone number must be 10 digits", Toast.LENGTH_SHORT).show()
                    } else {
                        if(uiState.password == uiState.passwordConfirmation) {
                            if(uiState.password.length < 8) {
                                Toast.makeText(context, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show()
                            } else {
                                viewModel.registerUser()
                            }
                        } else {
                            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "Enter a valid email", Toast.LENGTH_SHORT).show()
                }

            },
            loadingStatus = uiState.loadingStatus,
            buttonEnabled = uiState.saveButtonEnabled,
            navigateToLoginScreen = navigateToLoginScreen
        )
    }
}

@Composable
fun RegistrationScreen(
    surname: String,
    onChangeSurname: (name: String) -> Unit,
    firstName: String,
    onChangeFirstName: (name: String) -> Unit,
    lastName: String,
    onChangeLastName: (name: String) -> Unit,
    documentNo: String,
    onChangeDocumentNo: (name: String) -> Unit,
    documentType: DocumentType,
    onChangeDocumentType: (documentType: DocumentType) -> Unit,
    email: String,
    onChangeEmail: (name: String) -> Unit,
    phoneNo: String,
    onChangePhoneNo: (name: String) -> Unit,
    password: String,
    onChangePassword: (name: String) -> Unit,
    passwordConfirm: String,
    onChangePasswordConfirm: (name: String) -> Unit,
    onExpand: () -> Unit,
    expanded: Boolean,
    onRegister: () -> Unit,
    loadingStatus: LoadingStatus,
    buttonEnabled: Boolean,
    navigateToLoginScreen: () -> Unit,
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
                        .height(80.dp)
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.juvinal_pay_logo_light),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                    )
                }
            }
        }

        Box(
            modifier = Modifier
//                .align(Alignment.CenterStart)
                .padding(
                    top = 80.dp,
                    bottom = 10.dp,
                    start = 8.dp,
                    end = 8.dp
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
                    RegistrationDetailsInputField(
                        surname = surname,
                        onChangeSurname = onChangeSurname,
                        firstName = firstName,
                        onChangeFirstName = onChangeFirstName,
                        lastName = lastName,
                        onChangeLastName = onChangeLastName,
                        documentNo = documentNo,
                        onChangeDocumentNo = onChangeDocumentNo,
                        documentType = documentType,
                        onChangeDocumentType = onChangeDocumentType,
                        email = email,
                        onChangeEmail = onChangeEmail,
                        phoneNo = phoneNo,
                        onChangePhoneNo = onChangePhoneNo,
                        password = password,
                        onChangePassword = onChangePassword,
                        passwordConfirm = passwordConfirm,
                        onChangePasswordConfirm = onChangePasswordConfirm,
                        onExpand = onExpand,
                        expanded = expanded,
                        onRegister = onRegister,
                        loadingStatus = loadingStatus,
                        buttonEnabled = buttonEnabled,
                        navigateToLoginScreen = navigateToLoginScreen
                    )
                }
            }
        }

    }
}

@Composable
fun RegistrationDetailsInputField(
    surname: String,
    onChangeSurname: (name: String) -> Unit,
    firstName: String,
    onChangeFirstName: (name: String) -> Unit,
    lastName: String,
    onChangeLastName: (name: String) -> Unit,
    documentNo: String,
    onChangeDocumentNo: (name: String) -> Unit,
    documentType: DocumentType,
    onChangeDocumentType: (documentType: DocumentType) -> Unit,
    email: String,
    onChangeEmail: (name: String) -> Unit,
    phoneNo: String,
    onChangePhoneNo: (name: String) -> Unit,
    password: String,
    onChangePassword: (name: String) -> Unit,
    passwordConfirm: String,
    onChangePasswordConfirm: (name: String) -> Unit,
    onExpand: () -> Unit,
    expanded: Boolean,
    onRegister: () -> Unit,
    loadingStatus: LoadingStatus,
    buttonEnabled: Boolean,
    navigateToLoginScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    var passwordVisibility by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Create New Account",
            style = TextStyle(
                color = Color(0xFF62709e)
            ),
            fontSize = 24.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Already have an account?",
                color = MaterialTheme.colorScheme.scrim
            )
            TextButton(onClick = navigateToLoginScreen) {
                Text(
                    text = "Signin",
                    color = Color(0xFF405189)
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Get your JuvinalPay account now and start saving",
                style = TextStyle(
                    color = Color(0xFFacaeb8)
                ),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(

            ) {
                AuthInputField(
                    heading = "Surname",
                    value = surname,
                    placeHolder = "Enter surname",
                    onValueChange = onChangeSurname,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    modifier = Modifier
                        .weight(1f)
                )
                Spacer(modifier = Modifier.width(3.dp))
                AuthInputField(
                    heading = "First Name",
                    value = firstName,
                    placeHolder = "Enter First name",
                    onValueChange = onChangeFirstName,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    modifier = Modifier
                        .weight(1f)
                )
            }


            Spacer(modifier = Modifier.height(10.dp))
            AuthInputField(
                heading = "Last Name",
                value = lastName,
                placeHolder = "Enter last name",
                onValueChange = onChangeLastName,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
            )
            Spacer(modifier = Modifier.height(10.dp))

            DocumentTypeSelection(
                documentType = documentType,
                onChangeDocumentType = onChangeDocumentType,
                expanded = expanded,
                onExpand = onExpand
            )
            Spacer(modifier = Modifier.height(10.dp))
            AuthInputField(
                heading = "Document No",
                value = documentNo,
                placeHolder = "Enter Document No",
                onValueChange = onChangeDocumentNo,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
            )
            Spacer(modifier = Modifier.height(10.dp))
            AuthInputField(
                heading = "Email",
                value = email,
                placeHolder = "Enter email address",
                onValueChange = onChangeEmail,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
            )
            Spacer(modifier = Modifier.height(10.dp))
            AuthInputField(
                heading = "Phone No",
                value = phoneNo,
                placeHolder = "Enter phone no",
                onValueChange = onChangePhoneNo,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Phone
                ),
            )
            Spacer(modifier = Modifier.height(10.dp))
            PasswordInputField(
                heading = "Password",
                value = password,
                trailingIcon = R.drawable.visibility_on,
                placeHolder = "Enter password",
                onValueChange = onChangePassword,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Password
                ),
                visibility = passwordVisibility,
                onChangeVisibility = {
                    passwordVisibility = !passwordVisibility
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            PasswordInputField(
                heading = "Confirm Password",
                value = passwordConfirm,
                trailingIcon = R.drawable.visibility_on,
                placeHolder = "Confirm password",
                onValueChange = onChangePasswordConfirm,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                visibility = passwordVisibility,
                onChangeVisibility = {
                    passwordVisibility = !passwordVisibility
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "By registering you agree to the",
                color = MaterialTheme.colorScheme.scrim,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text("JuvinalPay")
                TextButton(onClick = { /*TODO*/ }) {
                    Text(
                        text = "Terms of use",
                        color = Color(0xFF405189)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                enabled = buttonEnabled && loadingStatus != LoadingStatus.LOADING,
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0ab39c),
                ),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .widthIn(250.dp),
                onClick = onRegister
            ) {
                if(loadingStatus == LoadingStatus.LOADING) {
                    Text(text = "Loading...")
                } else {
                    Text(
                        text = "Sign Up",
                        color = Color.White
                    )
                }
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    JuvinalPayTheme {
        RegistrationScreen(
            surname = "",
            onChangeSurname = {},
            firstName = "",
            onChangeFirstName = {},
            lastName = "",
            onChangeLastName = {},
            documentNo = "",
            onChangeDocumentNo = {},
            documentType = DocumentType.NATIONAL_ID,
            onChangeDocumentType = {},
            email = "",
            onChangeEmail = {},
            phoneNo = "",
            onChangePhoneNo = {},
            password = "",
            onChangePassword = {},
            passwordConfirm = "",
            onChangePasswordConfirm = {},
            onExpand = { /*TODO*/ },
            expanded = false,
            onRegister = { /*TODO*/ },
            loadingStatus = LoadingStatus.INITIAL,
            buttonEnabled = false,
            navigateToLoginScreen = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationDetailsInputFieldPreview() {
    JuvinalPayTheme {
        RegistrationDetailsInputField(
            surname = "",
            onChangeSurname = {},
            firstName = "",
            onChangeFirstName = {},
            lastName = "",
            onChangeLastName = {},
            documentNo = "",
            onChangeDocumentNo = {},
            documentType = DocumentType.NATIONAL_ID,
            onChangeDocumentType = {},
            email = "",
            onChangeEmail = {},
            phoneNo = "",
            onChangePhoneNo = {},
            password = "",
            onChangePassword = {},
            passwordConfirm = "",
            onChangePasswordConfirm = {},
            onExpand = { /*TODO*/ },
            expanded = false,
            onRegister = { /*TODO*/ },
            loadingStatus = LoadingStatus.INITIAL,
            buttonEnabled = false,
            navigateToLoginScreen = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DocumentTypeSelectionPreview() {
    JuvinalPayTheme {
        DocumentTypeSelection(
            documentType = DocumentType.NATIONAL_ID,
            onChangeDocumentType = {},
            onExpand = {},
            expanded = true
        )
    }
}