package com.elmachos.stockviewer.activity.menu

import com.elmachos.stockviewer.domain.StockExchange

data class ListData(val description: String? = null, val imgId: Int = 0, val stock: StockExchange) {}