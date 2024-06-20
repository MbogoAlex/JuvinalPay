package com.juvinal.pay.ui.screens.inApp.dashboard.profile

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.juvinal.pay.reusableComposables.AuthInputField
import com.juvinal.pay.reusableComposables.DocumentTypeSelection
import com.juvinal.pay.reusableComposables.PasswordInputField
import com.juvinal.pay.ui.theme.JuvinalPayTheme

@Composable
fun ChangePasswordScreenComposable(
    modifier: Modifier = Modifier
) {
    Box {
        ChangePasswordScreen()
    }
}

@Composable
fun ChangePasswordScreen(
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
               text = "Change password",
               fontWeight = FontWeight.Bold,
               fontSize = 20.sp
           )
       }
       FilledPasswordTextFields()
   }
}

@Composable
fun FilledPasswordTextFields(
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            PasswordInputField(
                heading = "Current password",
                value = "",
                trailingIcon = null,
                placeHolder = "Enter current password",
                onValueChange = {},
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Password
                ),
                visibility = null,
                onChangeVisibility = { /*TODO*/ }
            )
            Spacer(modifier = Modifier.height(10.dp))
            PasswordInputField(
                heading = "New Password",
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
            PasswordInputField(
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
            Spacer(modifier = Modifier.height(10.dp))
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "Forgot Password?")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0ab39c)
                ),
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(Alignment.End)
            ) {
                Text(
                    text = "Change Password"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChangePasswordScreenPreview() {
    JuvinalPayTheme {
        ChangePasswordScreen()
    }
}