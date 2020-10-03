package com.example.madlevel4task2.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import com.example.madlevel4task2.R
import com.example.madlevel4task2.database.GameRepo
import com.example.madlevel4task2.model.Game
import kotlinx.android.synthetic.main.activity_game_history.*
import kotlinx.android.synthetic.main.content_game_history.*
import kotlinx.coroutines.*

class HistoryActivity : AppCompatActivity() {

    private val gameArray = arrayListOf<Game>()
    private val gameAdapter = GameAdapter(gameArray)
    private lateinit var gameRepo: GameRepo
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private var clearedHistory = R.string.cleared_history;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_history)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        gameRepo = GameRepo(this)

        initViews()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_history, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clear -> {
                clearGameHistory()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initViews() {
        rvHistory.layoutManager =
            LinearLayoutManager(this@HistoryActivity, RecyclerView.VERTICAL, false)
        rvHistory.adapter = gameAdapter
        rvHistory.addItemDecoration(
            DividerItemDecoration(
                this@HistoryActivity,
                DividerItemDecoration.VERTICAL
            )
        )
        getGameHistory()
    }

    private fun getGameHistory() {
        mainScope.launch {
            val gameList = withContext(Dispatchers.IO) {
                gameRepo.getAllGames()
            }
            this@HistoryActivity.gameArray.clear()
            this@HistoryActivity.gameArray.addAll(gameList)
            this@HistoryActivity.gameArray.reverse()
            this@HistoryActivity.gameAdapter.notifyDataSetChanged()
        }
    }

    private fun clearGameHistory() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepo.emptyGameHistory()
            }
            getGameHistory()
            Toast.makeText(this@HistoryActivity, clearedHistory, Toast.LENGTH_SHORT).show()
        }
    }

}
