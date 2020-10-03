package com.example.madlevel4task2.database

import android.content.Context
import androidx.room.*
import com.example.madlevel4task2.model.Game


//database holder class, main access point to the db data
@Database(entities = [Game::class], version = 1, exportSchema = false)
@TypeConverters(TimeConverter::class)
abstract class HistoryDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao

    companion object {
        private const val DB_NAME = "GAME_HISTORY_DB"

        //Singleton object creation:
        @Volatile
        private var HistoryDatabaseInstance: HistoryDatabase? = null

        fun getDb(context: Context): HistoryDatabase? {
            // if instance or database doesn't exist, created new one
            if (HistoryDatabaseInstance == null) {
                synchronized(HistoryDatabase::class.java) {
                    if (HistoryDatabaseInstance == null) {
                        HistoryDatabaseInstance = Room.databaseBuilder(context.applicationContext,
                            HistoryDatabase::class.java,
                            DB_NAME).build()
                    }
                }
            }
            // Return the existing one:
            return HistoryDatabaseInstance
        }
    }
}