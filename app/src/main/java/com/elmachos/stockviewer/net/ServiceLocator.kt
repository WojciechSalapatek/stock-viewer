package com.elmachos.stockviewer.net

import com.elmachos.stockviewer.domain.StockExchange
import com.elmachos.stockviewer.net.parser.GPWResponseParser
import com.elmachos.stockviewer.net.parser.SharesResponseParser
import java.text.SimpleDateFormat
import java.util.*

object ServiceLocator {

    private val DEFIED_PROVIDERS: Map<StockExchange, SharesUrlProvider> = mapOf(
        //StockExchange.GPW_POLAND to SharesUrlProvider { date -> "https://www.gpw.pl/archiwum-notowan?fetch=0&type=10&instrument=&date=%DATE_HOLDER%&show_x=Poka%C5%BC+wyniki".replace("%DATE_HOLDER%" ,SimpleDateFormat("dd-MM-yyyy", Locale.UK).format(date)) }
        StockExchange.GPW_POLAND to SharesUrlProvider { date -> "https://www.gpw.pl/archiwum-notowan?fetch=0&type=10&instrument=&date=%DATE_HOLDER%&show_x=Poka%C5%BC+wyniki".replace("%DATE_HOLDER%" , "16-06-2020") }
    )

    private val DEFIED_PARSERS: Map<StockExchange, () -> SharesResponseParser> = mapOf(
        StockExchange.GPW_POLAND to {GPWResponseParser()}
    )

    fun getHttpExecutor(): HttpTaskExecutor = HttpTaskExecutor()

    fun getSharesDataProvider(): SharesDataHttpProvider = SharesDataHttpProvider(getHttpExecutor())

    fun getUrlProviderForStock(stockExchange: StockExchange): SharesUrlProvider? {
        return DEFIED_PROVIDERS[stockExchange]
    }

    fun getResponseParserForStock(stockExchange: StockExchange): SharesResponseParser? {
        return DEFIED_PARSERS[stockExchange]?.invoke()
    }

}