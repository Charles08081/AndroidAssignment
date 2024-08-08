package app.android.assignment.home.usecase

import app.android.assignment.common.response.Transaction
import app.android.assignment.network.ResultStatus
import app.android.assignment.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class GetTransactionRecordUseCase (private val appRepository: AppRepository) {
  suspend fun getTransactionData(): Flow<ResultStatus<MutableList<Transaction>>> {
        return appRepository.getTransactionRecord()
  }
}