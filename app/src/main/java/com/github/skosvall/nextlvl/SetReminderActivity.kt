package com.github.skosvall.nextlvl

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.TimePicker
import android.widget.ToggleButton
import androidx.core.view.get
import java.sql.Time
import java.util.*



class SetReminderActivity : AppCompatActivity() {
    companion object {
        const val SELECTED_HOURS = "SELECTED HOURS"
        const val SELECTED_MINUTES = "SELECTED MINUTES"
    }

    lateinit var timePicker: TimePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_reminder)

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        timePicker = findViewById<TimePicker>(R.id.timePicker)
        timePicker.setIs24HourView(true)
        timePicker.setBackgroundColor(getColor(R.color.white))

        if(savedInstanceState == null) {
            timePicker.hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            timePicker.minute = Calendar.getInstance().get(Calendar.MINUTE)


        } else{
            timePicker.hour = savedInstanceState.getInt(SELECTED_HOURS)
            timePicker.minute = savedInstanceState.getInt(SELECTED_MINUTES)
        }

        val toggleButton = findViewById<ToggleButton>(R.id.reminderToggleButton)
        toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val notificationTimeInMillis =
                    ((timePicker.hour * 3600000) + (timePicker.minute * 60000)).toLong()
                val intent = Intent(this, MainActivity.Receiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(
                    this,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
                alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    notificationTimeInMillis,
                    pendingIntent
                )
                Log.d("SetReminderActivity", "sent")
            }
        }

        timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            if (toggleButton.isChecked) {
                val notificationTimeInMillis =
                    ((hourOfDay * 3600000) + (minute * 60000)).toLong()
                val intent = Intent(this, MainActivity.Receiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(
                    this,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
                alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    notificationTimeInMillis,
                    pendingIntent
                )
                Log.d("SetReminderActivity", "sent")
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_HOURS, timePicker.hour)
        outState.putInt(SELECTED_MINUTES, timePicker.minute)
    }
}