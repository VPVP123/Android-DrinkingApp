package com.github.skosvall.nextlvl

import android.content.Intent
import android.os.Bundle
import android.transition.TransitionManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import com.google.android.material.chip.ChipGroup
import com.google.android.material.chip.Chip


class PrepareDareOrDrinkActivity : AppCompatActivity() {
    companion object {
        const val ADDED_PLAYER_NAMES = "ADDED_PLAYER_NAMES"
    }

    private lateinit var chipGroup: ChipGroup
    private lateinit var addPlayerEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prepare_dare_or_drink)

        if (savedInstanceState == null) {
            val buttonAdd = findViewById<Button>(R.id.add_player_button)
            chipGroup = findViewById(R.id.chip_group)
            val startGameButton = findViewById<Button>(R.id.start_game_button)
            addPlayerEditText = findViewById(R.id.add_player_name)

            buttonAdd.setOnClickListener {
                addChipToChipgroup(addPlayerEditText.text.toString())
                addPlayerEditText.setText("")
            }

            startGameButton.setOnClickListener {
                if (getPlayerNames().isNotEmpty()) {
                    val intent = Intent(this, PlayDareOrDrinkActivity::class.java)
                    intent.putExtra(
                        PlayDareOrDrinkActivity.PLAYER_NAMES,
                        getPlayerNames().toTypedArray()
                    )
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.please_add_players),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        } else {
            savedInstanceState.getStringArray(ADDED_PLAYER_NAMES)?.forEach {
                addChipToChipgroup(it)
            }
        }
    }

    private fun addChipToChipgroup(nameToAdd: String) {
        val chip = Chip(this)
        chip.text = nameToAdd

        chip.isClickable = true
        chip.isCheckable = false

        chip.isCloseIconVisible = true

        chip.setOnCloseIconClickListener {
            TransitionManager.beginDelayedTransition(chipGroup)
            chipGroup.removeView(chip)
        }

        chipGroup.addView(chip)
    }

    private fun getPlayerNames(): MutableList<String> {
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
