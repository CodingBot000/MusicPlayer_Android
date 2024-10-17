package com.sample.myplayer.ui.component

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun CustomAlertDialog(showDialog: Boolean, content: String, confirmButtonName: String = "ok", onDismiss: () -> Unit) {

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(text = "Alert") },
            text = { Text(text = content) },
            confirmButton = {
                Button(
                    onClick = {
                        onDismiss()
                    }) {
                    Text(confirmButtonName)
                }
            },
        )
    }
}