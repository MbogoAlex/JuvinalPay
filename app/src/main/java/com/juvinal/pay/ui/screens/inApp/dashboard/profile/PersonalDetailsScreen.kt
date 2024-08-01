package com.juvinal.pay.ui.screens.inApp.dashboard.profile

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
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.juvinal.pay.AppViewModelFactory
import com.juvinal.pay.DocumentType
import com.juvinal.pay.reusableComposables.AuthInputField
import com.juvinal.pay.reusableComposables.DocumentTypeSelection
import com.juvinal.pay.ui.screens.nav.AppNavigation
import com.juvinal.pay.ui.theme.JuvinalPayTheme

object PersonalDetailsScreenDestination: AppNavigation {
    override val title: String = "Personal details screen"
    override val route: String = "personal-details-screen"
}
@Composable
fun PersonalDetailsScreenComposable(
    navigateToPreviousScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: PersonalDetailsScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .safeDrawingPadding()
    ) {
        PersonalDetailsScreen(
            navigateToPreviousScreen = navigateToPreviousScreen
        )
    }
}

@Composable
fun PersonalDetailsScreen(
    navigateToPreviousScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: PersonalDetailsScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .padding(
//                top = 30.dp,
                start = 10.dp,
                end = 10.dp,
                bottom = 40.dp
            )
            .fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = navigateToPreviousScreen) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Previous screen"
                )
            }
            Text(
                text = "Personal Details",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
        FilledPersonalDetailsTextFields(
            surname = uiState.userDetails.user.surname,
            onChangeSurname = {},
            fname = uiState.userDetails.user.fname,
            onChangeFName = {},
            lname = uiState.userDetails.user.lname,
            onChangeLName = {},
            documentType = if(uiState.userDetails.user.document_type == "NATIONAL_ID") DocumentType.NATIONAL_ID else if(uiState.userDetails.user.document_type == "PASSPORT") DocumentType.PASSPORT else DocumentType.ALIEN_ID,
            onChangeDocumentType = {},
            onExpand = { /*TODO*/ },
            joiningDate = uiState.userDetails.member.mem_joined_date ?: "",
            email = uiState.userDetails.user.email,
            onChangeEmail = {},
            phoneNo = uiState.userDetails.user.phone_no,
            onChangePhoneNo = {},
            city = "",
            onChangeCity = {},
            country = "Kenya",
            onChangeCountry = {}
        )
    }
}

@Composable
fun FilledPersonalDetailsTextFields(
    surname: String,
    onChangeSurname: (String) -> Unit,
    fname: String,
    onChangeFName: (String) -> Unit,
    lname: String,
    onChangeLName: (String) -> Unit,
    documentType: DocumentType,
    onChangeDocumentType: (DocumentType) -> Unit,
    onExpand: () -> Unit,
    joiningDate: String,
    email: String,
    onChangeEmail: (String) -> Unit,
    phoneNo: String,
    onChangePhoneNo: (String) -> Unit,
    city: String,
    onChangeCity: (String) -> Unit,
    country: String,
    onChangeCountry: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = Modifier
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            AuthInputField(
                heading = "Surname",
                value = surname,
                placeHolder = "Enter your surname",
                onValueChange = onChangeSurname,
                readOnly = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
            )
            Spacer(modifier = Modifier.height(10.dp))
            AuthInputField(
                heading = "First Name",
                value = fname,
                placeHolder = "Enter your first name",
                onValueChange = onChangeFName,
                readOnly = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
            )
            Spacer(modifier = Modifier.height(10.dp))
            AuthInputField(
                heading = "Last Name",
                value = lname,
                placeHolder = "Enter your last name",
                onValueChange = onChangeLName,
                readOnly = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
            )
            Spacer(modifier = Modifier.height(10.dp))
            DocumentTypeSelection(
                documentType = documentType,
                onChangeDocumentType = onChangeDocumentType,
                expanded = false,
                onExpand = onExpand
            )
            Spacer(modifier = Modifier.height(10.dp))
            AuthInputField(
                heading = "Joining Date",
                value = joiningDate,
                placeHolder = "Enter your joining data",
                onValueChange = {},
                readOnly = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
            )
            Spacer(modifier = Modifier.height(10.dp))
            AuthInputField(
                heading = "Email",
                value = email,
                placeHolder = "Enter your email",
                onValueChange = onChangeEmail,
                readOnly = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
            )
            Spacer(modifier = Modifier.height(10.dp))
            AuthInputField(
                heading = "Phone No",
                value = phoneNo,
                placeHolder = "Enter your phone number",
                onValueChange = onChangePhoneNo,
                readOnly = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
            )
            Spacer(modifier = Modifier.height(10.dp))
            AuthInputField(
                heading = "City",
                value = city,
                placeHolder = "Enter your city",
                onValueChange = onChangeCity,
                readOnly = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
            )
            Spacer(modifier = Modifier.height(10.dp))
            CountrySelection(
                onChangeCountry = onChangeCountry,
                selectedCountry = country,
                onExpand = { /*TODO*/ },
                expanded = false
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(
                    enabled = false,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF405189)
                    ),
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = "Update")
                }
                Spacer(modifier = Modifier.width(3.dp))
                Button(
                    enabled = false,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFe6f7f5)
                    ),
                    onClick = { /*TODO*/ }
                ) {
                    Text(
                        text = "Cancel",
                        color = Color(0xFF40c3b2)
                    )
                }
            }
        }
    }
}

@Composable
fun CountrySelection(
    onChangeCountry:(country: String) -> Unit,
    selectedCountry: String,
    onExpand: () -> Unit,
    expanded: Boolean,
    modifier: Modifier = Modifier
) {
    val countries = listOf("Kenya")

    Column {
        Row {
            Text(
                text = "Country",
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
                Text(text = selectedCountry)
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Select country"
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
                countries.forEachIndexed { index, s ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onChangeCountry(s)
                            }
                    ) {
                        androidx.compose.material.Text(
                            text = s,
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
fun PersonalDetailsScreenPreview() {
    JuvinalPayTheme {
        PersonalDetailsScreen(
            navigateToPreviousScreen = {}
        )
    }
}