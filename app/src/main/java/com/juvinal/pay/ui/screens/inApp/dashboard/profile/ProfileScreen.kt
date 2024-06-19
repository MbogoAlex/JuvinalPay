package com.juvinal.pay.ui.screens.inApp.dashboard.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.juvinal.pay.R
import com.juvinal.pay.ui.theme.JuvinalPayTheme

@Composable
fun ProfileScreenComposable(
    modifier: Modifier = Modifier
) {
    Box {
        ProfileScreen()
    }
}


@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color(0xFF313d60))
        )

        Column(
            modifier = Modifier
                .padding(
                    top = 30.dp
                )
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
//                .height(200.dp)
                    .padding(
                        top = 30.dp,
                        start = 30.dp,
                        end = 30.dp
                    )
//                    .background(Color(0xFF313d60))
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
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
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "Mbogo Alex Gitau".uppercase(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4b5259)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
//                elevation = CardDefaults.cardElevation(
//                    defaultElevation = 10.dp
//                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
//                        top = 20.dp,
                        start = 20.dp,
                        end = 20.dp,
                        bottom = 80.dp
                    )
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(20.dp)
                ) {
                    Card {
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
                    Card {
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
                    Card {
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
        ProfileScreen()
    }
}