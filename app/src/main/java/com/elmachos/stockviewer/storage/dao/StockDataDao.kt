package com.elmachos.stockviewer.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.elmachos.stockviewer.storage.entities.StockData
import java.util.*

@Dao
interface StockDataDao {
    @Query("SELECT * FROM stockdata")
    fun getAll(): List<StockData>

    @Query("SELECT * FROM stockdata WHERE shareName = :shareName")
    fun getByStockName(shareName: String): List<StockData>

    @Query("SELECT * FROM stockdata WHERE date = :date")
    fun getByDate(date: String): List<StockData>

    @Insert
    fun insertAll(vararg stockShares: StockData)

    @Delete
    fun delete(stockShare : StockData)

    @Query("DELETE FROM stockdata")
    fun deleteAll()
}