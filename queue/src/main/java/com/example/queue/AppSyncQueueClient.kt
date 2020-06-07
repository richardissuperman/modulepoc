package com.example.queue

import android.content.Context
import android.widget.Toast
import com.example.base.BaseQueueClient

class AppSyncQueueClient : BaseQueueClient {

    override fun register(c: Context) {
        Toast.makeText(c, "Call this method from run time", Toast.LENGTH_LONG).show()
    }

}