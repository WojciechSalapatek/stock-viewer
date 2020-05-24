package com.elmachos.stockviewer.net

import com.elmachos.stockviewer.domain.ShareInformationRecord
import com.elmachos.stockviewer.domain.StockExchange
import com.elmachos.stockviewer.net.parser.ParsingParams
import com.elmachos.stockviewer.net.parser.SharesResponseParser
import java.util.*

class SharesDataHttpProvider(private val httpExecutor: HttpTaskExecutor) {

    fun fetchAndParseForStockAndDate(stockExchange: StockExchange?, date: Date?, resultCallback: (res: ShareInformationRecord) -> Any = {}) {
        validateParams(stockExchange, date); stockExchange!!; date!!

        val url = getUrlProvider(stockExchange).getUrlForDate(date)
        val responseParser = getResponseParser(stockExchange)

        val parsingCallback = resultCallback
        val requestCallback = { it: String? -> responseParser.execute(ParsingParams(it, parsingCallback=parsingCallback)); Unit}

        ServiceLocator.getHttpExecutor()
            .execute(RequestParams(url, responseCallback=requestCallback))
    }

    private fun getResponseParser(stockExchange: StockExchange): SharesResponseParser {
        val parser = ServiceLocator.getResponseParserForStock(stockExchange)
        nullCheck(parser, "No http response parser for stock: ${stockExchange.stockName}")
        return parser!!
    }

    private fun getUrlProvider(stockExchange: StockExchange): SharesUrlProvider {
        val urlProvider = ServiceLocator.getUrlProviderForStock(stockExchange)
        nullCheck(urlProvider, "No url provider defined for stock: ${stockExchange.stockName}")
        return urlProvider!!
    }

    private fun validateParams(stockExchange: StockExchange?, date: Date?) {
        nullCheck(stockExchange, "Illegal argument: stock must not be null")
        nullCheck(date, "Illegal argument: date must not be null")
    }

    private fun nullCheck(o: Any?, what: String) {
        if(o == null) throw Exception(what)
    }

}