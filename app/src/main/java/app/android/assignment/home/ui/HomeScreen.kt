package app.android.assignment.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import app.android.assignment.common.response.Transaction
import app.android.assignment.home.vm.HomeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    vm: HomeViewModel,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val transactionData by vm.transactionStateFlow.collectAsState()
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_CREATE) {

                vm.getTransactionData()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(modifier = modifier) {
        TopBar()
        if (transactionData.size > 0) {
            PaginatedList(transactionData)
        }

    }
}


@Composable
fun TopBar() {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(10.dp, 0.dp, 10.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "Transaction Record")
            Spacer(modifier = Modifier.weight(1f))
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.DarkGray)
                .height(1.dp)
        ) {
        }
    }
}

@Composable
fun PaginatedList(list: MutableList<Transaction>) {
    val allItems = list
    var displayedItems by remember { mutableStateOf(allItems.take(5)) }
    var currentPage by remember { mutableStateOf(1) }
    val pageSize = 5
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .filter { it == displayedItems.lastIndex }
            .distinctUntilChanged()
            .collectLatest {
                if (displayedItems.size < allItems.size) {
                    delay(1000)
                    val nextPage = currentPage + 1
                    val nextItems = allItems.take(nextPage * pageSize)
                    displayedItems = nextItems
                    currentPage = nextPage
                }
            }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
    ) {
        items(displayedItems) { item ->
            TransactionItem(item)
        }
    }
}


@Composable
fun TransactionItem(data: Transaction) {
    Column(
        Modifier
            .background(color = Color.LightGray)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(text = "Transaction Date:\n${data.transactionDate.orEmpty()}")
            Text(text = "Category:\n${data.category.orEmpty()}")
            Text(text = "Description:\n${data.description.orEmpty()}")
            data.debit?.let {
                Text(text = "Debit:\n${it}")
            }
            data.credit?.let {
                Text(text = "Credit:\n${it}")
            }

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.DarkGray)
                .height(1.dp)
        ) {

        }
    }

}