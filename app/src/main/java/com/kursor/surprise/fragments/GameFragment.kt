package com.kursor.surprise.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.kursor.surprise.*
import com.kursor.surprise.entities.Deck
import com.kursor.surprise.entities.Territory
import com.kursor.surprise.entities.findByName

class GameFragment : Fragment() {

    private lateinit var territory: Territory
    private lateinit var myButtons: List<Button>
    private lateinit var enemyButtons: List<Button>
    private lateinit var deckButton: Button
    private lateinit var scoreTextView: TextView
    private lateinit var yourPreviousMoveTextView: TextView
    private lateinit var enemyPreviousMoveTextView: TextView
    private val deck = Deck()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val args = arguments
        if (args == null) {
            activity?.onBackPressed()
            return
        }
        val terrName = args.getString(TERR_NAME)
        if (terrName == null) {
            activity?.onBackPressed()
            return
        }
        territory = Tools.territories.findByName(terrName)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_game, LinearLayout(activity), false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        deckButton = view.findViewById<Button>(R.id.deck_btn).apply {
            setOnClickListener {
                processMyMove(CARD_FROM_DECK)
            }
        }
        myButtons = listOf<Button>(
            view.findViewById<Button>(R.id.ally_btn0).apply {
                text = deck.myCards[0].toString()
            },
            view.findViewById<Button>(R.id.ally_btn1).apply {
                text = deck.myCards[1].toString()
            },
            view.findViewById<Button>(R.id.ally_btn2).apply {
                text = deck.myCards[2].toString()
            },
            view.findViewById<Button>(R.id.ally_btn3).apply {
                text = deck.myCards[3].toString()
            },
        ).mapIndexed { index, button ->
            button.setOnClickListener {
                val code = processMyMove(index)
                button.isEnabled = false
                button.text = "${button.text}\nx"
                if (code == VALID_MOVE) processAiMove()
            }
            button
        }

        enemyButtons = listOf<Button>(
            view.findViewById(R.id.enemy_btn0),
            view.findViewById(R.id.enemy_btn1),
            view.findViewById(R.id.enemy_btn2),
            view.findViewById(R.id.enemy_btn3)
        ).map {
            it.isEnabled = false
            it.text = "X"
            it
        }
        scoreTextView = view.findViewById(R.id.score_tv)
        yourPreviousMoveTextView = view.findViewById(R.id.your_prev_move_tv)
        enemyPreviousMoveTextView = view.findViewById(R.id.enemy_prev_move_tv)
    }

    /**
     * @param index index of a button pressed
     * @return returns a code which indicates whether a move was valid of not
     * if a move was invalid function just ignores it and returns code INVALID_MOVE
     * else returns VALID_MOVE
     */
    private fun processMyMove(index: Int): Int {
        val move = deck.myMove(index)
        return if (move != INVALID_MOVE) {
            myButtons.forEach { it.isEnabled = false }
            scoreTextView.text = deck.score.toString()
            yourPreviousMoveTextView.text = move.toString()
            if (!deck.gameContinues) {
                buildMessageYouLost()
            }
            VALID_MOVE
        } else INVALID_MOVE
    }

    private fun processAiMove() {
        val aiMove = deck.aiMove()
        enemyPreviousMoveTextView.text = aiMove.toString()
        scoreTextView.text = deck.score.toString()
        myButtons.forEach { it.isEnabled = true }
        if (!deck.gameContinues) {
            buildMessageYouWon()
        }
    }

    private fun buildMessageYouLost() {
        AlertDialog.Builder(activity)
            .setCancelable(false)
            .setTitle("You lost")
            .setMessage("loh")
            .setPositiveButton("ok") { dialog, which ->
                activity?.onBackPressed()
            }.create().show()
        territory.lostBattle()
    }

    private fun buildMessageYouWon() {
        AlertDialog.Builder(activity)
            .setCancelable(false)
            .setTitle("You won")
            .setMessage("you cul")
            .setPositiveButton("ok") { dialog, which ->
                activity?.onBackPressed()
            }.create().show()
        territory.wonBattle()
    }
}