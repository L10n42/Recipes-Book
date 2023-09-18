package com.kappdev.recipesbook.core.domain.util

import kotlinx.coroutines.flow.FlowCollector

sealed class ResultState<out R> {
    data class Success<out R>(val result: R): ResultState<R>()
    data class Failure(val exception: Exception): ResultState<Nothing>()
    data object Loading: ResultState<Nothing>()
}

suspend fun <T> FlowCollector<ResultState<T>>.emitSuccess(value: T) {
    emit(ResultState.Success(value))
}

suspend fun <T> FlowCollector<ResultState<T>>.emitFailure(exception: Exception) {
    emit(ResultState.Failure(exception))
}

suspend fun <T> FlowCollector<ResultState<T>>.emitLoading() {
    emit(ResultState.Loading)
}
