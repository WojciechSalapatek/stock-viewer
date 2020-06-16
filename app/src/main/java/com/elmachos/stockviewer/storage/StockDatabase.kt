package com.elmachos.stockviewer.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.elmachos.stockviewer.storage.dao.StockDataDao
import com.elmachos.stockviewer.storage.entities.StockData

@Database(entities = arrayOf(StockData::class), version = 1, exportSchema = false)
public abstract class StockDatabase : RoomDatabase() {

    abstract fun stockDataDao(): StockDataDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: StockDatabase? = null

        fun getDatabase(context: Context): StockDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StockDatabase::class.java,
                    "stock_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}