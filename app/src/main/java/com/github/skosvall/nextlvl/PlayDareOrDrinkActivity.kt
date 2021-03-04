package com.github.skosvall.nextlvl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class PlayDareOrDrinkActivity : AppCompatActivity() {
    lateinit var players: Array<String>
    companion object{
        const val PLAYER_NAMES = "PLAYER_NAMES"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_dare_or_drink)

        if(savedInstanceState == null) {
            players = intent.getStringArrayExtra(PLAYER_NAMES) as Array<String>
        }else{
            players = savedInstanceState.getStringArray(PLAYER_NAMES) as Array<String>
        }

        supportFragmentManager
            .beginTransaction()
            .add(R.id.dareOrDrinkFrameLayout, PlayMobileGamesFragment())
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArray(PLAYER_NAMES, players)
    }
}