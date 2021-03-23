package com.github.skosvall.nextlvl

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.Intent.getIntent
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.core.content.res.TypedArrayUtils.getText
import com.github.skosvall.nextlvl.R.*

class ReminderNotificationReciever : BroadcastReceiver() {
    private val channelId = "com.github.skosvall.nextlvl"
    //private val notificationId = 123

    companion object{
        const val NOTIFICATION_TITLE = "NOTIFICATION_TITLE"
        const val NOTIFICATION_TEXT = "NOTIFICATION_TEXT"
    }

    override fun onReceive(context: Context?, intent: Intent) {
        if (context != null) {
            createNotificationChannel(context)

            val title = context.applicationContext.getString(R.string.nextlvl)
            val text = context.applicationContext.getString(R.string.reminder_notification_text)

            sendNotification(context, title, text)
        }
    }

    private fun createNotificationChannel(context: Context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val title = "Title"
            val descriptionText = "Time to party"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, title, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(context: Context, title: String, text: String) {
        val notificationIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0)

        val NM = NotificationManagerCompat.from(context)
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(mipmap.ic_launcher_round)
            .setStyle(NotificationCompat.BigTextStyle())
            .setContentTitle(title)
            .setContentText(text)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        NM.notify(1, notification.build())
    }
}