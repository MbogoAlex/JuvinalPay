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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juvinal.pay.R
import com.juvinal.pay.reusableComposables.AuthInputField
import com.juvinal.pay.reusableComposables.PasswordInputField
import com.juvinal.pay.ui.theme.JuvinalPayTheme

@Composable
fun PrivacyPolicyScreenComposable(
    modifier: Modifier = Modifier
) {
    Box {
        PrivacyPolicyScreen()
    }
}

@Composable
fun PrivacyPolicyScreen(
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
                text = "Privacy Policy",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
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
                Text(
                    text = "Security:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    style = TextStyle(
                        textDecoration = TextDecoration.Underline
                    ),
                    color = Color(0xFF495057)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Two-factor Authentication",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFf62686e)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(id = R.string.two_factor_auth),
                    color = Color(0xFF878a99)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = "Enable Two-factor Authentication")
                }
                Spacer(modifier = Modifier.height(60.dp))
                Text(
                    text = "Delete this account:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    style = TextStyle(
                        textDecoration = TextDecoration.Underline
                    ),
                    color = Color(0xFF495057)
                )
                Spacer(modifier = Modifier.height(10.dp))
                PasswordInputField(
                    heading = "Password",
                    value = "",
                    trailingIcon = null,
                    placeHolder = "Enter your password",
                    onValueChange = {},
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password
                    ),
                    visibility = null,
                    onChangeVisibility = { /*TODO*/ }
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFfeefec)
                        ),
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(
                            text = "Close & Delete This Account",
                            color = Color(0xFFf0664a)
                        )
                    }
                    Spacer(modifier = Modifier.width(3.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFf3f6f9)
                        ),
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(
                            text = "Cancel",
                            color = Color.Black
                        )
                    }
                }
            }
        }

    }
}
@Preview(showBackground = true)
@Composable
fun PrivacyPolicyScreenPreview() {
    JuvinalPayTheme {
        PrivacyPolicyScreen()
    }
}