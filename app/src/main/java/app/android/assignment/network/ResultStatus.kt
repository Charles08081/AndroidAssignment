package app.android.assignment.network

sealed class ResultStatus<out R> {
    data class Success<out T>(val data: T) : ResultStatus<T>()
    data class Error(val errorMessage: String) : ResultStatus<Nothing>()
}