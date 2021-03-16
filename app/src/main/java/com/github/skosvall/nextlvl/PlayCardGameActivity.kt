package com.github.skosvall.nextlvl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class PlayCardGameActivity : AppCompatActivity() {
    companion object{
        const val CURRENT_GAME = "CURRENT_GAME"
        const val PREVIOUS_LANG = "PREVIOUS_LANG"
        const val GAME_TO_START = "gameToStart"
        const val RING_OF_FIRE = "ringOfFire"
        const val FUCK_THE_DEALER = "fuckTheDealer"
        const val THREE_TWO_ONE = "threeTwoOne"
    }

    private lateinit var currentGame: String
    private lateinit var getCardGames123: DocumentReference
    private lateinit var getCardGamesFuckTheDealer: DocumentReference
    private lateinit var getCardGamesRingOfFire: DocumentReference
    private lateinit var loadingSpinner: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_card_games)

        loadingSpinner = this.findViewById<ProgressBar>(R.id.cardGamesSpinner)
        loadingSpinner.visibility = View.VISIBLE

        val db = FirebaseFirestore.getInstance()

        val currentLang = getString(R.string.currentLang)

        getCardGames123 = db.collection("cardGamesData").document("1-2-3").collection(currentLang).document("texts")
        getCardGamesFuckTheDealer = db.collection("cardGamesData").document("fuckTheDealer").collection(currentLang).document("texts")
        getCardGamesRingOfFire = db.collection("cardGamesData").document("ringOfFire").collection(currentLang).document("texts")

        if(savedInstanceState == null) {
            currentGame = intent.getStringExtra(GAME_TO_START)!!
            startGame(savedInstanceState)
        } else {
            val previousLang = savedInstanceState.getString(PREVIOUS_LANG)
            currentGame = savedInstanceState.getString(CURRENT_GAME) as String
            if(previousLang != currentLang){
                startGame(savedInstanceState)
            }else{
                loadingSpinner.visibility = View.INVISIBLE
            }
        }
    }

    private fun startGame(savedInstanceState: Bundle?){
        when (currentGame) {
            RING_OF_FIRE -> startRingOfFire(savedInstanceState)
            FUCK_THE_DEALER -> startFuckTheDealer(savedInstanceState)
            THREE_TWO_ONE -> startThreeTwoOne(savedInstanceState)
        }
    }

    fun startRingOfFire(savedInstanceState: Bundle?){
        //Insert everything from firestore in fragment
        getCardGamesRingOfFire.get()
            .addOnSuccessListener { document ->
                if(document != null){
                    Log.d("exist", "DocumentSnapshot data: ${document.data}")
                    if(savedInstanceState == null) {
                        supportFragmentManager.beginTransaction()
                            .add(R.id.playCardGameFrameLayout, ringOfFireGameFragment.newInstance((document.getString("title") as String).replace("\\n", "\n"),
                                    (document.getString("shortDescription") as String).replace("\\n", "\n"),
                                    (document.getString("sectionOneTitle") as String).replace("\\n", "\n"),
                                    (document.getString("sectionOneText") as String).replace("\\n", "\n"),
                                    (document.getString("sectionTwoTitle") as String).replace("\\n", "\n"),
                                    (document.getString("sectionTwoText") as String).replace("\\n", "\n"),
                                    (document.getString("sectionThreeTitle") as String).replace("\\n", "\n"),
                                    (document.getString("sectionThreeText") as String).replace("\\n", "\n"),
                                    (document.getString("sectionFourTitle") as String).replace("\\n", "\n"),
                                    (document.getString("sectionFourText") as String).replace("\\n", "\n")))
                            .commit()
                    } else {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.playCardGameFrameLayout, ringOfFireGameFragment.newInstance((document.getString("title") as String).replace("\\n", "\n"),
                                    (document.getString("shortDescription") as String).replace("\\n", "\n"),
                                    (document.getString("sectionOneTitle") as String).replace("\\n", "\n"),
                                    (document.getString("sectionOneText") as String).replace("\\n", "\n"),
                                    (document.getString("sectionTwoTitle") as String).replace("\\n", "\n"),
                                    (document.getString("sectionTwoText") as String).replace("\\n", "\n"),
                                    (document.getString("sectionThreeTitle") as String).replace("\\n", "\n"),
                                    (document.getString("sectionThreeText") as String).replace("\\n", "\n"),
                                    (document.getString("sectionFourTitle") as String).replace("\\n", "\n"),
                                    (document.getString("sectionFourText") as String).replace("\\n", "\n")))
                            .commit()
                    }
                    loadingSpinner.visibility = View.INVISIBLE;
                }else{
                    Log.d("noExist", "No document found")
                }
            }
            .addOnFailureListener {exception ->
                Log.d("errorDB", "get failed with ", exception)

            }
    }
    fun startFuckTheDealer(savedInstanceState: Bundle?){
        //Insert everything from firestore in fragment
        getCardGamesFuckTheDealer.get()
            .addOnSuccessListener { document ->
                if(document != null){
                    Log.d("exist", "DocumentSnapshot data: ${document.data}")
                    if(savedInstanceState == null) {
                        supportFragmentManager.beginTransaction()
                            .add(R.id.playCardGameFrameLayout, CardGamesFragment.newInstance((document.getString("title") as String).replace("\\n", "\n"),
                                    (document.getString("shortDescription") as String).replace("\\n", "\n"),
                                    (document.getString("sectionOneTitle") as String).replace("\\n", "\n"),
                                    (document.getString("sectionOneText") as String).replace("\\n", "\n"),
                                    (document.getString("sectionTwoTitle") as String).replace("\\n", "\n"),
                                    (document.getString("sectionTwoText") as String).replace("\\n", "\n"),
                                    (document.getString("sectionThreeTitle") as String).replace("\\n", "\n"),
                                    (document.getString("sectionThreeText") as String).replace("\\n", "\n")))
                            .commit()
                    } else {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.playCardGameFrameLayout, CardGamesFragment.newInstance((document.getString("title") as String).replace("\\n", "\n"),
                                    (document.getString("shortDescription") as String).replace("\\n", "\n"),
                                    (document.getString("sectionOneTitle") as String).replace("\\n", "\n"),
                                    (document.getString("sectionOneText") as String).replace("\\n", "\n"),
                                    (document.getString("sectionTwoTitle") as String).replace("\\n", "\n"),
                                    (document.getString("sectionTwoText") as String).replace("\\n", "\n"),
                                    (document.getString("sectionThreeTitle") as String).replace("\\n", "\n"),
                                    (document.getString("sectionThreeText") as String).replace("\\n", "\n")))
                            .commit()
                    }
                    loadingSpinner.visibility = View.INVISIBLE;
                }else{
                    Log.d("noExist", "No document found")
                }
            }
            .addOnFailureListener {exception ->
                Log.d("errorDB", "get failed with ", exception)

            }
    }
    fun startThreeTwoOne(savedInstanceState: Bundle?){
        //Insert everything from firestore in fragment
        getCardGames123.get()
            .addOnSuccessListener { document ->
                if(document != null){
                    Log.d("exist", "DocumentSnapshot data: ${document.data}")
                    if(savedInstanceState == null) {
                        supportFragmentManager.beginTransaction()
                            .add(R.id.playCardGameFrameLayout, CardGamesFragment.newInstance((document.getString("title") as String).replace("\\n", "\n"),
                                    (document.getString("shortDescription") as String).replace("\\n", "\n"),
                                    (document.getString("sectionOneTitle") as String).replace("\\n", "\n"),
                                    (document.getString("sectionOneText") as String).replace("\\n", "\n"),
                                    (document.getString("sectionTwoTitle") as String).replace("\\n", "\n"),
                                    (document.getString("sectionTwoText") as String).replace("\\n", "\n"),
                                    (document.getString("sectionThreeTitle") as String).replace("\\n", "\n"),
                                    (document.getString("sectionThreeText") as String).replace("\\n", "\n")))
                            .commit()
                    } else {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.playCardGameFrameLayout, CardGamesFragment.newInstance((document.getString("title") as String).replace("\\n", "\n"),
                                    (document.getString("shortDescription") as String).replace("\\n", "\n"),
                                    (document.getString("sectionOneTitle") as String).replace("\\n", "\n"),
                                    (document.getString("sectionOneText") as String).replace("\\n", "\n"),
                                    (document.getString("sectionTwoTitle") as String).replace("\\n", "\n"),
                                    (document.getString("sectionTwoText") as String).replace("\\n", "\n"),
                                    (document.getString("sectionThreeTitle") as String).replace("\\n", "\n"),
                                    (document.getString("sectionThreeText") as String).replace("\\n", "\n")))
                            .commit()
                    }
                    loadingSpinner.visibility = View.INVISIBLE;
                }else{
                    Log.d("noExist", "No document found")
                }
            }
            .addOnFailureListener {exception ->
                Log.d("errorDB", "get failed with ", exception)

            }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(CURRENT_GAME, currentGame)
        outState.putString(PREVIOUS_LANG, getString(R.string.currentLang))
    }
}