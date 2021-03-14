package com.github.skosvall.nextlvl

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class ReminderNotificationReciever : BroadcastReceiver() {
    private val channelId = "com.github.skosvall.nextlvl"
    private val notificationId = 123

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
            createNotificationChannel(context)
            sendNotification(context)
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

    private fun sendNotification(context: Context){
        val intent = Intent(context, MainActivity::class.java).apply{
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val bitmap = BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round)

        val builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                .setContentTitle("Yo test")
                .setContentText("Time for a party")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(context)){
            notify(notificationId, builder.build())
        }
    }

}