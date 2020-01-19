package com.padcmyanmar.padc9.serviceexample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

/**
 * Created by Ye Pyae Sone Tun
 * on 2020-01-18.
 */

class ForegroundService : Service() {

    private val CHANNEL_ID = "ForegroundService Kotlin"

    companion object {
        private val IE_INPUT_EXTRA = "IE_INPUT_EXTRA"

        fun startService(context: Context, message: String) {
            val startIntent = Intent(context, ForegroundService::class.java)
            startIntent.putExtra(IE_INPUT_EXTRA, message)
            ContextCompat.startForegroundService(context, startIntent)
        }

        fun stopService(context: Context) {
            val stopIntent = Intent(context, ForegroundService::class.java)
            context.stopService(stopIntent)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        //do heavy work on a background thread
        val input = intent?.getStringExtra(IE_INPUT_EXTRA)
        createNotificationChannel()


        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
                this,
                0, notificationIntent, 0
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("FOREGROUND SERVICE TEST")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_notifications_none_black_24dp)
                .setContentIntent(pendingIntent)
                .build()

        startForeground(1, notification)

        //stopSelf();
        return START_NOT_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "NOTIFICATION_CHANNEL",
                    NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }


    override fun onBind(p0: Intent?): IBinder? {
        return null
        //  တခြား component တစ်ခုခုက လာပြီး bind ချင်တဲ့အခါတွက် ဆိုရင် ဆိုင်ရာ binder ဖြစ်ရမယ်၊ အဲလိုမှ မဟုတ်ရင်တော့ null
    }
}