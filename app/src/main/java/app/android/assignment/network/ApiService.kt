package app.android.assignment.network

import app.android.assignment.common.response.Transaction
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("")
    suspend fun getTransactionRecord(): Response<MutableList<Transaction>>
}