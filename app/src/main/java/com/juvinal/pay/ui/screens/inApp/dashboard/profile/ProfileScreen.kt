package com.juvinal.pay.ui.screens.inApp.dashboard.profile

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.juvinal.pay.AppViewModelFactory
import com.juvinal.pay.R
import com.juvinal.pay.reusableComposables.LogoutDialog
import com.juvinal.pay.ui.screens.nav.AppNavigation
import com.juvinal.pay.ui.theme.JuvinalPayTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object ProfileScreenDestination: AppNavigation {
    override val title: String = "Profile screen"
    override val route: String = "profile-screen"

}
@Composable
fun ProfileScreenComposable(
    navigateToPersonalDetailsScreen: () -> Unit,
    navigateToChangePasswordScreen: () -> Unit,
    navigateToPrivacyPolicyScreen: () -> Unit,
    navigateToInAppNavigationScreen: () -> Unit,
    navigateToLoginScreenWithArgs: (documentNo: String, password: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    BackHandler(onBack = navigateToInAppNavigationScreen)
    val viewModel: ProfileScreenViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    val scope = rememberCoroutineScope()

    var loggingOut by rememberSaveable {
        mutableStateOf(false)
    }
    
    var showLogoutDialog by remember {
        mutableStateOf(false)
    }
    
    if(showLogoutDialog) {
        LogoutDialog(
            loggingOut = loggingOut,
            onConfirm = {
                scope.launch {
                    loggingOut = true
                    delay(2000)
                    viewModel.logout()
                    navigateToLoginScreenWithArgs(uiState.userDetails.document_no, uiState.userDetails.password)
                    loggingOut = false
                    Toast.makeText(context, "You are logged out", Toast.LENGTH_SHORT).show()
                }      
            }, 
            onDismiss = {
                if(!loggingOut) {
                    showLogoutDialog = !showLogoutDialog
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .safeDrawingPadding()
    ) {
        ProfileScreen(
            loggingOut = loggingOut,
            username = uiState.userDetails.name,
            navigateToPersonalDetailsScreen = navigateToPersonalDetailsScreen,
            navigateToChangePasswordScreen = navigateToChangePasswordScreen,
            navigateToPrivacyPolicyScreen = navigateToPrivacyPolicyScreen,
            onLogout = {
                showLogoutDialog = !showLogoutDialog
            }
        )
    }
}


@Composable
fun ProfileScreen(
    username: String,
    loggingOut: Boolean,
    navigateToPersonalDetailsScreen: () -> Unit,
    navigateToChangePasswordScreen: () -> Unit,
    navigateToPrivacyPolicyScreen: () -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .height(200.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 30.dp,
                    bottom = 20.dp
                )
        ) {
            Box(
//                contentAlignment = Alignment.Center,
                modifier = Modifier
//                    .fillMaxSize()
//                .height(200.dp)
                    .padding(
                        start = 20.dp,
                        end = 20.dp
                    )
//                    .background(Color(0xFF313d60))
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Box(
                            modifier = Modifier
//                            .background(Color.Red)
                                .border(
                                    width = 1.dp,
                                    shape = RoundedCornerShape(100.dp),
                                    color = Color(0xFFf3f3f9)
                                )

                        ) {
                            Icon(
                                tint = Color.Gray,
                                painter = painterResource(id = R.drawable.person_filled),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(120.dp)
                                    .padding(40.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .zIndex(1f)
                                    .clip(CircleShape)
                            ) {
                                Icon(
                                    tint = Color.Black,
                                    painter = painterResource(id = R.drawable.camera),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .background(Color(0xFFf3f6f9))
                                        .padding(
                                            10.dp
                                        )


                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = username.uppercase(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4b5259)
                        )

                    }
                }
            }
//            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(
                        top = 10.dp,
                        start = 30.dp,
                        end = 30.dp
                    )
            ) {
                Card(
                    modifier = Modifier
                        .clickable {
                            navigateToPersonalDetailsScreen()
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(20.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.person_filled),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(text = "Personal details")
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Persona details")
                    }

                }
                Spacer(modifier = Modifier.height(20.dp))
                Card(
                    modifier = Modifier
                        .clickable { navigateToChangePasswordScreen() }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(20.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.password),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(text = "Change password")
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Change password")
                    }

                }
                Spacer(modifier = Modifier.height(20.dp))
                Card {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(20.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.documents),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(text = "Personal documents")
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Change password")
                    }

                }
                Spacer(modifier = Modifier.height(20.dp))
                Card(
                    modifier = Modifier
                        .clickable {
                            navigateToPrivacyPolicyScreen()
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(20.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.policy),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(text = "Privacy policy")
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Privacy policy")
                    }

                }

            }

            Spacer(modifier = Modifier.weight(1f))
//                    Spacer(modifier = Modifier.height(40.dp))
            Button(
                enabled = !loggingOut,
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 10.dp,
                        start = 30.dp,
                        end = 30.dp
                    )
            ) {
                if(loggingOut) {
                    Text(text = "Logging out...")
                } else {
                    Text(text = "Logout")
                }
            }
        }
    }
}

@Composable
fun ProfileSectionMenu(
    modifier: Modifier = Modifier
) {
    val items = listOf("Personal Details", "Change password")
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    JuvinalPayTheme {
        ProfileScreen(
            username = "David Njuguna",
            loggingOut = false,
            navigateToPersonalDetailsScreen = {},
            navigateToChangePasswordScreen = {},
            navigateToPrivacyPolicyScreen = {},
            onLogout = {}
        )
    }
}