package com.elmachos.stockviewer.storage.dao

import androidx.room.Dao
import androidx.room.Query
import com.elmachos.stockviewer.storage.entities.StockData
import java.util.*

@Dao
interface StockDataDao {
    @Query("SELECT * FROM stockdata")
    fun getAll(): List<StockData>

    @Query("SELECT * FROM stockdata WHERE date = :date")
    fun getByDate(date: Date): List<StockData>

    @Query("SELECT * FROM stockdata WHERE shareName = :shareName")
    fun getByStockName(shareName: String): List<StockData>
}