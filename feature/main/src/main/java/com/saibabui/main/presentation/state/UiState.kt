package com.saibabui.main.presentation.state

/**
 * Generic UI state management for handling loading, success, and error states
 */
sealed class UiState<out T> {
    object Idle : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String, val code: Int? = null) : UiState<Nothing>()
    
    companion object {
        fun <T> idle(): UiState<T> = Idle
        fun <T> loading(): UiState<T> = Loading
        fun <T> success(data: T): UiState<T> = Success(data)
        fun <T> error(message: String, code: Int? = null): UiState<T> = Error(message, code)
    }
}

/**
 * Extension functions for easier state handling
 */
fun <T> UiState<T>.isLoading(): Boolean = this is UiState.Loading
fun <T> UiState<T>.isSuccess(): Boolean = this is UiState.Success
fun <T> UiState<T>.isError(): Boolean = this is UiState.Error
fun <T> UiState<T>.isIdle(): Boolean = this is UiState.Idle

/**
 * Utility functions for state transformation
 */
fun <T, R> UiState<T>.map(transform: (T) -> R): UiState<R> {
    return when (this) {
        is UiState.Idle -> UiState.Idle
        is UiState.Loading -> UiState.Loading
        is UiState.Success -> UiState.Success(transform(this.data))
        is UiState.Error -> UiState.Error(this.message, this.code)
    }
}

fun <T> UiState<T>.fold(
    onIdle: () -> Unit = {},
    onLoading: () -> Unit = {},
    onSuccess: (T) -> Unit = {},
    onError: (String, Int?) -> Unit = { _, _ -> }
): Unit {
    when (this) {
        is UiState.Idle -> onIdle()
        is UiState.Loading -> onLoading()
        is UiState.Success -> onSuccess(this.data)
        is UiState.Error -> onError(this.message, this.code)
    }
}