package com.elmachos.stockviewer.net.parser

import com.elmachos.stockviewer.domain.Currency
import com.elmachos.stockviewer.domain.ShareInformation
import com.elmachos.stockviewer.domain.ShareInformationRecord
import com.elmachos.stockviewer.domain.ShareInformationType
import com.elmachos.stockviewer.net.parser.util.HtmlWalker
import java.util.*

class GPWResponseParser() : SharesResponseParser() {

    private val nameRegex = "<td class=\"left\">([^<]*)</td>"
    private val valueRegex = "<td class=\"text-right\">([^<]*)</td>"
    private val numberValueRegex = "<td class=\"text-right\">([0123456789,]*)</td>"

    override fun doParse(response: String): ShareInformationRecord {
        // TODO fix the date and refactor this shit
        val sharesChunk = getSharesSectionChunk(response)

        if (sharesChunk.contains("Brak danych")) return ShareInformationRecord(emptyMap())

        val res: MutableList<ShareInformation> = mutableListOf()

        var walker = HtmlWalker.ofString(sharesChunk)
        walker.skipToMatching("<table class=\"table footable\" data-sorting=\"true\" >")!!
        while (walker.skipToMatching("<tr>") != null) {
            walker.skipToMatching(nameRegex)
            val shareName = nameRegex.toRegex().find(walker.take())!!.groups[1]!!.value
            walker.move()
            val curr = Currency.fromLabel(valueRegex.toRegex().find(walker.take())?.groups?.get(1)?.value)
            walker.move()
            val openPrice = numberValueRegex.toRegex().find(walker.take())?.groups?.get(1)?.value?.replace(',', '.')?.toDouble()
            walker.move()
            walker.move()
            walker.move()
            val closePrice = numberValueRegex.toRegex().find(walker.take())?.groups?.get(1)?.value?.replace(',', '.')?.toDouble()
            if (openPrice != null) res.add(ShareInformation(shareName, curr, openPrice, ShareInformationType.OPEN_PRICE, Date()))
            if (closePrice != null) res.add(ShareInformation(shareName, curr, closePrice, ShareInformationType.CLOSE_PRICE, Date()))
        }


        return ShareInformationRecord.ofUngroupedShareInformation(res)
    }

    private fun getSharesSectionChunk(response: String): String {
        val regex = "<section class=\"mainContainer padding-bottom-20\"([\\s\\S]*)</section>".toRegex()
        val matchResult = regex.find(response, 0)
        if(matchResult==null) error()
        return matchResult!!.value
    }

}