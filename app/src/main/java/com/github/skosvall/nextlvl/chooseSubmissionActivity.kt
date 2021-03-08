package com.github.skosvall.nextlvl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class chooseSubmissionActivity : AppCompatActivity() {

    companion object FirebaseManager {
        val DOR = "Dare or Drink"
        val NHIE = "Never have i ever"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_submission)

        val DOR_Btn = findViewById<Button>(R.id.DOR_submissions_button)
        val neverHaveIEverButton = findViewById<Button>(R.id.neverHaveIEver_submissions_Button)




        DOR_Btn.setOnClickListener {
            val intent = Intent(this, SubmissionListActivity::class.java)
            intent.putExtra("gameType", DOR)
            startActivity(
                intent
            )
        }


        neverHaveIEverButton.setOnClickListener {
            val intent = Intent(this, SubmissionListActivity::class.java)
            intent.putExtra("gameType", NHIE)
            startActivity(
                intent
            )
        }
    }
}