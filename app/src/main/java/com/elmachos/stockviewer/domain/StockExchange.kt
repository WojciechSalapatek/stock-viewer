package com.elmachos.stockviewer.domain

import java.io.Serializable

enum class StockExchange(val stockName: String) : Serializable {
    GWP_POLAND("Geiłda Papirów Wartościowych - Poland")
}