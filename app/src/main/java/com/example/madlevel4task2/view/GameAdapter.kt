package com.example.madlevel4task2.view

import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel4task2.R
import com.example.madlevel4task2.model.Game
import kotlinx.android.synthetic.main.item_history.view.*

//Manages ViewHolder objects
class GameAdapter(private val games: List<Game>) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {
    private var pcWins = R.string.pc_wins;
    private var draw = R.string.draw;
    private var playerWins = R.string.player_wins;

    //Link the adapter with the layout:
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return games.size
    }

    //Bind ViewHolder to the data:
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(games[position])
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(game: Game) {
            when (game.result) {
                0 -> itemView.tvResult.text = pcWins.toString()
                1 -> itemView.tvResult.text = draw.toString()
                2 -> itemView.tvResult.text = playerWins.toString()
            }
            itemView.ivPlayer.setImageResource(Game.GAME_RES_DRAWABLES_IDS[game.player])
            itemView.ivPc.setImageResource(Game.GAME_RES_DRAWABLES_IDS[game.computer])
            itemView.tvDate.text = game.date.toString()
        }
    }
}