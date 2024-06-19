package com.juvinal.pay.ui.screens.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juvinal.pay.R
import com.juvinal.pay.ui.theme.JuvinalPayTheme

@Composable
fun MembershipFeeScreenComposable(
    modifier: Modifier = Modifier
) {
    MembershipFeeScreen()
}

@Composable
fun MembershipFeeScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .padding(
                top = 30.dp,
//                bottom = 40.dp
            )
            .fillMaxSize()
    ) {
        Text(
            text = "Membership Fee",
            color = Color(0xFF0D141C),
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
                    start = 16.dp,
                    top = 8.dp,
                    end = 16.dp,
                    bottom = 8.dp
                )
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
            Text(text = "ksh1")
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
            value = "0794649026",
            onValueChange = {},
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
//        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                )
        ) {
            Text(text = "Pay")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MembershipFeeScreenPreview() {
    JuvinalPayTheme {
        MembershipFeeScreen()
    }
}