package com.github.skosvall.nextlvl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LvLGamesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lvl_games)

        findViewById<Button>(R.id.beerpongButton).setOnClickListener {
            startGame(PlayLvLGamesActivity.BEERPONG)
        }
        findViewById<Button>(R.id.gasGasButton).setOnClickListener {
            startGame(PlayLvLGamesActivity.GAS_GAS)
        }
        findViewById<Button>(R.id.horseRaceButton).setOnClickListener {
            startGame(PlayLvLGamesActivity.HORSE_RACE)
        }
    }
    private fun startGame(gameToPlay: String){
        val intent = Intent(this, PlayLvLGamesActivity::class.java)
        intent.putExtra(PlayLvLGamesActivity.GAME_TO_START, gameToPlay)
        startActivity(intent)
    }
}