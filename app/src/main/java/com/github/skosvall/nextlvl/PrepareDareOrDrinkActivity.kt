package com.github.skosvall.nextlvl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import com.google.android.material.chip.ChipGroup
import com.google.android.material.chip.Chip
import kotlin.random.Random


class PrepareDareOrDrinkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prepare_dare_or_drink)

        val buttonAdd = findViewById<Button>(R.id.addButton) as Button
        val chipGroup = findViewById<ChipGroup>(R.id.chipGroup) as ChipGroup
        val startGameButton = findViewById<Button>(R.id.startGameButton) as Button

        buttonAdd.setOnClickListener{
            // Initialize a new chip instance
            val playerNameText = findViewById<EditText>(R.id.playerName) as EditText

            val chip = Chip(this)
            chip.text = playerNameText.text.toString()

            // Set the chip icon
            //chip.chipIcon = ContextCompat.getDrawable(this,R.drawable.ic_action_android)
            //chip.setChipIconTintResource(R.color.abc_search_url_text)

            // Make the chip clickable
            chip.isClickable = true
            chip.isCheckable = false

            // Show the chip icon in chip
            chip.isCloseIconVisible = true

            // Set the chip click listener
            chip.setOnClickListener{
                toast("Clicked: ${chip.text}")
            }

            // Set chip close icon click listener
            chip.setOnCloseIconClickListener{
                // Smoothly remove chip from chip group
                TransitionManager.beginDelayedTransition(chipGroup)
                chipGroup.removeView(chip)
            }

            // Finally, add the chip to chip group
            chipGroup.addView(chip)
        }

        startGameButton.setOnClickListener {
            val playerNames = mutableListOf<String>()
            chipGroup.forEach {
                playerNames.add((it as Chip).text.toString())
            }
            val intent = Intent(this, PlayDareOrDrinkActivity::class.java)
            intent.putExtra(PlayDareOrDrinkActivity.PLAYER_NAMES, playerNames.toTypedArray())
            startActivity(intent)
        }
    }

}




fun Context.toast(message:String)=
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
