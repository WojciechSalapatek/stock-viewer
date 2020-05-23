package com.elmachos.stockviewer.domain

import com.elmachos.stockviewer.activity.stockview.table.ShareViewData


class ShareInformationRecord(private val shareInformation: Map<String, List<ShareInformation>>) {

    companion object {
        @JvmStatic
        fun ofUngroupedShareInformation(shareInformation: List<ShareInformation>) =
            ShareInformationRecord(shareInformation.groupBy { it.shareName })
    }

    fun toView(): List<ShareViewData> {
        return shareInformation.entries
            .filter { it.value.isNotEmpty() }
            .map {
            ShareViewData(it.key, findOpenInformation(it.value)?.value, findCloseInformation(it.value)?.value, it.value.first().currency, it.value.first().date)
        }
    }

    private fun findOpenInformation(shareInformation: List<ShareInformation>): ShareInformation? {
        return shareInformation.find { it.type == ShareInformationType.OPEN_PRICE}
    }

    private fun findCloseInformation(shareInformation: List<ShareInformation>): ShareInformation? {
        return shareInformation.find { it.type == ShareInformationType.CLOSE_PRICE}
    }


}