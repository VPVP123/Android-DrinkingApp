package com.github.skosvall.nextlvl

import android.annotation.SuppressLint
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")


    lateinit var context: Context
    lateinit var alarmManager: AlarmManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val db = FirebaseFirestore.getInstance()

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

        alarmBtn.setOnClickListener{
            /*val seconds = 5*1000
            val intent = Intent(context, Receiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + seconds, pendingIntent)
            Log.d("MainActivity", "sent")*/
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

        window.decorView.findViewById<View>(android.R.id.content).setOnTouchListener { v, ev ->
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
        //End of secret admin login panel
    }






}