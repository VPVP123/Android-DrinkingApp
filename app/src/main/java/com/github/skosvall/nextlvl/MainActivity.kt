package com.github.skosvall.nextlvl

import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.location.Location
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var context: Context
    private lateinit var alarmManager: AlarmManager
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var country = ""
    private val permissionId = 1010

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val db = FirebaseFirestore.getInstance()

        val userLocation = db.collection("userData").document("Location")

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        checkAndGetPermission()
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                country = getCountryName(location.latitude, location.longitude)
                userLocation.update(country, FieldValue.increment(1))
            }
        }

        val mobileGamesBtn = findViewById<Button>(R.id.mobile_games_button)
        val cardGamesBtn = findViewById<Button>(R.id.card_games_button)
        val lvlGamesBtn = findViewById<Button>(R.id.lvl_games_button)
        val alarmBtn = findViewById<Button>(R.id.set_reminder_button)

        alarmBtn.setOnClickListener {
            val intent = Intent(this, SetReminderActivity::class.java)
            startActivity(intent)
        }

        mobileGamesBtn.setOnClickListener {
            val intent = Intent(this, MobileGamesActivity::class.java)
            startActivity(intent)
        }
        cardGamesBtn.setOnClickListener {
            val intent = Intent(this, CardGamesActivity::class.java)
            startActivity(intent)
        }
        lvlGamesBtn.setOnClickListener {
            val intent = Intent(this, LvLGamesActivity::class.java)
            startActivity(intent)
        }

        //Secret admin login panel
        val oneSecond = 1 * 1000

        var fingerDownTime: Long = -1
        val fingersToHold = 2

        window.decorView.findViewById<View>(android.R.id.content).setOnTouchListener(fun(_: View, ev: MotionEvent): Boolean {
            val action = ev.action
            when (action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_POINTER_DOWN -> if (ev.pointerCount == fingersToHold) {
                    fingerDownTime = System.currentTimeMillis()
                }
                MotionEvent.ACTION_POINTER_UP -> {
                    if (ev.pointerCount < fingersToHold) {
                        fingerDownTime = -1
                    }
                    val now = System.currentTimeMillis()
                    if (now - fingerDownTime >= oneSecond && fingerDownTime != -1L) {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
            return true
        })
    }
    
    private fun checkAndGetPermission(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return
        }else{
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                permissionId
            )
        }
    }

    private fun getCountryName(lat: Double,long: Double):String{
        val countryName: String
        val geoCoder = Geocoder(this, Locale.getDefault())
        val address = geoCoder.getFromLocation(lat,long,3)

        countryName = address[0].countryName
        return countryName
    }
}