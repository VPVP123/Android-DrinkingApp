package com.github.skosvall.nextlvl

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class PlayLvLGamesActivity : AppCompatActivity() {
    companion object{
        const val GAME_TO_START = "gameToPlay"
        const val BEERPONG = "beerpong"
        const val GAS_GAS = "gasGas"
        const val HORSE_RACE = "horseRace"
    }

    val db = FirebaseFirestore.getInstance()

    lateinit var getLvLGamesBeerPong: DocumentReference
    lateinit var getLvLGamesGasGas: DocumentReference
    lateinit var getLvLGamesHorseRace: DocumentReference

    lateinit var loadingSpinner: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_lvl_games)

        loadingSpinner = this.findViewById<ProgressBar>(R.id.lvlGamesSpinner)
        loadingSpinner.visibility = View.VISIBLE

        val currentLang = getString(R.string.currentLang)
        getLvLGamesBeerPong = db.collection("lvlGamesData").document("beerpong").collection(currentLang).document("texts")
        getLvLGamesGasGas = db.collection("lvlGamesData").document("gasGas").collection(currentLang).document("texts")
        getLvLGamesHorseRace = db.collection("lvlGamesData").document("horseRace").collection(currentLang).document("texts")

        val gameToPlay = intent.getStringExtra(GAME_TO_START)

        when (intent.getStringExtra(GAME_TO_START)){
            BEERPONG -> startBeerpong()
            GAS_GAS -> startGasGas()
            HORSE_RACE -> startHorseRace()
        }

    }
    private fun startBeerpong(){
        //Insert everything from firestore in fragment
            getLvLGamesBeerPong.get()
                    .addOnSuccessListener { document ->
                        if(document != null){
                            Log.d("exist", "DocumentSnapshot data: ${document.data}")

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
                            loadingSpinner.visibility = View.INVISIBLE
                        }else{
                            Log.d("noExist", "No document found")
                        }
                    }
                    .addOnFailureListener {exception ->
                        Log.d("errorDB", "get failed with ", exception)

                    }
    }
    private fun startGasGas(){
        //Insert everything from firestore in fragment
            getLvLGamesGasGas.get()
                    .addOnSuccessListener { document ->
                        if(document != null) {
                            Log.d("exist", "DocumentSnapshot data: ${document.data}")

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
                            loadingSpinner.visibility = View.INVISIBLE
                        } else {
                            Log.d("noExist", "No document found")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d("errorDB", "get failed with ", exception)
                    }
    }
    private fun startHorseRace(){
        //Insert everything from firestore in fragment
        getLvLGamesHorseRace.get()
                .addOnSuccessListener { document ->
                    if(document != null){
                        Log.d("exist", "DocumentSnapshot data: ${document.data}")

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
                        loadingSpinner.visibility = View.INVISIBLE
                    }else{
                        Log.d("noExist", "No document found")
                    }
                }
                .addOnFailureListener {exception ->
                    Log.d("errorDB", "get failed with ", exception)

                }
    }
}