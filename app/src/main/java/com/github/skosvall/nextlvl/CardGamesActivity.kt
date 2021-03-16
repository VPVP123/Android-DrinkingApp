package com.github.skosvall.nextlvl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class CardGamesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_games)

        findViewById<Button>(R.id.fuckTheDealerButton).setOnClickListener {
            startGame(PlayCardGameActivity.FUCK_THE_DEALER)
        }
        findViewById<Button>(R.id.ringOfFireButton).setOnClickListener {
            startGame(PlayCardGameActivity.RING_OF_FIRE)
        }
        findViewById<Button>(R.id.threeTwoOneButton).setOnClickListener {
            startGame(PlayCardGameActivity.THREE_TWO_ONE)
        }
    }

    private fun startGame(gameToStart: String){
        val intent = Intent(this, PlayCardGameActivity::class.java)
        intent.putExtra(PlayCardGameActivity.GAME_TO_START, gameToStart)
        startActivity(intent)
    }
}