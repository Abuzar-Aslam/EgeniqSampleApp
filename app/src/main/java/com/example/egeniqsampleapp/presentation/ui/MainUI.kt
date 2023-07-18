package com.example.egeniqsampleapp.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.egeniqsampleapp.R
import com.example.egeniqsampleapp.presentation.MainViewModel
import com.example.egeniqsampleapp.presentation.state.UiAction

/**
 * Composable function for the main screen of the app.
 *
 * @param viewModel The instance of MainViewModel to interact with the screen's state and events.
 */
@Composable
fun MainUI(viewModel: MainViewModel) {
    val list = viewModel.pagingData.collectAsLazyPagingItems()

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = stringResource(id = R.string.main_activity_title)) }) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(paddingValues = it)
        ) {
            when (list.loadState.refresh) {
                LoadState.Loading -> {
                    LoadingIndicator()
                }

                is LoadState.Error -> {
                    val loadState = list.loadState.refresh as LoadState.Error

                    if (uiState.errorMessageId != 0) {
                        ErrorSnackbar(
                            errorMessage = stringResource(id = uiState.errorMessageId),
                            onRetry = { viewModel.onUiAction(UiAction.RetryAction) },
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(8.dp)
                        )
                    } else {
                        viewModel.onUiAction(UiAction.HandleErrorAction(loadState.error))
                    }
                }

                else -> {
                    Column(
                        modifier = Modifier.align(Alignment.TopStart)
                    ) {
                        displayBreedList(list)
                    }
                }
            }
        }
    }
}
