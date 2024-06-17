package com.juvinal.pay.ui.screens.authentication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.juvinal.pay.ui.theme.JuvinalPayTheme

@Composable
fun RegistrationScreenComposable() {

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
        ) {

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