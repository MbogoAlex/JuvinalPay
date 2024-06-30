package com.juvinal.pay.ui.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.juvinal.pay.AppViewModelFactory
import com.juvinal.pay.R
import com.juvinal.pay.ui.screens.nav.AppNavigation
import com.juvinal.pay.ui.theme.JuvinalPayTheme

object WelcomeScreenDestination: AppNavigation {
    override val title: String = "Welcome Screen"
    override val route: String = "welcome-screen"

}

@Composable
fun WelcomeScreenComposable(
    navigateToRegistrationPage: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: WelcomeScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .safeDrawingPadding()
    ) {
        WelcomeScreen(
            navigateToRegistrationPage = {
                navigateToRegistrationPage()
                viewModel.saveLaunchState()
            }
        )
    }

}
@Composable
fun WelcomeScreen(
    navigateToRegistrationPage: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activity = (LocalContext.current as? Activity)

    BackHandler(onBack = {activity?.finish()})

    var items = listOf<Int>(1, 2, 3)
    var currentIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    var painter by rememberSaveable {
        mutableIntStateOf(R.drawable.welcome_group)
    }
    var mainText by rememberSaveable {
        mutableStateOf("Welcome to our app")
    }
    var bodyText by rememberSaveable {
        mutableStateOf("We are a community that helps each other grow and achieve our financial goals.")
    }
    when(currentIndex) {
        0 -> {
            painter = R.drawable.welcome_group
            mainText = "Juvinal Self Help Group"
            bodyText = "We are a community that helps each other grow and achieve our financial goals. Join us."
        }
        1 -> {
            painter = R.drawable.welcome_savings
            mainText = "Juvinal Self Help Group"
            bodyText = "Save with us"
        }
        2 -> {
            painter = R.drawable.welcome_loan
            mainText = "Juvinal Self Help Group"
            bodyText = "Take a loan at 1%"
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(onClick = {
                activity?.finish()
            }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close app",
                    modifier = Modifier
                )
            }
        }
        WelcomeScreenContent(
            painter = painterResource(id = painter),
            mainText = mainText,
            bodyText = bodyText
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            CircleDotCurIndex(
                currentIndex = currentIndex,
                items = items
            )
            if(currentIndex > 0) {
                Button(
                    onClick = {
                        if(currentIndex != 0) {
                            currentIndex--
                        }
                    },
                    modifier = Modifier
                        .clip(CircleShape)
//                        .background(Color.Blue)
                ) {
                    Icon(
                        tint = Color.White,
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Next"
                    )
                }
            }
            if(currentIndex != 2) {
                Button(
                    onClick = {
                        if(currentIndex != 2) {
                            currentIndex++
                        }
                    },
                    modifier = Modifier
                        .clip(CircleShape)
//                        .background(Color.Blue)
                ) {
                    Icon(
                        tint = Color.White,
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Next"
                    )
                }
            }
            if(currentIndex == 2) {
                Button(onClick = { navigateToRegistrationPage() }) {
                    Text(text = "Signup")
                }
            }

        }
    }
}

@Composable
fun WelcomeScreenContent(
    painter: Painter,
    mainText: String,
    bodyText: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(400.dp)
        )
        Text(
            text = mainText.uppercase(),
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = bodyText,
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontStyle = FontStyle.Italic
            ),
            lineHeight = 25.5.sp,
            fontWeight = FontWeight.Light
        )
    }
}

@Composable
fun CircleDotCurIndex(
    items: List<Int>,
    currentIndex: Int,
    modifier: Modifier = Modifier
) {
    var color: Color

    Row {
        items.forEachIndexed() { index, item ->

            if(currentIndex == index) {
                color = Color.Blue
            } else {
                color = Color.Gray
            }
            Icon(
                tint = color,
                painter = painterResource(id = R.drawable.circle),
                contentDescription = null,
                modifier = Modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CircleDotCurIndexPreview() {
    JuvinalPayTheme {
        CircleDotCurIndex(
            currentIndex = 0,
            items = listOf(1, 2, 3)
        )
    }
}
@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    JuvinalPayTheme {
        WelcomeScreen(
            navigateToRegistrationPage = {},
        )
    }
}