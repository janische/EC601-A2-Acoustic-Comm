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
const val CHIRP_APP_CONFIG = "lqAQODNUj7tlmxHHz569+BqmDEuYfhe41a6GkRsE8z0NOuRU0eFKuOM5XWv6E5PaBO4ikqV45sS5O8+5wfb7UUhS9V2hKc3pBQcrlThxittYkeC7N5UX4VmEVwQY2T/VtZddGWrH9AQcDwoOzvAC0l3+Z4Bx71WAQJR2+aMEiUqVazuLV+o5hKU0EtlKEtDR/oOppewLNr2ZKs9zgli+92FVFB+inM3EkLjVk00u8dlZK6ZFbmVKK+qGxWOOn+T7cKPX8zmEjy1G/IC7BM84t6rjLnu3vgvFzb5ST+pkAb9Q7aMZBpMZI0c0hWnirOjqT/mGvr3xSkm4Qn2zsQFZvQvhGhx/I/gp+KQbE7XKTuiknrYbj58rOQxbcTQgDAs6epgd0MxSZlb9gzq/KBKymSWJK7qywqKbHr/cllaYNPN9w0hA+SAI9CuNe6GjZCXVU/GMsDFDeDXdSaG0p04/hEAj96c5CPYT1XMv+GXB0tbYyAtx+AzJXO5NC5ldJymG3H2C8WZjNjrczVETI0vivyNCQOh4z242jMKIeXrGcskX5b70uX81E+3VX7/6jwmy+97+4JiIR5yPRiX7vaLNFywyeEoppray0WRL/FhakyDP7Wkey5tDdX5izu2AzPISs8bYEAmvrFspGP3QPWVQoGgCqa+vyjXE9oXBBK8VH8toHPlIvFkmMPpAC4zhZs/ALiBUyuLHqXQez2p6du0jTtA+A7OqZtMKvyC3MRO7We1QAXebqYtPbwsWGIKOvkYiROmig+CI+cE0usPCJcttOGFnq209Gjx5q6X2GdASp9G772go4P7jBCLIkg5Jq36xZQWWQ5L1v3/TBKPW/NBquBaHHpOcjJCo6SA0h7Me7OI=";


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
