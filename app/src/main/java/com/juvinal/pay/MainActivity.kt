package com.juvinal.pay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.juvinal.pay.ui.screens.StartScreen
import com.juvinal.pay.ui.screens.authentication.LoginScreenComposable
import com.juvinal.pay.ui.screens.authentication.MembershipFeeScreenComposable
import com.juvinal.pay.ui.screens.authentication.RegistrationScreenComposable
import com.juvinal.pay.ui.screens.inApp.dashboard.profile.ProfileScreenComposable
import com.juvinal.pay.ui.theme.JuvinalPayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JuvinalPayTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StartScreen()
                }
            }
        }
    }
}
