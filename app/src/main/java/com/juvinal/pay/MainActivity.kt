package com.juvinal.pay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.juvinal.pay.ui.screens.authentication.LoginScreenComposable
import com.juvinal.pay.ui.screens.authentication.MembershipFeeScreenComposable
import com.juvinal.pay.ui.screens.authentication.RegistrationScreenComposable
import com.juvinal.pay.ui.screens.inApp.NavScreenComposable
import com.juvinal.pay.ui.screens.inApp.dashboard.profile.ChangePasswordScreenComposable
import com.juvinal.pay.ui.screens.inApp.dashboard.profile.PersonalDetailsScreenComposable
import com.juvinal.pay.ui.screens.inApp.dashboard.profile.PrivacyPolicyScreenComposable
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
                    MembershipFeeScreenComposable()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JuvinalPayTheme {
        Greeting("Android")
    }
}