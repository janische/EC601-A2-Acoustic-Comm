package com.example.lowcostacousticcomm

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

private const val REQUEST_RECORD_AUDIO = 1



const val CHIRP_APP_KEY = "37b57Bf1bCEA0Ca8e9Ba3CfCc";
const val CHIRP_APP_SECRET = "8fdD20Fa0E7A7b4A0AEC2E7b73bc5BCAF9e520bBAd8AA2FbCE";
const val CHIRP_APP_CONFIG = "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";



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val contacts = setContacts()
        val ids = assignIDs(contacts)
        val myID = "1"
        val aboveWatButton = findViewById<Button>(R.id.button1)
        // set on-click listener
        aboveWatButton.setOnClickListener {
            // your code to perform when the user clicks on the button
            Toast.makeText(this@MainActivity, "Above-Water Mode", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, AboveWaterMode::class.java)
            intent.putStringArrayListExtra ("IDs", ids)
            intent.putStringArrayListExtra ("Contacts", contacts)
            intent.putExtra("My ID", myID)
            startActivity(intent)
        }
        val underWatButton = findViewById<Button>(R.id.button2)
        // set on-click listener
        underWatButton.setOnClickListener {
            // your code to perform when the user clicks on the button
            Toast.makeText(this@MainActivity, "Underwater Mode", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, UnderwaterMode::class.java)
            intent.putStringArrayListExtra ("IDs", ids)
            intent.putStringArrayListExtra ("Contacts", contacts)
            intent.putExtra("My ID", myID)
            startActivity(intent)
        }
    }

    fun mode(view: View) {
        val id = view.id
        val stringId = resources.getResourceName(id).substringAfter("/")

        if (stringId == "button1") {
            val textView = findViewById<TextView>(R.id.textView).apply {
                text = stringId
            }
            var intent = Intent(this, AboveWaterMode::class.java)
            startActivity(intent)
        } else {
            val textView = findViewById<TextView>(R.id.textView).apply {
                text = stringId
            }
            var intent = Intent(this, UnderwaterMode::class.java)
            startActivity(intent)
        }

    }
    fun setContacts(): ArrayList<String> {
        var contacts = arrayListOf<String>()
        contacts.addAll(listOf("Broadcast", "JP", "Glenn", "Jonathan", "Amanda"))
        return contacts
    }
    fun assignIDs(contacts: ArrayList<String>): ArrayList<String> {
        var IDs = arrayListOf<String>()
        var i = 0
        for (con in contacts) {
            IDs.add(i.toString())
            i++
        }
        return IDs
    }
}
