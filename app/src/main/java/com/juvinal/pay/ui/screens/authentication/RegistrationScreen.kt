package com.juvinal.pay.ui.screens.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.juvinal.pay.AppViewModelFactory
import com.juvinal.pay.reusableComposables.AuthInputField
import com.juvinal.pay.DocumentType
import com.juvinal.pay.LoadingStatus
import com.juvinal.pay.R
import com.juvinal.pay.documentTypes
import com.juvinal.pay.reusableComposables.DocumentTypeSelection
import com.juvinal.pay.ui.theme.JuvinalPayTheme

@Composable
fun RegistrationScreenComposable(
    modifier: Modifier = Modifier
) {
    val viewModel: RegistrationScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    var showDocumentTypeSelection by remember { mutableStateOf(false) }

    Box {
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
                viewModel.registerUser()
            },
            loadingStatus = uiState.loadingStatus,
            buttonEnabled = uiState.saveButtonEnabled
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
                Text(text = "Already have an account?")
                TextButton(onClick = { /*TODO*/ }) {
                    Text(
                        text = "Signin",
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
                    containerColor = Color.White
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
                        buttonEnabled = buttonEnabled
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
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
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
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Get your free JuvinalPay account now and start saving",
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
            heading = "Surname",
            value = surname,
            trailingIcon = null,
            placeHolder = "Enter surname",
            onValueChange = onChangeSurname,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            visibility = null,
            onChangeVisibility = { /*TODO*/ }
        )
        Spacer(modifier = Modifier.height(10.dp))
        AuthInputField(
            heading = "First Name",
            value = firstName,
            trailingIcon = null,
            placeHolder = "Enter First name",
            onValueChange = onChangeFirstName,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            visibility = null,
            onChangeVisibility = { /*TODO*/ }
        )
        Spacer(modifier = Modifier.height(10.dp))
        AuthInputField(
            heading = "Last Name",
            value = lastName,
            trailingIcon = null,
            placeHolder = "Enter last name",
            onValueChange = onChangeLastName,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            visibility = null,
            onChangeVisibility = { /*TODO*/ }
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
            trailingIcon = null,
            placeHolder = "Enter Document No",
            onValueChange = onChangeDocumentNo,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            visibility = null,
            onChangeVisibility = { /*TODO*/ }
        )
        Spacer(modifier = Modifier.height(10.dp))
        AuthInputField(
            heading = "Email",
            value = email,
            trailingIcon = null,
            placeHolder = "Enter email address",
            onValueChange = onChangeEmail,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            visibility = null,
            onChangeVisibility = { /*TODO*/ }
        )
        Spacer(modifier = Modifier.height(10.dp))
        AuthInputField(
            heading = "Phone No",
            value = phoneNo,
            trailingIcon = null,
            placeHolder = "Enter phone no",
            onValueChange = onChangePhoneNo,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Phone
            ),
            visibility = null,
            onChangeVisibility = { /*TODO*/ }
        )
        Spacer(modifier = Modifier.height(10.dp))
        AuthInputField(
            heading = "Password",
            value = password,
            trailingIcon = null,
            placeHolder = "Enter password",
            onValueChange = onChangePassword,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Password
            ),
            visibility = null,
            onChangeVisibility = { /*TODO*/ }
        )
        Spacer(modifier = Modifier.height(10.dp))
        AuthInputField(
            heading = "Confirm Password",
            value = passwordConfirm,
            trailingIcon = null,
            placeHolder = "Confirm password",
            onValueChange = onChangePasswordConfirm,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            visibility = null,
            onChangeVisibility = { /*TODO*/ }
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            "By registering you agree to the",
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
            onClick = onRegister
        ) {
            if(loadingStatus == LoadingStatus.LOADING) {
                CircularProgressIndicator()
            } else {
                Text(
                    text = "Sign Up",
                    color = Color.White
                )
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
            buttonEnabled = false
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
            buttonEnabled = false
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