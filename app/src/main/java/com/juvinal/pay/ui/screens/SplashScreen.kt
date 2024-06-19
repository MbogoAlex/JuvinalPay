package com.juvinal.pay.ui.screens

import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.juvinal.pay.R
import com.juvinal.pay.ui.screens.nav.AppNavigation
import com.juvinal.pay.ui.theme.JuvinalPayTheme
object SplashScreenDestination: AppNavigation{
    override val title: String = "Splash screen"
    override val route: String = "splash-screen"
}
@Composable
fun SplashScreenComposable(
    modifier: Modifier = Modifier
) {
    Box {
        SplashScreen()
    }
}

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        Image(painter = painterResource(id = R.drawable.juvinalpay_logo), contentDescription = null)
    }

}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    JuvinalPayTheme {
        SplashScreen()
    }
}