package com.github.skosvall.nextlvl

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val mobileGamesBtn = findViewById<Button>(R.id.button)
        val cardGamesBtn = findViewById<Button>(R.id.button2)
        val lvlGamesBtn = findViewById<Button>(R.id.button3)

        mobileGamesBtn.setOnClickListener {
            val intent = Intent(this, mobileGamesActivity::class.java)
            startActivity(
                intent
            )
        }

        //Secrete admin login panel

        val FIVE_SECONDS = 5 * 1000 // 5s * 1000 ms/s

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
                    if (now - fiveFingerDownTime > FIVE_SECONDS && fiveFingerDownTime != -1L) {
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


        //End of secrete admin login panel







        cardGamesBtn.setOnClickListener {
            val intent = Intent(this, cardGamesActivity::class.java)
            startActivity(
                intent
            )
        }

        lvlGamesBtn.setOnClickListener {
            val intent = Intent(this, lvlGamesActivity::class.java)
            startActivity(
                intent
            )
        }




    }
}