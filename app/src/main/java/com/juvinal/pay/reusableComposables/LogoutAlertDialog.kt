package com.juvinal.pay.reusableComposables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LogoutDialog(
    loggingOut: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        title = {
            Text(text = "Logout confirmation")
        },
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(
                enabled = !loggingOut,
                onClick = onDismiss
            ) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            Button(
                enabled = !loggingOut,
                onClick = onConfirm
            ) {
                if(loggingOut) {
                    Text(text = "Logging out...")
                } else {
                    Text("Logout")
                }

            }
        },
    )
}