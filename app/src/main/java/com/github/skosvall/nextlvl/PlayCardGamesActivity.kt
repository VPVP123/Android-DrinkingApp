package com.github.skosvall.nextlvl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle



class PlayCardGamesActivity : AppCompatActivity() {
    companion object{
        const val GAME_TO_START = "gameToStart"
        const val RING_OF_FIRE = "ringOfFire"
        const val FUCK_THE_DEALER = "fuckTheDealer"
        const val THREE_TWO_ONE = "threeTwoOne"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_card_games)

        when (intent.getStringExtra(GAME_TO_START)){
            RING_OF_FIRE -> startRingOfFire()
            FUCK_THE_DEALER -> startFuckTheDealer()
            THREE_TWO_ONE -> startThreeTwoOne()
        }
    }

    fun startRingOfFire(){
        supportFragmentManager.beginTransaction()
            .add(R.id.playCardGameFrameLayout, CardGamesFragment.newInstance("3-2-1",
                "3-2-1 is a card drinking game where players nominate each other to drink while taking turns to flip cards from the pyramid over.",
                "A card deck\nA surface to play on",
                "Set up the cards into a flat pyramid shape 4-3-2-1 on the table. Then hand out three cards to each player. Then make sure to choose one person who will act as a human timer.",
                "Start with the first row, containing four cards. When you have read the instructions through, you will flip one of the cards over Once the card has been flipped, anyone can place a card on top if it is either the same suit or the same value. When placing a card, you say the name of a person in the group, the person whose name is said last gets to drink.  After flipping the first card, someone in the group will act as a timer, counting down from 3. Each time a new card is placed, the timer resets, so after a new card has been placed, all players have three seconds to place a card. When the person counting reaches 0, the round is over and the person whose name was said last will drink as many sips as there are cards in the pile. Before flipping the next card, make sure every player has 3 cards on hand.  On the first row of cards, only the last-named person drinks.  On the second row, containing three cards, the person to the left of the last-named person drinks as well.  On the third row, both people sitting next to the last-named person drink as well.  On the fourth row, everyone has to drink except the person who places the last card, which means saying a name each time is not necessary in this round."))
            .commit()
    }
    fun startFuckTheDealer(){
        supportFragmentManager.beginTransaction()
            .add(R.id.playCardGameFrameLayout, CardGamesFragment.newInstance("3-2-1",
                "3-2-1 is a card drinking game where players nominate each other to drink while taking turns to flip cards from the pyramid over.",
                "A card deck\nA surface to play on",
                "Set up the cards into a flat pyramid shape 4-3-2-1 on the table. Then hand out three cards to each player. Then make sure to choose one person who will act as a human timer.",
                "Start with the first row, containing four cards. When you have read the instructions through, you will flip one of the cards over Once the card has been flipped, anyone can place a card on top if it is either the same suit or the same value. When placing a card, you say the name of a person in the group, the person whose name is said last gets to drink.  After flipping the first card, someone in the group will act as a timer, counting down from 3. Each time a new card is placed, the timer resets, so after a new card has been placed, all players have three seconds to place a card. When the person counting reaches 0, the round is over and the person whose name was said last will drink as many sips as there are cards in the pile. Before flipping the next card, make sure every player has 3 cards on hand.  On the first row of cards, only the last-named person drinks.  On the second row, containing three cards, the person to the left of the last-named person drinks as well.  On the third row, both people sitting next to the last-named person drink as well.  On the fourth row, everyone has to drink except the person who places the last card, which means saying a name each time is not necessary in this round."))
            .commit()
    }
    fun startThreeTwoOne(){
        supportFragmentManager.beginTransaction()
            .add(R.id.playCardGameFrameLayout, CardGamesFragment.newInstance("3-2-1",
                "3-2-1 is a card drinking game where players nominate each other to drink while taking turns to flip cards from the pyramid over.",
                "A card deck\nA surface to play on",
                "Set up the cards into a flat pyramid shape 4-3-2-1 on the table. Then hand out three cards to each player. Then make sure to choose one person who will act as a human timer.",
                "Start with the first row, containing four cards. When you have read the instructions through, you will flip one of the cards over Once the card has been flipped, anyone can place a card on top if it is either the same suit or the same value. When placing a card, you say the name of a person in the group, the person whose name is said last gets to drink.  After flipping the first card, someone in the group will act as a timer, counting down from 3. Each time a new card is placed, the timer resets, so after a new card has been placed, all players have three seconds to place a card. When the person counting reaches 0, the round is over and the person whose name was said last will drink as many sips as there are cards in the pile. Before flipping the next card, make sure every player has 3 cards on hand.  On the first row of cards, only the last-named person drinks.  On the second row, containing three cards, the person to the left of the last-named person drinks as well.  On the third row, both people sitting next to the last-named person drink as well.  On the fourth row, everyone has to drink except the person who places the last card, which means saying a name each time is not necessary in this round."))
            .commit()
    }
}