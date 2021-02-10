package com.github.skosvall.nextlvl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class PlayNeverHaveIEverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_never_have_i_ever)

        val statements = arrayOf("Never have I ever kissed a stranger",
            "Never have I ever taken a shower selfie",
            "Never have I ever been to a nude beach",
            "Never have I ever been to Spain",
            "Never have I ever slept in the buff",
            "Never have I ever worn speedos",
            "Never have I ever lied about anything",
            "Never have I ever spied on a girl online",
            "Never have I ever been dumped")

        val textView = findViewById<TextView>(R.id.statementTextview)
        textView.text = statements.random()

        val nextButton = findViewById<TextView>(R.id.neverHaveIEverNextButton)
        nextButton.setOnClickListener {
            textView.text = statements.random()
        }
    }
}