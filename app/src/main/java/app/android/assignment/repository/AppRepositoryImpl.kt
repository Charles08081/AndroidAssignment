package app.android.assignment.repository

import app.android.assignment.common.response.Transaction
import app.android.assignment.network.ResultStatus
import kotlinx.coroutines.flow.Flow

interface AppRepositoryImpl {
    suspend fun getTransactionRecord(): Flow<ResultStatus<MutableList<Transaction>>>
}