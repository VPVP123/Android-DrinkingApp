package com.github.skosvall.nextlvl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PlayLvLGamesActivity : AppCompatActivity() {
    companion object{
        const val GAME_TO_START = "gameToPlay"
        const val BEERPONG = "beerpong"
        const val GAS_GAS = "gasGas"
        const val HORSE_RACE = "horseRace"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_lvl_games)

        val gameToPlay = intent.getStringExtra(GAME_TO_START)

        when (intent.getStringExtra(GAME_TO_START)){
            BEERPONG -> startBeerpong()
            GAS_GAS -> startGasGas()
            HORSE_RACE -> startHorseRace()
        }
    }
    private fun startBeerpong(){
        supportFragmentManager.beginTransaction()
            .add(R.id.PlayLvLGamesFrameLayout, LvLGameFragment.newInstance("Beerpong",
                "Beerpong is a nice and chill yet competitive drinking game, pretty much the perfect game to get the night started.",
                "12 plastic cups\nTwo or more pong balls\nBeverage of your choice\nA table to play on",
                "Place 6 cups at each end of the table in a 3-2-1 flat pyramid, with the row containing 3 cups closest to the edge of the table, so that the pyramid is pointing towards the pyramid on the other side. Fill the cups about ⅓-½ with the beverage of your choice and split up into two teams, one team per pyramid of cups.",
                "Choose one team that starts, the players should now try to throw the balls so that they land in one of the enemy team cups. If a ball is sunk into a cup and doesn't bounce out again, the enemy team has to drink the contents of the cup that was hit. Make sure to always release the ball behind the table when throwing to keep it nice and fair. So no leaning over the edge of the table to get closer! The team that has drunk all of their 6 cups first has lost. And before you start, make sure to get some sweet music going in the background.Choose one team that starts, the players should now try to throw the balls so that they land in one of the enemy team cups. If a ball is sunk into a cup and doesn't bounce out again, the enemy team has to drink the contents of the cup that was hit. Make sure to always release the ball behind the table when throwing to keep it nice and fair. So no leaning over the edge of the table to get closer! The team that has drunk all of their 6 cups first has lost. And before you start, make sure to get some sweet music going in the background.Choose one team that starts, the players should now try to throw the balls so that they land in one of the enemy team cups. If a ball is sunk into a cup and doesn't bounce out again, the enemy team has to drink the contents of the cup that was hit. Make sure to always release the ball behind the table when throwing to keep it nice and fair. So no leaning over the edge of the table to get closer! The team that has drunk all of their 6 cups first has lost. And before you start, make sure to get some sweet music going in the background."))
            .commit()
    }
    private fun startGasGas(){
        /*supportFragmentManager.beginTransaction()
            .add(R.id.playCardGameFrameLayout, LvLGameFragment.newInstance())
            .commit()*/
    }
    private fun startHorseRace(){
        /*supportFragmentManager.beginTransaction()
            .add(R.id.playCardGameFrameLayout, LvLGameFragment.newInstance("3-2-1",
                "3-2-1 is a card drinking game where players nominate each other to drink while taking turns to flip cards from the pyramid over.",
                "A card deck\nA surface to play on",
                "Set up the cards into a flat pyramid shape 4-3-2-1 on the table. Then hand out three cards to each player. Then make sure to choose one person who will act as a human timer.",
                "Start with the first row, containing four cards. When you have read the instructions through, you will flip one of the cards over Once the card has been flipped, anyone can place a card on top if it is either the same suit or the same value. When placing a card, you say the name of a person in the group, the person whose name is said last gets to drink.  After flipping the first card, someone in the group will act as a timer, counting down from 3. Each time a new card is placed, the timer resets, so after a new card has been placed, all players have three seconds to place a card. When the person counting reaches 0, the round is over and the person whose name was said last will drink as many sips as there are cards in the pile. Before flipping the next card, make sure every player has 3 cards on hand.  On the first row of cards, only the last-named person drinks.  On the second row, containing three cards, the person to the left of the last-named person drinks as well.  On the third row, both people sitting next to the last-named person drink as well.  On the fourth row, everyone has to drink except the person who places the last card, which means saying a name each time is not necessary in this round."))
            .commit()*/
    }
}