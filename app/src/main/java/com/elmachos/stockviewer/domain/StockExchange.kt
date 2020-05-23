package com.elmachos.stockviewer.domain

data class StockExchange(val name: String){}

class StockExchangeProvider {
    companion object {
        fun getDefinedStockExchanges(): Set<StockExchange> {
            return setOf(StockExchange("Dummy Stock"))
        }
    }
}