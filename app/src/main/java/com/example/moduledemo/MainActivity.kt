package com.example.moduledemo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.base.BaseQueueClient
import java.io.InputStream
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.Toast
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringWriter
import java.lang.Exception




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val client : BaseQueueClient  = readFromFile()
        client.register(this)
    }


    fun readFromFile(): BaseQueueClient {
        val i: InputStream = resources.openRawResource(R.raw.service);
        val writer = StringWriter()
        val buffer = CharArray(1024)
        try {
            var reader = BufferedReader(InputStreamReader(i, "UTF-8"));
            var n: Int = 0
            while (n != -1) {
                n = reader.read(buffer)
                if (n != -1) {
                    writer.write(buffer, 0, n)
                }
            }
        } finally {
            i.close()
        }
        try {
            var serviceJson: JSONObject = JSONObject(writer.toString())
            val appsyncClientStringClassName : String = serviceJson.getString("queue")
            val c = Class.forName(appsyncClientStringClassName)
            return c.newInstance() as BaseQueueClient

        } catch (e: Exception) {
            return object : BaseQueueClient {
                override fun register(c: Context) {
                    Toast.makeText(applicationContext, "this is failure default", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
}
