package com.elmachos.stockviewer.net.parser

import com.elmachos.stockviewer.domain.Currency
import com.elmachos.stockviewer.domain.ShareInformation
import com.elmachos.stockviewer.domain.ShareInformationRecord
import com.elmachos.stockviewer.domain.ShareInformationType
import java.util.*

class GPWResponseParser() : SharesResponseParser() {

    override fun doParse(response: String?): ShareInformationRecord {
        return ShareInformationRecord.ofUngroupedShareInformation(listOf(
            ShareInformation("share1",  Currency.PLN, 30.0, ShareInformationType.OPEN_PRICE, Date()),
            ShareInformation("share1",  Currency.PLN, 35.0, ShareInformationType.CLOSE_PRICE, Date()),
            ShareInformation("share2",  Currency.PLN, 10.0, ShareInformationType.OPEN_PRICE, Date()),
            ShareInformation("share2",  Currency.PLN, 31.0, ShareInformationType.CLOSE_PRICE, Date())
        ))
    }
}