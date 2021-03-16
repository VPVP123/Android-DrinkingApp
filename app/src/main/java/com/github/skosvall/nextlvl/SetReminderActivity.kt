package com.github.skosvall.nextlvl

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TimePicker
import android.widget.Toast
import android.widget.ToggleButton
import androidx.core.view.get
import java.sql.Time
import java.util.*
import kotlin.math.min
import kotlin.time.hours


class SetReminderActivity : AppCompatActivity() {
    companion object {
        const val SELECTED_HOURS = "SELECTED HOURS"
        const val SELECTED_MINUTES = "SELECTED MINUTES"
    }

    lateinit var timePicker: TimePicker
    val standardRequestCode = 0

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

        fun createNotification(selectedHour: Int, selectedMinute: Int){
            val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            val currentMinute = Calendar.getInstance().get(Calendar.MINUTE)
            val currentSeconds = Calendar.getInstance().get(Calendar.SECOND)
            val millisUntilReminder = (((selectedHour - currentHour) * 3600000) + ((selectedMinute - currentMinute) * 60000)).toLong()

            val intent = Intent(this, ReminderNotificationReciever::class.java)
            intent.putExtra(ReminderNotificationReciever.NOTIFICATION_TITLE, getString(R.string.nextlvl))
            intent.putExtra(ReminderNotificationReciever.NOTIFICATION_TEXT, getString(R.string.reminder_notification_text))

            intent.putExtra("reason", "notification")
            intent.putExtra("timestamp", (System.currentTimeMillis() + millisUntilReminder))

            val pendingIntent = PendingIntent.getBroadcast(
                    this,
                    standardRequestCode,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            )
            alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    (System.currentTimeMillis() + millisUntilReminder),
                    pendingIntent
            )
            Toast.makeText(applicationContext, "Reminder has been set!", Toast.LENGTH_SHORT).show()
        }

        val toggleButton = findViewById<ToggleButton>(R.id.reminderToggleButton)
        toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                createNotification(timePicker.hour, timePicker.minute)
            } else {
                val intent = Intent(this, ReminderNotificationReciever::class.java)
                val pendingIntent = PendingIntent.getBroadcast(
                        this,
                        standardRequestCode,
                        intent,
                        0
                )
                alarmManager.cancel(pendingIntent)
            }
        }

        timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            if (toggleButton.isChecked) {
                createNotification(hourOfDay, minute)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_HOURS, timePicker.hour)
        outState.putInt(SELECTED_MINUTES, timePicker.minute)
    }

    override fun onStop() {
        super.onStop()

    }
}