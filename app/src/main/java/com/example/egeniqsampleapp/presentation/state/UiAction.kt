package com.example.egeniqsampleapp.presentation.state

/**
 * Sealed class representing UI actions.
 */
sealed class UiAction {
    /**
     * UI action for retrying an operation.
     */
    object RetryAction : UiAction()

    /**
     * UI action for handling an error.
     *
     * @param throwable The throwable representing the error.
     */
    data class HandleErrorAction(val throwable: Throwable) : UiAction()
}
