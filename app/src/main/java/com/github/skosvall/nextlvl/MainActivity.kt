package com.github.skosvall.nextlvl

import android.Manifest
import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class MainActivity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")

    private lateinit var context: Context
    private lateinit var alarmManager: AlarmManager
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var country = ""
    val PERMISSION_ID = 1010

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val db = FirebaseFirestore.getInstance()

        val userLocation = db.collection("userData").document("Location")

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        checkgetPermission()
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                country = getCountryName(location.latitude, location.longitude)
                userLocation.update(country, FieldValue.increment(1))
            }
        }


        /** MOVING DATABASE DATA
        val dareOrDrinkDb = db.collection("mobileGamesData").document("vågaEllerDrick")

        dareOrDrinkDb.get()
        .addOnSuccessListener { fields ->
        if(fields != null){
        val myArray = fields.get("frågor") as List<String>?
        if (myArray != null) {
        for(item in myArray){
        db.collection("mobileGamesData").document("dareOrDrink").collection("swedish").document("questions").update("questions", FieldValue.arrayUnion(item) )
        }
        }
        }else{
        Log.d("noExist", "No document found")
        }
        }
        .addOnFailureListener {exception ->
        Log.d("errorDB", "get failed with ", exception)

        }
         */

        val mobileGamesBtn = findViewById<Button>(R.id.button)
        val cardGamesBtn = findViewById<Button>(R.id.button2)
        val lvlGamesBtn = findViewById<Button>(R.id.button3)
        val alarmBtn = findViewById<Button>(R.id.button4)

        alarmBtn.setOnClickListener {
            val intent = Intent(this, SetReminderActivity::class.java)
            startActivity(
                    intent
            )
        }

        mobileGamesBtn.setOnClickListener {
            val intent = Intent(this, MobileGamesActivity::class.java)
            startActivity(
                    intent
            )
        }
        cardGamesBtn.setOnClickListener {
            val intent = Intent(this, CardGamesActivity::class.java)
            startActivity(
                    intent
            )
        }
        lvlGamesBtn.setOnClickListener {
            val intent = Intent(this, LvLGamesActivity::class.java)
            startActivity(
                    intent
            )
        }

        //Secret admin login panel
        val FIVE_SECONDS = 5 * 1000 // 5s * 1000 ms/s
        val ONE_SECOND = 1 * 1000

        var fiveFingerDownTime: Long = -1

        window.decorView.findViewById<View>(android.R.id.content).setOnTouchListener {v, ev ->
            val action = ev.action
            when (action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_POINTER_DOWN -> if (ev.pointerCount == 2) {
                    // We have five fingers touching, so start the timer
                    fiveFingerDownTime = System.currentTimeMillis()
                }
                MotionEvent.ACTION_POINTER_UP -> {
                    if (ev.pointerCount < 2) {
                        // Fewer than five fingers, so reset the timer
                        fiveFingerDownTime = -1
                    }
                    val now = System.currentTimeMillis()
                    if (now - fiveFingerDownTime >= ONE_SECOND && fiveFingerDownTime != -1L) {
                        // Five fingers have been down for 5 seconds!
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(
                                intent
                        )
                    }
                }
            }
            true
        }
    }
    
    fun checkgetPermission(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return
        }else{
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_ID
            )
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        if(requestCode == PERMISSION_ID){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("Debug:","You have the Permission")
            }
        }
    }

    private fun getCountryName(lat: Double,long: Double):String{
        var countryName = ""
        var geoCoder = Geocoder(this, Locale.getDefault())
        var Adress = geoCoder.getFromLocation(lat,long,3)

        countryName = Adress.get(0).countryName
        return countryName
    }
}