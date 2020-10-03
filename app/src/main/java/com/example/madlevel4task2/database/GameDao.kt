package com.example.madlevel4task2.database

import androidx.room.*
import com.example.madlevel4task2.model.Game

@Dao
interface GameDao {

    //suspend = can only be run in coroutine
    @Query("SELECT * FROM gameTable")
    suspend fun getAllGames(): List<Game>

    @Insert
    suspend fun addGame(game: Game)

    @Query("DELETE FROM gameTable")
    suspend fun emptyGameHistory()

    @Query("SELECT COUNT(*) FROM gameTable WHERE result = 0")
    suspend fun getLoseCount(): Int

    @Query("SELECT COUNT(*) FROM gameTable WHERE result = 1")
    suspend fun getDrawCount(): Int

    @Query("SELECT COUNT(*) FROM gameTable WHERE result = 2")
    suspend fun getWinCount(): Int
}