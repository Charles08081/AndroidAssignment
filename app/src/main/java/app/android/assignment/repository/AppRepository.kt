package app.android.assignment.repository

import app.android.assignment.common.response.Transaction
import app.android.assignment.network.ApiService
import app.android.assignment.network.ResultStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Response


class AppRepository(private val apiService: ApiService) : AppRepositoryImpl {

    override suspend fun getTransactionRecord(): Flow<ResultStatus<MutableList<Transaction>>> {
        return apiService.getTransactionRecord().apiCall()
    }

    suspend fun <T> Response<T>.apiCall(): Flow<ResultStatus<T>> {
        return flow {
            this@apiCall.let { response ->
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(ResultStatus.Success<T>(it))
                    } ?: run {
                        emit(ResultStatus.Error(response.message()))
                    }
                } else {
                    val error = when (response.raw().code) {
                        401 -> {
                            "401 error"
                        }

                        else -> response.message()
                    }
                    emit(ResultStatus.Error(error))
                }

            }


        }
    }


}