package com.github.skosvall.nextlvl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val mobileGamesBtn = findViewById<Button>(R.id.button)
        val cardGamesBtn = findViewById<Button>(R.id.button2)
        val lvlGamesBtn = findViewById<Button>(R.id.button3)

        mobileGamesBtn.setOnClickListener {
            val intent = Intent(this, mobileGamesActivity::class.java)
            startActivity(
                intent
            )
        }

        cardGamesBtn.setOnClickListener {
            val intent = Intent(this, cardGamesActivity::class.java)
            startActivity(
                intent
            )
        }

        lvlGamesBtn.setOnClickListener {
            val intent = Intent(this, lvlGamesActivity::class.java)
            startActivity(
                intent
            )
        }




    }
}