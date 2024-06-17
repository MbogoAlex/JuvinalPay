package com.juvinal.pay.reusableComposables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juvinal.pay.R

@Composable
fun AuthInputField(
    heading: String,
    value: String,
    trailingIcon: Int?,
    placeHolder: String,
    onValueChange: (newValue: String) -> Unit,
    keyboardOptions: KeyboardOptions,
    visibility: Boolean?,
    onChangeVisibility: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Row {
            Text(
                text = heading,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = "*",
                color = Color.Red
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = value,
            trailingIcon = {
                if(trailingIcon != null) {
                    IconButton(onClick = onChangeVisibility) {
                        if(visibility != null && visibility) {
                            Icon(
                                painter = painterResource(id = R.drawable.visibility_off),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        } else if(visibility != null) {
                            Icon(
                                painter = painterResource(id = R.drawable.visibility_on),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        }
                    }

                }
            },
            placeholder = {
                Text(text = placeHolder)
            },
            onValueChange = onValueChange,
            keyboardOptions = keyboardOptions,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}