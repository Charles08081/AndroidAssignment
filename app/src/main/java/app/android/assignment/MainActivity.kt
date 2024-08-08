package app.android.assignment

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.android.assignment.home.ui.HomeScreen
import app.android.assignment.home.usecase.GetTransactionRecordUseCase
import app.android.assignment.home.vm.HomeViewModel
import app.android.assignment.network.RetrofitBuilder
import app.android.assignment.repository.AppRepository
import app.android.assignment.ui.theme.AndroidAssignmentTheme

class MainActivity : ComponentActivity() {

    private val vm: HomeViewModel by lazy {
        val retrofitBuilder = RetrofitBuilder.newInstance(applicationContext)
        val apiService = retrofitBuilder.apiService
        val repository = AppRepository(apiService)
        val useCase = GetTransactionRecordUseCase(repository)
        HomeViewModel(useCase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidAssignmentTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(Modifier.padding(innerPadding), vm)
                }
            }
        }
    }
}