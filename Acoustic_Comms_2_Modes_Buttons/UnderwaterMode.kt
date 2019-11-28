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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.os.Handler
import kotlinx.android.synthetic.main.activity_underwater_mode.*
import android.text.Layout
import android.opengl.ETC1.getHeight
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.*


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

        val contacts = intent.getStringArrayListExtra("Contacts")
        val IDs = intent.getStringArrayListExtra("IDs")
        val myID = intent.getStringExtra("My ID")
        var recID = "1"
        val messages = parseCodeBook(R.array.message_codebook)

        val msgsView = findViewById<TextView>(R.id.msgsRecvd)
        val spinner = findViewById<Spinner>(R.id.spinner_u)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, contacts)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                recID = position.toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        //Receive Data
        chirp.onReceived { payload: ByteArray?, channel: Int ->
            if (payload != null) {
                var identifier: String = String(payload, Charsets.UTF_8)
                val id = identifier[2].toString()
                if (id == "0" || id == myID) {
                    val senderMessage = parseMessage(identifier)
                    msgsView.append("\n" + messages[senderMessage["Message"]])
                    Toast.makeText(this@UnderwaterMode, "Message received.", Toast.LENGTH_SHORT).show()
                }
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
            //msgsView.append("\n" + Button20.text)
            Button20.sendMessage(messages.keys.elementAt(0), myID, IDs[0])
        }
        Button21.setOnClickListener {
            // your code to perform when the user clicks on the button
            Toast.makeText(this@UnderwaterMode, "Message sent.", Toast.LENGTH_SHORT).show()
            //msgsView.append("\n" + Button21.text)
            Button21.sendMessage(messages.keys.elementAt(4), myID, recID)
        }
        Button22.setOnClickListener {
            // your code to perform when the user clicks on the button
            Toast.makeText(this@UnderwaterMode, "Message sent.", Toast.LENGTH_SHORT).show()
            //msgsView.append("\n" + Button22.text)
            Button22.sendMessage(messages.keys.elementAt(2), myID, recID)
        }
        Button23.setOnClickListener {
            // your code to perform when the user clicks on the button
            Toast.makeText(this@UnderwaterMode, "Message sent.", Toast.LENGTH_SHORT).show()
            //msgsView.append("\n" + Button23.text)
            Button23.sendMessage(messages.keys.elementAt(3), myID, recID)
        }
        Button24.setOnClickListener {
            // your code to perform when the user clicks on the button
            Toast.makeText(this@UnderwaterMode, "Message sent.", Toast.LENGTH_SHORT).show()
            //msgsView.append("\n" + Button24.text)
            Button24.sendMessage(messages.keys.elementAt(5), myID, recID)
        }
        Button25.setOnClickListener {
            // your code to perform when the user clicks on the button
            Toast.makeText(this@UnderwaterMode, "Message sent.", Toast.LENGTH_SHORT).show()
            //msgsView.append("\n" + Button25.text)
            Button25.sendMessage(messages.keys.elementAt(6), myID, recID)
        }
    }

    fun Button.sendMessage(messageID: String, myID: String, recID: String) {
        //Transmit Data
        val message = "" + recID + myID + messageID
        val payload: ByteArray = message.toByteArray(Charsets.UTF_8)

        val error = chirp.send(payload)
        if (error.code > 0) {
            Log.e("ChirpError: ", error.message)
        } else {
            Log.v("ChirpSDK: ", "Sent")
        }
    }
    fun parseMessage(payload: String): MutableMap<String, String> {
        val sender = payload[0].toString()
        val message = payload[2].toString()
        val parsed = mutableMapOf("Sender" to sender, "Message" to message)
        return parsed
    }
    fun parseCodeBook(stringArrayResourceId: Int): MutableMap<String, String> {
        val stringArray = resources.getStringArray(stringArrayResourceId);
        var outputArray = mutableMapOf<String, String>()
        for (entry in stringArray) {
            val splitResult = entry.split("|")
            outputArray.put(splitResult[0], splitResult[1])
        }
        return outputArray
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
