package com.github.skosvall.nextlvl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class CardGamesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_games)

        val fuckTheDealerButton = findViewById<Button>(R.id.fuckTheDealerButton)
        val ringOfFireButton = findViewById<Button>(R.id.ringOfFireButton)
        val threeTwoOneButton = findViewById<Button>(R.id.threeTwoOneButton)

        fuckTheDealerButton.setOnClickListener {
            val intent = Intent(this, PlayCardGamesActivity::class.java)
            intent.putExtra(PlayCardGamesActivity.GAME_TO_START, "FTD")
            startActivity(intent)
        }

        ringOfFireButton.setOnClickListener {
            val intent = Intent(this, PlayCardGamesActivity::class.java)
            intent.putExtra(PlayCardGamesActivity.GAME_TO_START, "ROF")
            startActivity(intent)
        }

        threeTwoOneButton.setOnClickListener {
            val intent = Intent(this, PlayCardGamesActivity::class.java)
            intent.putExtra(PlayCardGamesActivity.GAME_TO_START, "321")
            startActivity(intent)
        }
    }
}