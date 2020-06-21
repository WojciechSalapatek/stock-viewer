package com.elmachos.stockviewer.domain

import java.io.Serializable
import java.util.*

data class ShareInformation (
    val shareName: String,
    val currency: Currency,
    val value: Double,
    val type: ShareInformationType,
    val date: Date
): Serializable {}

enum class ShareInformationType {
    OPEN_PRICE,
    CLOSE_PRICE,
    OTHER,
}

enum class Currency(val label: String) {
    PLN("PLN"),
    EUR("EUR"),
    UNKNOWN("Unknown"),
    USD("USD");

    companion object {
        @JvmStatic
        fun fromLabel(label: String?): Currency {
            return values().find { it.label == label } ?: UNKNOWN
        }
    }
}