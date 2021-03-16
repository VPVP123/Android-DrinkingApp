package com.github.skosvall.nextlvl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class PlayLvLGameActivity : AppCompatActivity() {
    companion object{
        const val CURRENT_GAME = "CURRENT_GAME"
        const val PREVIOUS_LANG = "PREVIOUS_LANG"
        const val GAME_TO_START = "gameToPlay"
        const val BEERPONG = "beerpong"
        const val GAS_GAS = "gasGas"
        const val HORSE_RACE = "horseRace"
    }

    private lateinit var currentGame: String
    private lateinit var getLvLGamesBeerPong: DocumentReference
    private lateinit var getLvLGamesGasGas: DocumentReference
    private lateinit var getLvLGamesHorseRace: DocumentReference
    private lateinit var loadingSpinner: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_lvl_games)

        loadingSpinner = this.findViewById<ProgressBar>(R.id.lvlGamesSpinner)

        val db = FirebaseFirestore.getInstance()

        val currentLang = getString(R.string.currentLang)

        getLvLGamesBeerPong = db.collection("lvlGamesData").document("beerpong").collection(currentLang).document("texts")
        getLvLGamesGasGas = db.collection("lvlGamesData").document("gasGas").collection(currentLang).document("texts")
        getLvLGamesHorseRace = db.collection("lvlGamesData").document("horseRace").collection(currentLang).document("texts")

        if(savedInstanceState == null) {
            loadingSpinner.visibility = View.VISIBLE

            currentGame = intent.getStringExtra(GAME_TO_START) as String

            startGame(savedInstanceState)
        }else{
            val previousLang = savedInstanceState.getString(PREVIOUS_LANG)

            currentGame = savedInstanceState.getString(PlayCardGameActivity.CURRENT_GAME) as String

            if(previousLang != currentLang){
                startGame(savedInstanceState)
            }else{
                loadingSpinner.visibility = View.INVISIBLE
            }
        }
    }

    private fun startGame(savedInstanceState: Bundle?){
        when (currentGame) {
            BEERPONG -> startBeerpong(savedInstanceState)
            GAS_GAS -> startGasGas(savedInstanceState)
            HORSE_RACE -> startHorseRace(savedInstanceState)
        }
    }

    private fun startBeerpong(savedInstanceState: Bundle?){
        //Insert everything from firestore in fragment
        getLvLGamesBeerPong.get()
            .addOnSuccessListener { document ->
                if(document != null){
                    Log.d("exist", "DocumentSnapshot data: ${document.data}")
                    if(savedInstanceState == null) {
                        supportFragmentManager.beginTransaction()
                            .add(R.id.PlayLvLGamesFrameLayout, LvLGameFragment.newInstance((document.getString("title") as String).replace("\\n", "\n"),
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
                            .replace(R.id.PlayLvLGamesFrameLayout, LvLGameFragment.newInstance((document.getString("title") as String).replace("\\n", "\n"),
                                    (document.getString("shortDescription") as String).replace("\\n", "\n"),
                                    (document.getString("sectionOneTitle") as String).replace("\\n", "\n"),
                                    (document.getString("sectionOneText") as String).replace("\\n", "\n"),
                                    (document.getString("sectionTwoTitle") as String).replace("\\n", "\n"),
                                    (document.getString("sectionTwoText") as String).replace("\\n", "\n"),
                                    (document.getString("sectionThreeTitle") as String).replace("\\n", "\n"),
                                    (document.getString("sectionThreeText") as String).replace("\\n", "\n")))
                            .commit()
                    }
                    loadingSpinner.visibility = View.INVISIBLE
                }else{
                    Log.d("noExist", "No document found")
                }
            }
            .addOnFailureListener {exception ->
                Log.d("errorDB", "get failed with ", exception)

            }
    }
    private fun startGasGas(savedInstanceState: Bundle?){
        //Insert everything from firestore in fragment
        getLvLGamesGasGas.get()
                .addOnSuccessListener { document ->
                    if(document != null) {
                        Log.d("exist", "DocumentSnapshot data: ${document.data}")
                        if(savedInstanceState == null) {
                            supportFragmentManager.beginTransaction()
                                .add(R.id.PlayLvLGamesFrameLayout, LvLGameFragment.newInstance((document.getString("title") as String).replace("\\n", "\n"),
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
                                .replace(R.id.PlayLvLGamesFrameLayout, LvLGameFragment.newInstance((document.getString("title") as String).replace("\\n", "\n"),
                                    (document.getString("shortDescription") as String).replace("\\n", "\n"),
                                    (document.getString("sectionOneTitle") as String).replace("\\n", "\n"),
                                    (document.getString("sectionOneText") as String).replace("\\n", "\n"),
                                    (document.getString("sectionTwoTitle") as String).replace("\\n", "\n"),
                                    (document.getString("sectionTwoText") as String).replace("\\n", "\n"),
                                    (document.getString("sectionThreeTitle") as String).replace("\\n", "\n"),
                                    (document.getString("sectionThreeText") as String).replace("\\n", "\n")))
                                .commit()
                        }
                        loadingSpinner.visibility = View.INVISIBLE
                    } else {
                        Log.d("noExist", "No document found")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("errorDB", "get failed with ", exception)
                }
    }
    private fun startHorseRace(savedInstanceState: Bundle?){
        //Insert everything from firestore in fragment
        getLvLGamesHorseRace.get()
            .addOnSuccessListener { document ->
                if(document != null){
                    Log.d("exist", "DocumentSnapshot data: ${document.data}")
                    if(savedInstanceState == null) {
                        supportFragmentManager.beginTransaction()
                            .add(R.id.PlayLvLGamesFrameLayout, LvLGameFragment.newInstance((document.getString("title") as String).replace("\\n", "\n"),
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
                            .replace(R.id.PlayLvLGamesFrameLayout, LvLGameFragment.newInstance((document.getString("title") as String).replace("\\n", "\n"),
                                (document.getString("shortDescription") as String).replace("\\n", "\n"),
                                (document.getString("sectionOneTitle") as String).replace("\\n", "\n"),
                                (document.getString("sectionOneText") as String).replace("\\n", "\n"),
                                (document.getString("sectionTwoTitle") as String).replace("\\n", "\n"),
                                (document.getString("sectionTwoText") as String).replace("\\n", "\n"),
                                (document.getString("sectionThreeTitle") as String).replace("\\n", "\n"),
                                (document.getString("sectionThreeText") as String).replace("\\n", "\n")))
                            .commit()
                    }
                    loadingSpinner.visibility = View.INVISIBLE
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