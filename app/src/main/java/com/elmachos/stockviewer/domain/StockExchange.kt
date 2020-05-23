package com.elmachos.stockviewer.domain

import java.io.Serializable

enum class StockExchange(val stockName: String) : Serializable {
    GWP_POLAND("Giełda Papierów Wartościowych - Poland")
}