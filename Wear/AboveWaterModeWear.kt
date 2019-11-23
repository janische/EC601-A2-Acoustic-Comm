package com.example.lowcostacousticcommwear

import android.support.wearable.activity.WearableActivity
import android.Manifest
import android.util.Log
import io.chirp.chirpsdk.ChirpSDK
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val REQUEST_RECORD_AUDIO = 1

class AboveWaterModeWear : WearableActivity() {

    private lateinit var chirp: ChirpSDK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_above_water_mode_wear)

        // Enables Always-on
        setAmbientEnabled()

        this.chirp = ChirpSDK(this, CHIRP_APP_KEY, CHIRP_APP_SECRET)
        var error = chirp.setConfig(CHIRP_APP_CONFIG)
        if (error.code == 0) {
            Log.v("ChirpSDK: ", "Configured ChirpSDK")
        } else {
            Log.e("ChirpError: ", error.message)
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_RECORD_AUDIO)
        } else {
            // Start ChirpSDK sender and receiver, if no arguments are passed both sender and receiver are started
            var error = chirp.start(send = true, receive = true)
            if (error.code > 0) {
                Log.e("ChirpError: ", error.message)
            } else {
                Log.v("ChirpSDK: ", "Started ChirpSDK")
            }
        }
        chirp.setListenToSelf(selfToListen = true)

        /*val rv = findViewById<RecyclerView>(R.id.recyclerView1)
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val messages = ArrayList<String>()
        messages.add("Hi there")
        messages.add("Oh no")
        var adptr = ReceiveMessagesAdapter(messages)
        rv.adapter = adptr*/

        val contacts = intent.getStringArrayListExtra("Contacts")
        val Ids = intent.getIntegerArrayListExtra("IDs")
        val myID = intent.getIntExtra("My ID", 0)
        val msgs = findViewById<TextView>(R.id.msgsRecvd)

        //Receive Data
        chirp.onReceived { payload: ByteArray?, channel: Int ->
            if (payload != null) {
                var identifier: String = String(payload)
                msgs.append("\n" + identifier)
                Toast.makeText(this@AboveWaterModeWear, "Message received.", Toast.LENGTH_SHORT).show()
                Log.v("ChirpSDK: ", "Received $identifier")
            } else {
                Log.e("ChirpError: ", "Decode failed")
            }
        }
        chirp.onReceiving { channel: Int ->
            Log.v("ChirpSDK", "ChirpSDKCallback: onReceiving on channel: $channel")
        }

        val Button10 = findViewById<Button>(R.id.button10)
        val Button11 = findViewById<Button>(R.id.button11)
        val Button12 = findViewById<Button>(R.id.button12)
        val Button13 = findViewById<Button>(R.id.button13)
        val Button14 = findViewById<Button>(R.id.button14)
        Button10.setOnClickListener {
            // your code to perform when the user clicks on the button
            Toast.makeText(this@AboveWaterModeWear, "Message sent.", Toast.LENGTH_SHORT).show()
            msgs.append("\n" + Button10.text)
            Button10.sendMessage(Button10.text.toString())
        }
        Button11.setOnClickListener {
            // your code to perform when the user clicks on the button
            Toast.makeText(this@AboveWaterModeWear, "Message sent.", Toast.LENGTH_SHORT).show()
            msgs.append("\n" + Button11.text)
            Button11.sendMessage(Button11.text.toString())
        }
        Button12.setOnClickListener {
            // your code to perform when the user clicks on the button
            Toast.makeText(this@AboveWaterModeWear, "Message sent.", Toast.LENGTH_SHORT).show()
            msgs.append("\n" + Button12.text)
            Button12.sendMessage(Button12.text.toString())
        }
        Button13.setOnClickListener {
            // your code to perform when the user clicks on the button
            Toast.makeText(this@AboveWaterModeWear, "Message sent.", Toast.LENGTH_SHORT).show()
            msgs.append("\n" + Button13.text)
            Button13.sendMessage(Button13.text.toString())
        }
        Button14.setOnClickListener {
            // your code to perform when the user clicks on the button
            Toast.makeText(this@AboveWaterModeWear, "Message sent.", Toast.LENGTH_SHORT).show()
            msgs.append("\n" + Button14.text)
            Button14.sendMessage(Button14.text.toString())
        }
    }
    fun Button.sendMessage(message: String) {
        //Transmit Data
        val payload: ByteArray = message.toByteArray()

        val error = chirp.send(payload)
        if (error.code > 0) {
            Log.e("ChirpError: ", error.message)
        } else {
            Log.v("ChirpSDK: ", "Sent")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        chirp.stop()
        try {
            chirp.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    fun TextView.setMaxViewableLines() = doOnPreDraw {
        val numberOfCompletelyVisibleLines = (measuredHeight - paddingTop - paddingBottom) / lineHeight
        maxLines = numberOfCompletelyVisibleLines
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_RECORD_AUDIO -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    var error = chirp.start()
                    if (error.code > 0) {
                        Log.e("ChirpError: ", error.message)
                    } else {
                        Log.v("ChirpSDK: ", "Started ChirpSDK")
                    }
                }
                return
            }
        }
    }
    override fun onResume() {
        super.onResume()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_RECORD_AUDIO)
        } else {
            // Start ChirpSDK sender and receiver, if no arguments are passed both sender and receiver are started
            var error = chirp.start(send = true, receive = true)
            if (error.code > 0) {
                Log.e("ChirpError: ", error.message)
            } else {
                Log.v("ChirpSDK: ", "Started ChirpSDK")
            }
        }
    }
    override fun onPause() {
        super.onPause()
        chirp.stop()
    }
}
