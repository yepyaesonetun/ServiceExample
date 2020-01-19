package com.padcmyanmar.padc9.serviceexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_foreground_service_test.*

class ForegroundServiceTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foreground_service_test)

        btnStartService.setOnClickListener {
            ForegroundService.startService(this, "Service is starting")
        }

        btnStopService.setOnClickListener {
            ForegroundService.stopService(this)
        }
    }
}
