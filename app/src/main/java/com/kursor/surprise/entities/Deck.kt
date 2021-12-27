package com.kursor.surprise.entities

import com.kursor.surprise.CARD_FROM_DECK
import com.kursor.surprise.INVALID_MOVE
import com.kursor.surprise.MAX_SCORE
import com.kursor.surprise.USED

class Deck {

    private val deck: IntArray =
        IntArray(36).mapIndexed { index, i -> index % 4 }.shuffled().toIntArray()
    val myCards = deck.toList().subList(0, 4).toIntArray()
    private val enemyCards = deck.toList().subList(4, 8).toMutableList()
    private var count = 7
    var score = 0
        private set

    val gameContinues: Boolean
        get() = score <= MAX_SCORE

    fun useCardFromDeck(): Int {
        count++
        score += deck[count]
        return deck[count]
    }


    fun aiMove(): Int {
        if (3 + score < MAX_SCORE || enemyCards.isEmpty()) return useCardFromDeck()
        enemyCards.sorted().reversed().forEachIndexed { index, i ->
            if (i + score < MAX_SCORE) {
                enemyCards.removeAt(index)
                return i
            }
        }
        return enemyCards[0]
    }

    fun myMove(index: Int): Int {
        val move: Int
        if (index == CARD_FROM_DECK) {
            move = useCardFromDeck()
            score += move
            return move
        }
        if (myCards[index] == USED) return INVALID_MOVE
        move = myCards[index]
        score += move
        myCards[index] = USED
        return move
    }

}