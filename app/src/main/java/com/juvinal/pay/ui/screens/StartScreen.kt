package com.juvinal.pay.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.juvinal.pay.ui.screens.nav.NavigationGraph

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StartScreen(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
   NavigationGraph(navController = navController) 
}