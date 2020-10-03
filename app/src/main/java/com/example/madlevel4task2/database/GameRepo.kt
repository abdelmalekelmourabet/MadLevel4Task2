package com.example.madlevel4task2.database

import android.content.Context
import com.example.madlevel4task2.model.Game

class GameRepo(context: Context) {
    private val gameDao: GameDao

    init {
        val database = HistoryDatabase.getDb(context)
        gameDao = database!!.gameDao()
    }

    suspend fun getAllGames(): List<Game> = gameDao.getAllGames()
    suspend fun addGame(game: Game) = gameDao.addGame(game)
    suspend fun emptyGameHistory() = gameDao.emptyGameHistory()
    suspend fun getLoseCount(): Int = gameDao.getLoseCount()
    suspend fun getDrawCount(): Int = gameDao.getDrawCount()
    suspend fun getWinCount(): Int = gameDao.getWinCount()
}