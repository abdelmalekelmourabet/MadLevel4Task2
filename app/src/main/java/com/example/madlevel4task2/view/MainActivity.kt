package com.example.madlevel4task2.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.madlevel4task2.R
import com.example.madlevel4task2.database.GameRepo
import com.example.madlevel4task2.model.Game

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var gameRepository: GameRepo
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        gameRepository = GameRepo(this)

        updateStatistics()

        ivRock.setOnClickListener { playOneRound(0) }
        ivPaper.setOnClickListener { playOneRound(1) }
        ivScissors.setOnClickListener { playOneRound(2) }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_history -> {
                startHistoryActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun playOneRound(playersChoice: Int) {
        //choice: 0 rock. 1 paper. 3 scissor
        //result player:  0 lose. 1 draw. 2 win

        //generates the pc's answer
        val computersChoice = (0..2).random()
        var result = -1

        ivPc.setImageResource(Game.GAME_RES_DRAWABLES_IDS[computersChoice])
        ivPlayer.setImageResource(Game.GAME_RES_DRAWABLES_IDS[playersChoice])

        if (playersChoice == computersChoice) {
            result = 1
        } else {
            when (playersChoice) {
                0 -> {
                    when (computersChoice) {
                        1 -> result = 0
                        2 -> result = 2
                    }
                }
                1 -> {
                    when (computersChoice) {
                        0 -> result = 2
                        2 -> result = 0
                    }
                }
                2 -> {
                    when (computersChoice) {
                        0 -> result = 0
                        1 -> result = 2
                    }
                }
            }
        }

        when (result) {
            0 -> tvResult.text = getString(R.string.pc_wins)
            1 -> tvResult.text = getString(R.string.draw)
            2 -> tvResult.text = getString(R.string.player_wins)
        }

        mainScope.launch {
            val game = Game(
                date = Date(), computer = computersChoice,
                player = playersChoice, result = result
            )
            withContext(Dispatchers.IO) {
                gameRepository.addGame(game)
            }

            updateStatistics()
        }

    }

    private fun startHistoryActivity() {
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
    }

    private fun updateStatistics() {
        mainScope.launch {
            val stats = withContext(Dispatchers.IO) {
                return@withContext arrayOf(
                    gameRepository.getLoseCount(),
                    gameRepository.getDrawCount(), gameRepository.getWinCount()
                )
            }
            this@MainActivity.tvStatistics.text =
                """Lose: ${stats[0]} | Draw: ${stats[1]} | Win: ${stats[2]}"""

        }
    }
}
