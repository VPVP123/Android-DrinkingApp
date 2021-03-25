package com.github.skosvall.nextlvl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class PlayCardGameActivity : AppCompatActivity() {
    companion object {
        const val CURRENT_GAME = "CURRENT_GAME"
        const val PREVIOUS_LANG = "PREVIOUS_LANG"
        const val GAME_TO_START = "gameToStart"
        const val RING_OF_FIRE = "ringOfFire"
        const val FUCK_THE_DEALER = "fuckTheDealer"
        const val THREE_TWO_ONE = "threeTwoOne"
    }

    private lateinit var currentGame: String
    private lateinit var getCardGames321: DocumentReference
    private lateinit var getCardGamesFuckTheDealer: DocumentReference
    private lateinit var getCardGamesRingOfFire: DocumentReference
    private lateinit var loadingSpinner: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_card_games)

        loadingSpinner = this.findViewById(R.id.card_games_spinner)
        loadingSpinner.visibility = View.VISIBLE

        val db = FirebaseFirestore.getInstance()

        val currentLang = getString(R.string.current_lang)

        getCardGames321 = db.collection("cardGamesData").document("1-2-3").collection(currentLang)
            .document("texts")
        getCardGamesFuckTheDealer =
            db.collection("cardGamesData").document("fuckTheDealer").collection(currentLang)
                .document("texts")
        getCardGamesRingOfFire =
            db.collection("cardGamesData").document("ringOfFire").collection(currentLang)
                .document("texts")

        if (savedInstanceState == null) {
            currentGame = intent.getStringExtra(GAME_TO_START)!!
            startGame(savedInstanceState)
        } else {
            val previousLang = savedInstanceState.getString(PREVIOUS_LANG)
            currentGame = savedInstanceState.getString(CURRENT_GAME) as String
            if (previousLang != currentLang) {
                startGame(savedInstanceState)
            } else {
                loadingSpinner.visibility = View.INVISIBLE
            }
        }
    }

    private fun startGame(savedInstanceState: Bundle?) {
        when (currentGame) {
            RING_OF_FIRE -> startRingOfFire(savedInstanceState)
            FUCK_THE_DEALER -> startFuckTheDealer(savedInstanceState)
            THREE_TWO_ONE -> startThreeTwoOne(savedInstanceState)
        }
    }

    private fun startRingOfFire(savedInstanceState: Bundle?) {
        getCardGamesRingOfFire.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    if (savedInstanceState == null) {
                        supportFragmentManager.beginTransaction()
                            .add(
                                R.id.playCardGameFrameLayout, RingOfFireGameFragment.newInstance(
                                    (document.getString("title") as String).replace("\\n", "\n"),
                                    (document.getString("shortDescription") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionOneTitle") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionOneText") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionTwoTitle") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionTwoText") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionThreeTitle") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionThreeText") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionFourTitle") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionFourText") as String).replace(
                                        "\\n",
                                        "\n"
                                    )
                                )
                            )
                            .commit()
                    } else {
                        supportFragmentManager.beginTransaction()
                            .replace(
                                R.id.playCardGameFrameLayout, RingOfFireGameFragment.newInstance(
                                    (document.getString("title") as String).replace("\\n", "\n"),
                                    (document.getString("shortDescription") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionOneTitle") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionOneText") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionTwoTitle") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionTwoText") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionThreeTitle") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionThreeText") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionFourTitle") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionFourText") as String).replace(
                                        "\\n",
                                        "\n"
                                    )
                                )
                            )
                            .commit()
                    }
                    loadingSpinner.visibility = View.INVISIBLE;
                } else {
                    displayError()
                }
            }
            .addOnFailureListener { exception ->
                displayError()
            }
    }

    private fun startFuckTheDealer(savedInstanceState: Bundle?) {
        getCardGamesFuckTheDealer.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    if (savedInstanceState == null) {
                        supportFragmentManager.beginTransaction()
                            .add(
                                R.id.playCardGameFrameLayout, CardGamesFragment.newInstance(
                                    (document.getString("title") as String).replace("\\n", "\n"),
                                    (document.getString("shortDescription") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionOneTitle") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionOneText") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionTwoTitle") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionTwoText") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionThreeTitle") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionThreeText") as String).replace(
                                        "\\n",
                                        "\n"
                                    )
                                )
                            )
                            .commit()
                    } else {
                        supportFragmentManager.beginTransaction()
                            .replace(
                                R.id.playCardGameFrameLayout, CardGamesFragment.newInstance(
                                    (document.getString("title") as String).replace("\\n", "\n"),
                                    (document.getString("shortDescription") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionOneTitle") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionOneText") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionTwoTitle") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionTwoText") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionThreeTitle") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionThreeText") as String).replace(
                                        "\\n",
                                        "\n"
                                    )
                                )
                            )
                            .commit()
                    }
                    loadingSpinner.visibility = View.INVISIBLE;
                } else {
                    displayError()
                }
            }
            .addOnFailureListener { exception ->
                displayError()
            }
    }

    private fun startThreeTwoOne(savedInstanceState: Bundle?) {
        getCardGames321.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    if (savedInstanceState == null) {
                        supportFragmentManager.beginTransaction()
                            .add(
                                R.id.playCardGameFrameLayout, CardGamesFragment.newInstance(
                                    (document.getString("title") as String).replace("\\n", "\n"),
                                    (document.getString("shortDescription") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionOneTitle") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionOneText") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionTwoTitle") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionTwoText") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionThreeTitle") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionThreeText") as String).replace(
                                        "\\n",
                                        "\n"
                                    )
                                )
                            )
                            .commit()
                    } else {
                        supportFragmentManager.beginTransaction()
                            .replace(
                                R.id.playCardGameFrameLayout, CardGamesFragment.newInstance(
                                    (document.getString("title") as String).replace("\\n", "\n"),
                                    (document.getString("shortDescription") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionOneTitle") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionOneText") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionTwoTitle") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionTwoText") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionThreeTitle") as String).replace(
                                        "\\n",
                                        "\n"
                                    ),
                                    (document.getString("sectionThreeText") as String).replace(
                                        "\\n",
                                        "\n"
                                    )
                                )
                            )
                            .commit()
                    }
                    loadingSpinner.visibility = View.INVISIBLE;
                } else {
                    displayError()
                }
            }
            .addOnFailureListener { _ ->
                displayError()
            }
    }

    private fun displayError() {
        Toast.makeText(applicationContext, getString(R.string.db_error_message), Toast.LENGTH_LONG)
            .show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(CURRENT_GAME, currentGame)
        outState.putString(PREVIOUS_LANG, getString(R.string.current_lang))
    }
}