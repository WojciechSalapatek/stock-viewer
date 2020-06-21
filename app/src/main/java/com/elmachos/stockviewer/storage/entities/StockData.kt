package com.elmachos.stockviewer.storage.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.elmachos.stockviewer.domain.Currency
import java.util.*

@Entity(tableName="stockdata")
class StockData (
    @ColumnInfo(name = "shareName")
    @NonNull
    val shareName: String?,
    @ColumnInfo(name = "openVal")
    @NonNull
    val openVal: Double?,
    @ColumnInfo(name = "closeVal")
    @NonNull
    val closeVal: Double?,
    @ColumnInfo(name = "currency")
    @NonNull
    val currency: String?,
    @ColumnInfo(name = "date")
    @NonNull
    val date: String?
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}