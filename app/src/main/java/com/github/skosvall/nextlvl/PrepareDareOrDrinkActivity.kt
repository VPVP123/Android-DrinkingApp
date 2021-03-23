package com.github.skosvall.nextlvl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import com.google.android.material.chip.ChipGroup
import com.google.android.material.chip.Chip
import kotlin.random.Random


class PrepareDareOrDrinkActivity : AppCompatActivity() {
    companion object{
        const val ADDED_PLAYER_NAMES = "ADDED_PLAYER_NAMES"
    }
    private lateinit var chipGroup: ChipGroup
    private lateinit var addPlayerEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prepare_dare_or_drink)

        if(savedInstanceState == null) {
            val buttonAdd = findViewById<Button>(R.id.addButton) as Button
            chipGroup = findViewById<ChipGroup>(R.id.chipGroup) as ChipGroup
            val startGameButton = findViewById<Button>(R.id.startGameButton) as Button
            addPlayerEditText = findViewById<EditText>(R.id.playerName) as EditText

            buttonAdd.setOnClickListener {
                addChipToChipgroup(addPlayerEditText.text.toString())
                addPlayerEditText.setText("")
            }

            startGameButton.setOnClickListener {
                val intent = Intent(this, PlayDareOrDrinkActivity::class.java)
                intent.putExtra(PlayDareOrDrinkActivity.PLAYER_NAMES, getPlayerNames().toTypedArray())
                startActivity(intent)
            }
        }else{
            savedInstanceState.getStringArray(ADDED_PLAYER_NAMES)?.forEach {
                addChipToChipgroup(it)
            }
        }
    }

    private fun addChipToChipgroup(nameToAdd: String){
        // Initialize a new chip instance
        val chip = Chip(this)
        chip.text = nameToAdd

        // Set the chip icon
        //chip.chipIcon = ContextCompat.getDrawable(this,R.drawable.ic_action_android)
        //chip.setChipIconTintResource(R.color.abc_search_url_text)

        // Make the chip clickable
        chip.isClickable = true
        chip.isCheckable = false

        // Show the chip icon in chip
        chip.isCloseIconVisible = true

        // Set chip close icon click listener
        chip.setOnCloseIconClickListener{
            // Smoothly remove chip from chip group
            TransitionManager.beginDelayedTransition(chipGroup)
            chipGroup.removeView(chip)
        }

        // Finally, add the chip to chip group
        chipGroup.addView(chip)
    }

    private fun getPlayerNames() : MutableList<String>{
        val playerNames = mutableListOf<String>()
        chipGroup.forEach {
            playerNames.add((it as Chip).text.toString())
        }
        return playerNames
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArray("ADDED_PLAYER_NAMES", getPlayerNames().toTypedArray())
    }
}
