package com.example.lowcostacousticcomm

import android.Manifest
import android.util.Log
import io.chirp.chirpsdk.ChirpSDK
import androidx.appcompat.app.AppCompatActivity
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
import android.os.Handler
import kotlinx.android.synthetic.main.activity_underwater_mode.*


private const val REQUEST_RECORD_AUDIO = 1

class UnderwaterMode : AppCompatActivity() {

    private lateinit var chirp: ChirpSDK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_underwater_mode)

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

        val msgsRecvd = findViewById<TextView>(R.id.msgsRecvd)

        //Receive Data
        chirp.onReceived { payload: ByteArray?, channel: Int ->
            if (payload != null) {
                var identifier: String = String(payload)
                msgsRecvd.append("\n" + identifier)
                Log.v("ChirpSDK: ", "Received $identifier")
            } else {
                Log.e("ChirpError: ", "Decode failed")
            }
        }
        chirp.onReceiving { channel: Int ->
            Log.v("ChirpSDK", "ChirpSDKCallback: onReceiving on channel: $channel")
        }

        val Button20 = findViewById<Button>(R.id.button20)
        val Button21 = findViewById<Button>(R.id.button21)
        val Button22 = findViewById<Button>(R.id.button22)
        val Button23 = findViewById<Button>(R.id.button23)
        val Button24 = findViewById<Button>(R.id.button24)
        val Button25 = findViewById<Button>(R.id.button25)
        Button20.setOnClickListener {
            // your code to perform when the user clicks on the button
            Toast.makeText(this@UnderwaterMode, "Message sent.", Toast.LENGTH_SHORT).show()
            msgsRecvd.append("\n" + Button20.text)
            Button20.sendMessage(Button20.text.toString())
        }
        Button21.setOnClickListener {
            // your code to perform when the user clicks on the button
            Toast.makeText(this@UnderwaterMode, "Message sent.", Toast.LENGTH_SHORT).show()
            msgsRecvd.append("\n" + Button21.text)
            Button21.sendMessage(Button21.text.toString())
        }
        Button22.setOnClickListener {
            // your code to perform when the user clicks on the button
            Toast.makeText(this@UnderwaterMode, "Message sent.", Toast.LENGTH_SHORT).show()
            msgsRecvd.append("\n" + Button22.text)
            Button22.sendMessage(Button22.text.toString())
        }
        Button23.setOnClickListener {
            // your code to perform when the user clicks on the button
            Toast.makeText(this@UnderwaterMode, "Message sent.", Toast.LENGTH_SHORT).show()
            msgsRecvd.append("\n" + Button23.text)
            Button23.sendMessage(Button23.text.toString())
        }
        Button24.setOnClickListener {
            // your code to perform when the user clicks on the button
            Toast.makeText(this@UnderwaterMode, "Message sent.", Toast.LENGTH_SHORT).show()
            msgsRecvd.append("\n" + Button24.text)
            Button24.sendMessage(Button24.text.toString())
        }
        Button25.setOnClickListener {
            // your code to perform when the user clicks on the button
            Toast.makeText(this@UnderwaterMode, "Message sent.", Toast.LENGTH_SHORT).show()
            msgsRecvd.append("\n" + Button25.text)
            Button25.sendMessage(Button25.text.toString())
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
