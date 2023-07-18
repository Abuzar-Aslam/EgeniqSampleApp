package com.example.egeniqsampleapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.egeniqsampleapp.presentation.ui.MainUI
import com.example.egeniqsampleapp.ui.theme.EgeniqSampleAppTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * The main activity of the app.
 *
 * This activity sets up the content view using Jetpack Compose and displays the UI defined in the MainUI composable function.
 * The MainViewModel is injected using Hilt's viewModel delegate.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Inject the MainViewModel using Hilt viewModel
    val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the content view using Jetpack Compose
        setContent {
            EgeniqSampleAppTheme {
                MainUI(mainViewModel)
            }
        }
    }
}