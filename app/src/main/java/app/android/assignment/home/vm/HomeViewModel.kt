package app.android.assignment.home.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.android.assignment.common.response.Transaction
import app.android.assignment.home.usecase.GetTransactionRecordUseCase

import app.android.assignment.network.ApiService
import app.android.assignment.network.ResultStatus
import app.android.assignment.network.RetrofitBuilder
import app.android.assignment.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val usecase: GetTransactionRecordUseCase) : ViewModel() {

    private val _transactionStateFlow =
        MutableStateFlow<MutableList<Transaction>>(mutableListOf())

    val transactionStateFlow: StateFlow<MutableList<Transaction>> =
        _transactionStateFlow.asStateFlow()

    fun getTransactionData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                usecase.getTransactionData().collect {
                    if (it is ResultStatus.Success) {
                        _transactionStateFlow.value = it.data
                    }

                }

            }

        }
    }
}