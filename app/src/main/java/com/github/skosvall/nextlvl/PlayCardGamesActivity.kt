package com.github.skosvall.nextlvl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore


class PlayCardGamesActivity : AppCompatActivity() {
    companion object{
        const val GAME_TO_START = "GAME_TO_START"
    }

    val db = FirebaseFirestore.getInstance()

    val getCardGames321 = db.collection("cardGamesData").document("1-2-3").collection("english").document("texts")
    val getCardGamesFuckTheDealer = db.collection("cardGamesData").document("fuckTheDealer").collection("english").document("texts")
    val getCardGamesRingOfFire = db.collection("cardGamesData").document("ringOfFire").collection("english").document("texts")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_card_games)

        when (intent.getStringExtra(GAME_TO_START)){
            "ROF" -> startRingOfFire()
            "FTD" -> startFuckTheDealer()
            "321" -> startThreeTwoOne()
        }
    }

    fun startRingOfFire(){
        //Insert everything from firestore in fragment
        getCardGames321.get()
                .addOnSuccessListener { document ->
                    if(document != null){
                        Log.d("exist", "DocumentSnapshot data: ${document.data}")

                        supportFragmentManager.beginTransaction()
                                .add(R.id.playCardGameFrameLayout, CardGamesFragment.newInstance(document.getString("title") as String,
                                        document.getString("shortDescription") as String,
                                        document.getString("sectionOneText") as String,
                                        document.getString("sectionTwoText") as String,
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
                                        document.getString("sectionOneText") as String,
                                        document.getString("sectionTwoText") as String,
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
        getCardGamesRingOfFire.get()
                .addOnSuccessListener { document ->
                    if(document != null){
                        Log.d("exist", "DocumentSnapshot data: ${document.data}")

                        supportFragmentManager.beginTransaction()
                                .add(R.id.playCardGameFrameLayout, CardGamesFragment.newInstance(document.getString("title") as String,
                                        document.getString("shortDescription") as String,
                                        document.getString("sectionOneText") as String,
                                        document.getString("sectionTwoText") as String,
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