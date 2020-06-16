package com.elmachos.stockviewer.storage.repositories

import com.elmachos.stockviewer.storage.dao.StockDataDao
import com.elmachos.stockviewer.storage.entities.StockData

class StockDataRepository(private val stockDataDao: StockDataDao) {

    val allWords: List<StockData> = stockDataDao.getAll()

    /*suspend fun insert(word: StockData) {
        wordDao.insert(word)
    }*/
}