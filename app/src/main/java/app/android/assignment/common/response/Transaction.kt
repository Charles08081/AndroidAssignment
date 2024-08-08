package app.android.assignment.common.response

data class Transaction(
    val id: Int, val transactionDate: String? = "", val description: String? = "",
    val category: String? = "", val debit: Double?  = null, val credit: Double? = null) {

}