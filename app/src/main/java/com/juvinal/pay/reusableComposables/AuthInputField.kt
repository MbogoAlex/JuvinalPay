package com.juvinal.pay.reusableComposables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juvinal.pay.R

@Composable
fun AuthInputField(
    heading: String,
    value: String,
    placeHolder: String,
    readOnly: Boolean = false,
    onValueChange: (newValue: String) -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        label = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = heading,
//                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.scrim,
//                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "*",
                    color = Color.Red
                )
            }
        },
        placeholder = {
            Text(
                text = placeHolder,
                color = MaterialTheme.colorScheme.scrim
            )
            Text(text = placeHolder)
        },
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        readOnly = readOnly,
        modifier = modifier
            .fillMaxWidth()
    )
}

@Composable
fun PasswordInputField(
    heading: String,
    value: String,
    trailingIcon: Int?,
    placeHolder: String,
    readOnly: Boolean = false,
    onValueChange: (newValue: String) -> Unit,
    keyboardOptions: KeyboardOptions,
    visibility: Boolean?,
    onChangeVisibility: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        label = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = heading,
//                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.scrim,
//                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "*",
                    color = Color.Red
                )
            }
        },
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
            Text(
                text = placeHolder,
                color = MaterialTheme.colorScheme.scrim
            )
            Text(text = placeHolder)
        },
        readOnly = readOnly,
        visualTransformation = if(visibility != null && visibility) VisualTransformation.None else PasswordVisualTransformation(),
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        modifier = modifier
            .fillMaxWidth()
    )
}