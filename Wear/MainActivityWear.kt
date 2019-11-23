package com.example.lowcostacousticcommwear

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

const val CHIRP_APP_KEY = "";
const val CHIRP_APP_SECRET = "";
const val CHIRP_APP_CONFIG = "";


class MainActivityWear : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_wear)

        // Enables Always-on
        setAmbientEnabled()

        val contacts = setContacts()
        val ids = assignIDs(contacts)
        val myID = 1
        val aboveWatButton = findViewById<Button>(R.id.button1)
        // set on-click listener
        aboveWatButton.setOnClickListener {
            // your code to perform when the user clicks on the button
            Toast.makeText(this@MainActivityWear, "Above-Water Mode", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, AboveWaterModeWear::class.java)
            intent.putIntegerArrayListExtra ("IDs", ids)
            intent.putStringArrayListExtra ("Contacts", contacts)
            intent.putExtra("My ID", myID)
            startActivity(intent)
        }
        val underWatButton = findViewById<Button>(R.id.button2)
        // set on-click listener
        underWatButton.setOnClickListener {
            // your code to perform when the user clicks on the button
            Toast.makeText(this@MainActivityWear, "Underwater Mode", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, UnderwaterModeWear::class.java)
            intent.putIntegerArrayListExtra ("IDs", ids)
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
            var intent = Intent(this, AboveWaterModeWear::class.java)
            startActivity(intent)
        } else {
            val textView = findViewById<TextView>(R.id.textView).apply {
                text = stringId
            }
            var intent = Intent(this, UnderwaterModeWear::class.java)
            startActivity(intent)
        }

    }
    fun setContacts(): ArrayList<String> {
        var contacts = arrayListOf<String>()
        contacts.addAll(listOf("Broadcast", "JP", "Glenn", "Jonathan", "Amanda"))
        return contacts
    }
    fun assignIDs(contacts: ArrayList<String>): ArrayList<Int> {
        var IDs = arrayListOf<Int>()
        var i = 1
        for (con in contacts) {
            IDs.add(i)
            i++
        }
        return IDs
    }
}
