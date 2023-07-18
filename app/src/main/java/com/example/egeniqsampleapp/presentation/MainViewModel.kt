package com.example.egeniqsampleapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.egeniqsampleapp.R
import com.example.egeniqsampleapp.domain.usecase.BreedUseCase
import com.example.egeniqsampleapp.presentation.model.BreedItem
import com.example.egeniqsampleapp.presentation.state.UiState
import com.example.egeniqsampleapp.presentation.state.UiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.jetbrains.annotations.VisibleForTesting
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * ViewModel class for the main screen of the app.
 *
 * This class is responsible for managing the state and business logic related to the main screen.
 * It interacts with the BreedUseCase to retrieve the list of breeds.
 *
 * @param breedUseCase The use case for retrieving the list of breeds.
 */
@HiltViewModel
class MainViewModel @Inject constructor(private val breedUseCase: BreedUseCase) : ViewModel() {

    private val _pagingData: MutableStateFlow<PagingData<BreedItem>> =
        MutableStateFlow(PagingData.empty())
    val pagingData: Flow<PagingData<BreedItem>> = _pagingData.asStateFlow()

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    /**
     * Initializes the ViewModel by fetching the breeds.
     */
    init {
        fetchBreeds()
    }

    /**
     * Handles UI actions triggered by the UI.
     *
     * @param action The UI action to handle.
     */
    fun onUiAction(action: UiAction) {
        when (action) {
            is UiAction.RetryAction -> fetchBreeds()
            is UiAction.HandleErrorAction -> {
                val newUiState = UiState(errorMessageId = handleException(action.throwable))
                _uiState.value = newUiState
            }
        }
    }

    /**
     * Fetches the list of breeds from the BreedUseCase.
     */
    fun fetchBreeds() {
        breedUseCase.getBreeds()
            .cachedIn(viewModelScope)
            .onEach { _pagingData.value = it }
            .launchIn(viewModelScope)
    }

    /**
     * Handles exceptions and returns the corresponding error message resource ID.
     *
     * @param exception The exception to handle.
     * @return The error message resource ID.
     */
    @VisibleForTesting
    fun handleException(exception: Throwable): Int {
        return when (exception) {
            is IOException -> R.string.network_error
            is HttpException -> R.string.http_error
            else -> R.string.unknown_error
        }
    }
}
