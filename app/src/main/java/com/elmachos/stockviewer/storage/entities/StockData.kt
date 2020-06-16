package com.elmachos.stockviewer.storage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.elmachos.stockviewer.domain.Currency
import java.util.*

@Entity(tableName="stockdata")
data class StockData (
    @PrimaryKey
    val uid: Int,
    @ColumnInfo(name = "shareName")
    val shareName: String?,
    @ColumnInfo(name = "openVal")
    val openVal: Double?,
    @ColumnInfo(name = "closeVal")
    val closeVal: Double?,
    @ColumnInfo(name = "currency")
    val currency: Currency?,
    @ColumnInfo(name = "date")
    val date: Date?
)