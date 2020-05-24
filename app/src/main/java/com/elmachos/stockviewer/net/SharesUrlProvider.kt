package com.elmachos.stockviewer.net

import java.util.*

class SharesUrlProvider(val providerFunction: (date: Date) -> String) {

    fun getUrlForDate(date: Date): String {
        return providerFunction(date)
    }

}