package com.elmachos.stockviewer.activity.stockview.table

import com.elmachos.stockviewer.domain.Currency
import java.util.Date


data class ShareViewData(val shareName: String, val openVal: Double?, val closeVal: Double?, val currency: Currency, val date: Date) {}