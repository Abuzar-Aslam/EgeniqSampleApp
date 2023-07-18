package com.example.egeniqsampleapp.presentation.ui

import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Composable function for displaying an error Snackbar.
 *
 * @param errorMessage The error message to display.
 * @param onRetry The callback function to be called when the retry button is clicked.
 * @param modifier The modifier for configuring the appearance of the Snackbar.
 */
@Composable
fun ErrorSnackbar(
    errorMessage: String,
    onRetry: () -> Unit,
    modifier: Modifier
) {
    Snackbar(
        modifier = modifier,
        action = {
            TextButton(onClick = onRetry) {
                Text("Retry")
            }
        }
    ) {
        Text(errorMessage)
    }
}
