package app.android.assignment.network

import android.content.Context
import app.android.assignment.R
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder(appContext: Context) {
    companion object {
        fun newInstance(appContext: Context): RetrofitBuilder {
            return RetrofitBuilder(appContext)
        }
    }

    private val retrofit: Retrofit by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(ApiKeyInterceptor()).build()
        Retrofit.Builder()
            .baseUrl(appContext.resources.getString(R.string.localhost))
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestWithApiKey = originalRequest.newBuilder()
            .header("x-api-key", "")
            .build()

        return chain.proceed(requestWithApiKey)
    }
}