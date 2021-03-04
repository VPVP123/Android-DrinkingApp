package com.github.skosvall.nextlvl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class PlayCardGamesActivity : AppCompatActivity() {
    companion object{
        const val GAME_TO_START = "gameToStart"
        const val RING_OF_FIRE = "ringOfFire"
        const val FUCK_THE_DEALER = "fuckTheDealer"
        const val THREE_TWO_ONE = "threeTwoOne"
    }

    val db = FirebaseFirestore.getInstance()

    lateinit var getCardGames123: DocumentReference
    lateinit var getCardGamesFuckTheDealer: DocumentReference
    lateinit var getCardGamesRingOfFire: DocumentReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_card_games)

        //Set language
        val currentLang = getString(R.string.currentLang)

        getCardGames123 = db.collection("cardGamesData").document("1-2-3").collection(currentLang).document("texts")
        getCardGamesFuckTheDealer = db.collection("cardGamesData").document("fuckTheDealer").collection(currentLang).document("texts")
        getCardGamesRingOfFire = db.collection("cardGamesData").document("ringOfFire").collection(currentLang).document("texts")

        when (intent.getStringExtra(GAME_TO_START)){
            RING_OF_FIRE -> startRingOfFire()
            FUCK_THE_DEALER -> startFuckTheDealer()
            THREE_TWO_ONE -> startThreeTwoOne()
        }
    }

    fun startRingOfFire(){
        //Insert everything from firestore in fragment
        getCardGamesRingOfFire.get()
                .addOnSuccessListener { document ->
                    if(document != null){
                        Log.d("exist", "DocumentSnapshot data: ${document.data}")
                        supportFragmentManager.beginTransaction()
                                .add(R.id.playCardGameFrameLayout, CardGamesFragment.newInstance(document.getString("title") as String,
                                        document.getString("shortDescription") as String,
                                        document.getString("sectionOneTitle") as String,
                                        document.getString("sectionOneText") as String,
                                        document.getString("sectionTwoTitle") as String,
                                        document.getString("sectionTwoText") as String,
                                        document.getString("sectionThreeTitle") as String,
                                        document.getString("sectionThreeText") as String))
                                .commit()
                    }else{
                        Log.d("noExist", "No document found")
                    }
                }
                .addOnFailureListener {exception ->
                    Log.d("errorDB", "get failed with ", exception)

                }
    }
    fun startFuckTheDealer(){
        //Insert everything from firestore in fragment
        getCardGamesFuckTheDealer.get()
                .addOnSuccessListener { document ->
                    if(document != null){
                        Log.d("exist", "DocumentSnapshot data: ${document.data}")

                        supportFragmentManager.beginTransaction()
                                .add(R.id.playCardGameFrameLayout, CardGamesFragment.newInstance(document.getString("title") as String,
                                        document.getString("shortDescription") as String,
                                        document.getString("sectionOneTitle") as String,
                                        document.getString("sectionOneText") as String,
                                        document.getString("sectionTwoTitle") as String,
                                        document.getString("sectionTwoText") as String,
                                        document.getString("sectionThreeTitle") as String,
                                        document.getString("sectionThreeText") as String))
                                .commit()
                    }else{
                        Log.d("noExist", "No document found")
                    }
                }
                .addOnFailureListener {exception ->
                    Log.d("errorDB", "get failed with ", exception)

                }
    }
    fun startThreeTwoOne(){
        //Insert everything from firestore in fragment
        getCardGames123.get()
                .addOnSuccessListener { document ->
                    if(document != null){
                        Log.d("exist", "DocumentSnapshot data: ${document.data}")

                        supportFragmentManager.beginTransaction()
                                .add(R.id.playCardGameFrameLayout, CardGamesFragment.newInstance(document.getString("title") as String,
                                        document.getString("shortDescription") as String,
                                        document.getString("sectionOneTitle") as String,
                                        (document.getString("sectionOneText") as String).replace("\\n", "\n"),
                                        document.getString("sectionTwoTitle") as String,
                                        document.getString("sectionTwoText") as String,
                                        document.getString("sectionThreeTitle") as String,
                                        document.getString("sectionThreeText") as String))
                                .commit()
                    }else{
                        Log.d("noExist", "No document found")
                    }
                }
                .addOnFailureListener {exception ->
                    Log.d("errorDB", "get failed with ", exception)

                }
    }
}