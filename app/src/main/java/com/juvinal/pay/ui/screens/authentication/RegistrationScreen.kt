package com.juvinal.pay.ui.screens.authentication

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
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juvinal.pay.reusableComposables.AuthInputField
import com.juvinal.pay.DocumentType
import com.juvinal.pay.documentTypes
import com.juvinal.pay.ui.theme.JuvinalPayTheme

@Composable
fun RegistrationScreenComposable(
    modifier: Modifier = Modifier
) {
    Box {
        RegistrationScreen()
    }
}

@Composable
fun RegistrationScreen(
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
                    RegistrationDetailsInputField()
                }
            }
        }

    }
}

@Composable
fun RegistrationDetailsInputField(
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
            value = "",
            trailingIcon = null,
            placeHolder = "Enter surname",
            onValueChange = {},
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
            value = "",
            trailingIcon = null,
            placeHolder = "Enter First name",
            onValueChange = {},
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
            value = "",
            trailingIcon = null,
            placeHolder = "Enter last name",
            onValueChange = {},
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            visibility = null,
            onChangeVisibility = { /*TODO*/ }
        )
        Spacer(modifier = Modifier.height(10.dp))
        DocumentTypeSelection(
            documentType = DocumentType.NATIONAL_ID,
            onChangeDocumentType = {},
            expanded = true,
            onExpand = { /*TODO*/ })
        Spacer(modifier = Modifier.height(10.dp))
        AuthInputField(
            heading = "Document No",
            value = "",
            trailingIcon = null,
            placeHolder = "Enter Document No",
            onValueChange = {},
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
            value = "",
            trailingIcon = null,
            placeHolder = "Enter email address",
            onValueChange = {},
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
            value = "",
            trailingIcon = null,
            placeHolder = "Enter phone no",
            onValueChange = {},
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
            value = "",
            trailingIcon = null,
            placeHolder = "Enter password",
            onValueChange = {},
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
            value = "",
            trailingIcon = null,
            placeHolder = "Confirm password",
            onValueChange = {},
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
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0ab39c),
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .widthIn(250.dp),
            onClick = { /*TODO*/ }
        ) {
            Text(
                text = "Sign Up",
                color = Color.White
            )
        }
    }
}


@Composable
fun DocumentTypeSelection(
    documentType: DocumentType,
    onChangeDocumentType: (document: DocumentType) -> Unit,
    expanded: Boolean,
    onExpand: () -> Unit,
    modifier: Modifier = Modifier
) {

    var selectedType = if(documentType == DocumentType.NATIONAL_ID) "National Identification" else "Passport"

    Column {
        Row {
            Text(
                text = "Document Type",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = "*",
                color = Color.Red
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .clickable {
                    onExpand()
                }
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(
                        10.dp
                    )
                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Text(text = selectedType)
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Select Document Type"
                )
            }
        }

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp,
            ),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if(expanded) {
                documentTypes.forEachIndexed { index, s ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onChangeDocumentType(s.documentType)
                            }
                    ) {
                        Text(
                            text = s.name,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding()
                                .padding(
                                    top = 10.dp,
                                    bottom = 10.dp,
                                    start = 5.dp
                                )

                        )
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    JuvinalPayTheme {
        RegistrationScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationDetailsInputFieldPreview() {
    JuvinalPayTheme {
        RegistrationDetailsInputField()
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