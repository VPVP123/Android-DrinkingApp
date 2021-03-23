package com.github.skosvall.nextlvl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class PlayDareOrDrinkActivity : AppCompatActivity() {
    companion object{
        const val PLAYER_NAMES = "PLAYER_NAMES"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_dare_or_drink)

        if(savedInstanceState == null) {
            val players = intent.getStringArrayExtra(PLAYER_NAMES) as Array<String>
            supportFragmentManager
                .beginTransaction()
                .add(R.id.dare_or_drink_frame_layout, PlayMobileGamesFragment.newInstance(players))
                .commit()
        }
    }
}