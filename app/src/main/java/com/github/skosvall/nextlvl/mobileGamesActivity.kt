package com.github.skosvall.nextlvl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class mobileGamesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mobile_games)

        val DOR_Btn = findViewById<Button>(R.id.DOR_button)
        val neverHaveIEverButton = findViewById<Button>(R.id.neverHaveIEverButton)

        DOR_Btn.setOnClickListener {
            val intent = Intent(this, PrepareDareOrDrinkActivity::class.java)
            startActivity(
                intent
            )

            val neverHaveIEverButton = findViewById<Button>(R.id.neverHaveIEverButton)
            neverHaveIEverButton.setOnClickListener {
                startActivity(Intent(this, PlayNeverHaveIEverActivity::class.java))
            }

        }
        neverHaveIEverButton.setOnClickListener {
            startActivity(Intent(this, PlayNeverHaveIEverActivity::class.java))
        }
    }
}
