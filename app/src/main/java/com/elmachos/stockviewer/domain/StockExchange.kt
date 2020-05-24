package com.elmachos.stockviewer.domain

import java.io.Serializable

enum class StockExchange(val stockName: String) : Serializable {
    GPW_POLAND("Giełda Papierów Wartościowych - Poland")
}