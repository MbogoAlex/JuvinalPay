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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juvinal.pay.DocumentType
import com.juvinal.pay.documentTypes
import com.juvinal.pay.reusableComposables.AuthInputField
import com.juvinal.pay.reusableComposables.DocumentTypeSelection
import com.juvinal.pay.ui.theme.JuvinalPayTheme

@Composable
fun PersonalDetailsScreenComposable(
    modifier: Modifier = Modifier
) {
    Box {
        PersonalDetailsScreen()
    }
}

@Composable
fun PersonalDetailsScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .padding(
                top = 30.dp,
                start = 10.dp,
                end = 10.dp,
                bottom = 40.dp
            )
            .fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /*TODO*/ }) {
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
        FilledPersonalDetailsTextFields()
    }
}

@Composable
fun FilledPersonalDetailsTextFields(
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
                value = "Mbogo",
                trailingIcon = null,
                placeHolder = "Enter your surname",
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
                value = "Alex",
                trailingIcon = null,
                placeHolder = "Enter your first name",
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
                value = "Gitau",
                trailingIcon = null,
                placeHolder = "Enter your last name",
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
                onExpand = { /*TODO*/ }
            )
            Spacer(modifier = Modifier.height(10.dp))
            AuthInputField(
                heading = "Joining Date",
                value = "20 Jan, 2024",
                trailingIcon = null,
                placeHolder = "Enter your joining data",
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
                value = "mbogo3@gmail.com",
                trailingIcon = null,
                placeHolder = "Enter your password",
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
                value = "254794649026",
                trailingIcon = null,
                placeHolder = "Enter your phone number",
                onValueChange = {},
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                visibility = null,
                onChangeVisibility = { /*TODO*/ }
            )
            Spacer(modifier = Modifier.height(10.dp))
            AuthInputField(
                heading = "City",
                value = "Nairobi",
                trailingIcon = null,
                placeHolder = "Enter your city",
                onValueChange = {},
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
                visibility = null,
                onChangeVisibility = { /*TODO*/ }
            )
            Spacer(modifier = Modifier.height(10.dp))
            CountrySelection(
                onChangeCountry = {},
                selectedCountry = "Kenya",
                onExpand = { /*TODO*/ },
                expanded = true
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF405189)
                    ),
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = "Update")
                }
                Spacer(modifier = Modifier.width(3.dp))
                Button(
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
        PersonalDetailsScreen()
    }
}